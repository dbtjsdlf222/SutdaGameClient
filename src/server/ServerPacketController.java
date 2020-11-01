package server;

import java.io.IOException;
import java.net.Socket;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonProcessingException;

import operator.RoomOperator;
import util.Packing;
import vo.Packet;
import vo.PlayerVO;
import vo.Protocol;

public class ServerPacketController extends ServerMethod {
	protected Socket socket;
	
	public ServerPacketController(Socket socket) {
		this.socket = socket;
		thisPlayerVO.setSocketWithBrPw(socket);
	} //ServerPacketController();
	
	public void packetAnalysiser(Packet packet) throws JsonProcessingException {
		
		logger.info("[Receive(" + Protocol.getName(packet.getProtocol()) + ")] " + packet);
		
		switch (packet.getProtocol()) {

		case Protocol.MESSAGE:
			packet.setMotion(thisPlayerVO.getNic() + " : " + packet.getMotion());
			if (thisPlayerVO.getRoomNo() == 0) {
				
				for (Entry<String,PlayerVO> e: lobbyPlayerList.entrySet()) {
					Packing.sender(e.getValue().getPwSocket(), packet);
				}
			} else
				ro.getRoom(thisPlayerVO.getRoomNo()).roomSpeaker(packet);

			break;
			
		case Protocol.WHISPER:
			if(playerOnlineList.containsKey(packet.getPlayerVO().getNic())) {
				packet.setMotion(thisPlayerVO.getNic() + " : " + packet.getMotion());
				Packing.sender(playerOnlineList.get(packet.getPlayerVO().getNic()),packet);	
			} else {
				packet.setProtocol(Protocol.SERVER_MESSAGE);
				packet.setMotion("접속해 있지 않는 아이디 이거나 아아디를 잘못 입력하셨습니다.");
				Packing.sender(thisPlayerVO.getPwSocket(), packet);
			}
			
			break;

		case Protocol.LOGIN:

			String id = packet.getPlayerVO().getId();
			String pw = packet.getPlayerVO().getPassword();

			PlayerVO vo = serverDAO.login(id, pw);
			if(vo == null) {
				Packing.sender(thisPlayerVO.getPwSocket(), Protocol.LOGIN);
			}else {			
				if(playerOnlineList.containsKey(vo.getNic())) {
					Packing.sender(thisPlayerVO.getPwSocket(), Protocol.ONLINE, vo);
					return;
				}				
				Packing.sender(thisPlayerVO.getPwSocket(), Protocol.LOGIN, vo);
				vo.setSocketWithBrPw(thisPlayerVO.getSocket());
				playerOnlineList.put(vo.getNic(), vo.getPwSocket());
				thisPlayerVO = vo;
				packet.setProtocol(Protocol.ENTER_LOBBY);
				packet.setPlayerVO(vo);
				this.packetAnalysiser(packet);
			}
			break;
			
		case Protocol.OFFLINE:
			exitPlayer();
			try {
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;
		
		case Protocol.KICK:
			//로비에 있는 경우 
			if(thisPlayerVO.getRoomNo() == 0) {
				Packing.sender(thisPlayerVO.getPwSocket(),Protocol.SERVER_MESSAGE,"로비엔 추방이 불가능 합니다.");
			}else {
				//방장이 아닌경우
				if(!ro.getRoom(thisPlayerVO.getRoomNo()).getMaster().equals(thisPlayerVO.getNic())) {
					Packing.sender(thisPlayerVO.getPwSocket(),Protocol.SERVER_MESSAGE,"방장이 아닌 플레이어는 사용할 수 없습니다.");
				}
				//본인 게임 시작 했을 경우
				if(ro.getRoom(thisPlayerVO.getRoomNo()).isGameStarted()) {
					Packing.sender(thisPlayerVO.getPwSocket(),Protocol.SERVER_MESSAGE,"게임중엔 추방이 불가능 합니다.");
				}else {
					//상대가 방에 없는 경우
					Integer  b = null;
					for (Integer a : ro.getRoom(thisPlayerVO.getRoomNo()).getPlayerMap().keySet()) {
						if(ro.getRoom(thisPlayerVO.getRoomNo()).getPlayerMap().get(a).getNic().equals(packet.getMotion())) {
							b=a;
							break;
						}
					}
					if(b == null) {
						Packing.sender(thisPlayerVO.getPwSocket(),Protocol.SERVER_MESSAGE,packet.getMotion()+"님이 게임방에 없습니다.");
					}else {
						//추방
						Packing.sender(thisPlayerVO.getPwSocket(),Protocol.SERVER_MESSAGE,packet.getMotion()+"님을 추방하셨습니다.");
						
						Packet packet1 = new Packet();
						packet1.setProtocol(Protocol.KICK);
						packet1.setRoom(ro.getRoom(thisPlayerVO.getRoomNo()));
						
						Packing.sender(ro.getRoom(thisPlayerVO.getRoomNo()).getPlayerMap().get(b).getPwSocket(),packet1);
					}
				}
			}
				
			
			
			break;
			
		case Protocol.FIND:
			if(playerOnlineList.get(packet.getMotion()) != null) {
				
			}
			
		case Protocol.MAKE_ROOM:
			thisPlayerVO.setRoomNo(ro.makeRoom(thisPlayerVO,packet.getRoom()));
			lobbyExitBroadcast();
			
			thisPlayerVO.setIndex(0);	//첫 플레이어로 초기화
			packet.setPlayerVO(thisPlayerVO);
			packet.setMotion(0+"");		//방장 인덱스
			packet.setProtocol(Protocol.MAKE_ROOM);
			
			Packing.sender(thisPlayerVO.getPwSocket(), packet);
			lobbyReloadBroadcast();
			
//			packet.setAction(Protocol.CHANGEMASTER);
//			Packing.sender(thisPlayerVO.getPwSocket(), packet);
			
			break;

		case Protocol.EXIT_ROOM:
			Room room = ro.getRoom(thisPlayerVO.getRoomNo());
			room.exitPlayer(thisPlayerVO);
			thisPlayerVO.setRoomNo(0);
			lobbyPlayerList.put(thisPlayerVO.getNic(),thisPlayerVO);
			room.roomSpeakerNotThisPlayer(packet, thisPlayerVO.getNo());
			lobbyReloadBroadcast();
			break;

		case Protocol.ENTER_ROOM:
			int roomNo = packet.getPlayerVO().getRoomNo();	//입장할 방 번호 받음
			//엔터 룸 돈 체크
			//엔터 룸시 방이 꽉 찻는지 체크
			//게임 중인지 체크
			String reason = null;
			if((reason=ro.getRoom(roomNo).checkStatus(thisPlayerVO))!=null) {
				Packing.sender(thisPlayerVO.getPwSocket(), Protocol.SERVER_MESSAGE,reason);
				return;
			}
			
			thisPlayerVO.setRoomNo(roomNo);
			int index = ro.joinRoom(roomNo, thisPlayerVO);
			thisPlayerVO.setIndex(index);	//자신이 몇번쨰 index인지 저장
			Packet pak = new Packet(Protocol.ENTER_OTHER_ROOM, thisPlayerVO);
			pak.setMotion(index +"");
			pak.setPlayerVO(thisPlayerVO);
			ro.getRoom(roomNo).roomSpeakerNotThisPlayer(pak,thisPlayerVO.getNo());
			packet.setRoomPlayerList(ro.getRoom(roomNo).getList());
			packet.setPlayerVO(thisPlayerVO);
			packet.setRoom(ro.getRoom(roomNo));
			packet.setMotion(ro.getRoom(roomNo).getMasterIndex().toString());
			Packing.sender(thisPlayerVO.getPwSocket(), packet);
			
			lobbyExitBroadcast();

			break;

		case Protocol.RELOAD_PlAYERLIST:
			packet.setRoomPlayerList(ro.getRoom(thisPlayerVO.getRoomNo()).getList());
			packet.setPlayerVO(thisPlayerVO);
			packet.setRoom(ro.getRoom(thisPlayerVO.getRoomNo()));
			packet.setMotion(ro.getRoom(thisPlayerVO.getRoomNo()).getMasterIndex().toString());
			Packing.sender(thisPlayerVO.getPwSocket(), packet);
			
			break;

			
		// 로비에 있는 소켓에게 입장 playerVO와 그 소켓들의 목록을 자신 소켓에 보냄
		case Protocol.ENTER_LOBBY:
			if (thisPlayerVO.getNic() == null) {
				thisPlayerVO = packet.getPlayerVO();
				thisPlayerVO.setSocketWithBrPw(socket);
			}
			lobbyPlayerList.put(thisPlayerVO.getNic(),thisPlayerVO); // 로비 리스트에 자신 추가
			
//			packet.setPlayerList(lobbyPlayerList); // 자신에게 로비에 출력할 입장된 사람 보냄
//			packet.setRoomMap(ro.getAllRoom());
//			Packing.sender(thisPlayerVO.getPwSocket(), packet);
			
			thisPlayerVO.saveExceptPw(serverDAO.selectOnePlayerWithNo(thisPlayerVO.getNo()));
			lobbyReloadBroadcast();
			break;

		case Protocol.RELOAD_LOBBY_LIST:
			packet.setRoomMap(ro.getAllRoom());
			packet.setPlayerList(lobbyPlayerList);
			
			lobbyBroadcast(packet);

			break;
			
		case Protocol.GAME_START:
			ro.getRoom(thisPlayerVO.getRoomNo()).gameStart();
			break;
			
		case Protocol.BET:
			RoomOperator.getInstance().getRoom(thisPlayerVO.getRoomNo()).bet(packet.getMotion());
			break;
			
		case Protocol.TIME_OUT:
			RoomOperator.getInstance().getRoom(thisPlayerVO.getRoomNo()).countDie();
			break;

		case Protocol.JOIN:
			try {
				Packing.sender(thisPlayerVO.getPwSocket(), Protocol.JOIN, Integer.toString(serverDAO.playerJoin(packet.getPlayerVO())));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			break;
			
		case Protocol.PASSWORD:
			String password = ro.getRoom(packet.getRoom().getRoomNo()).getPassword();
			if(password.equals(packet.getRoom().getPassword())) {
				Packing.sender(thisPlayerVO.getPwSocket(),Protocol.PASSWORD,"true");
				packet.setProtocol(Protocol.ENTER_ROOM);
				thisPlayerVO.setRoomNo(packet.getRoom().getRoomNo());
				packet.setPlayerVO(thisPlayerVO);
				packetAnalysiser(packet);
			} else {
				Packing.sender(thisPlayerVO.getPwSocket(),Protocol.PASSWORD,"false");
			}
			break;
		
		case Protocol.DO_INVITE:
			
//			Timer t = new Timer();
//			TimerTask task = new TimerTask() {
//				@Override
//				public void run() { }
//			};
			
			//본인 로비인 경우
			if(thisPlayerVO.getRoomNo() == 0) {
				Packing.sender(thisPlayerVO.getPwSocket(),Protocol.SERVER_MESSAGE,"로비엔 초대가 불가능 합니다.");
			} else {
				//방장이 아닌경우
				if(!ro.getRoom(thisPlayerVO.getRoomNo()).getMaster().equals(thisPlayerVO.getNic())) {
					Packing.sender(thisPlayerVO.getPwSocket(),Protocol.SERVER_MESSAGE,"방장이 아닌 플레이어는 사용할 수 없습니다.");
				}
				//본인 게임 시작 했을 경우
				if(ro.getRoom(thisPlayerVO.getRoomNo()).isGameStarted()) {
					Packing.sender(thisPlayerVO.getPwSocket(),Protocol.SERVER_MESSAGE,"게임중엔 초대가 불가능 합니다.");
				}else {
					//상대가 오프라인인 경우
					if(playerOnlineList.get(packet.getMotion()) == null) {
						Packing.sender(thisPlayerVO.getPwSocket(),Protocol.SERVER_MESSAGE,"없는 닉네임 이거나 접속중이지 않습니다.");
					} else {
						//상대가 로비에 없는 경우
						if(lobbyPlayerList.get(packet.getMotion()) == null) {
							Packing.sender(thisPlayerVO.getPwSocket(),Protocol.SERVER_MESSAGE,packet.getMotion()+"님이 다른 게임방에 있습니다.");
						} else {
						//초대
							Packing.sender(thisPlayerVO.getPwSocket(),Protocol.SERVER_MESSAGE,packet.getMotion()+"님을 초대했습니다.");
							
							Packet packet1 = new Packet();
							packet1.setPlayerVO(thisPlayerVO);
							packet1.setRoom(ro.getRoom(thisPlayerVO.getRoomNo()));
							packet1.setProtocol(Protocol.GET_INVITE);

							Packing.sender(lobbyPlayerList.get(packet.getMotion()).getPwSocket(),packet1);
						}
					}
				}
			}
			break;
			
		case Protocol.EXTRAMONEY:
			if(serverDAO.moneyChargeCheck(thisPlayerVO.getNo()) > 0) {
				if(serverDAO.extraMoney(thisPlayerVO) == 1) {
					serverDAO.useMoneyCharge(thisPlayerVO.getNo());
					Packing.sender(thisPlayerVO.getPwSocket(), Protocol.RELOAD_MY_VO, thisPlayerVO);
					infoReloadcast();
					packet.setMotion("비상금 1000만원이 입급 되었습니다.");
					Packing.sender(thisPlayerVO.getPwSocket(), Protocol.SERVER_MESSAGE, packet.getMotion());
				}
			}else {
				packet.setMotion("금일 비상금 서비스를 다 사용하셨습니다.");
				Packing.sender(thisPlayerVO.getPwSocket(), Protocol.SERVER_MESSAGE, packet.getMotion());
			}
			break;
			
		case Protocol.SELECT_ID:
			Packing.sender(thisPlayerVO.getPwSocket(), Protocol.SELECT_ID, serverDAO.selectID(packet.getMotion()) ? "true" : "false");
			break;
			
		case Protocol.SELECT_NICK:
			Packing.sender(thisPlayerVO.getPwSocket(), Protocol.SELECT_NICK, serverDAO.selectNick(packet.getMotion()) ? "true" : "false");
			break;		
			
		case Protocol.SELECT_ONE_PLAYER_WITH_NO:
			Packing.sender(thisPlayerVO.getPwSocket(), Protocol.SELECT_ONE_PLAYER_WITH_NO, serverDAO.selectOnePlayerWithNo(packet.getPlayerVO().getNo()));
			break;
		} // switch
	} // packetAnalysiser();
} // ServerPacketController();
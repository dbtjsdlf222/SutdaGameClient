package server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import com.fasterxml.jackson.core.JsonProcessingException;

import dao.ServerDAO;
import operator.RoomOperator;
import util.Packing;
import vo.Packet;
import vo.PlayerVO;
import vo.Protocol;

public class ServerPacketController extends ServerMethod {
	
	private ServerDAO serverDAO = new ServerDAO();
	
	public ServerPacketController(Socket socket) {
		super.socket = socket;
		thisPlayerVO.setSocketWithBrPw(socket);
	} //ServerPacketController();
	
	public void packetAnalysiser(Packet packet) throws JsonProcessingException {
		
		logger.info("[Receive(" + Protocol.getName(packet.getAction()) + ")] " + packet);
		
		switch (packet.getAction()) {

		case Protocol.MESSAGE:
			packet.setMotion(thisPlayerVO.getNic() + ": " + packet.getMotion());
			if (thisPlayerVO.getRoomNo() == 0) {
				for (PlayerVO playerVO : lobbyPlayerList) {
					Packing.sender(playerVO.getPwSocket(), packet);
				}
			} else
				ro.getRoom(thisPlayerVO.getRoomNo()).roomSpeaker(packet);

			break;

		case Protocol.LOGIN:

			String id = packet.getPlayerVO().getId();
			String pw = packet.getPlayerVO().getPassword();

			PlayerVO vo = dao.login(id, pw);

			if (vo != null) {
				vo.setSocketWithBrPw(thisPlayerVO.getSocket()); 
				thisPlayerVO = vo;
				packet.setAction(Protocol.ENTERLOBBY);
				packet.setPlayerVO(vo);
				this.packetAnalysiser(packet);
			} else {
				Packing.sender(thisPlayerVO.getPwSocket(), Protocol.LOGIN, vo);
			}
			
			break;

		case Protocol.MAKEROOM:
			thisPlayerVO.setRoomNo(ro.makeRoom(thisPlayerVO));
			lobbyExitBroadcast();
			
			thisPlayerVO.setIndex(0);	//첫 플레이어로 초기화
			packet.setPlayerVO(thisPlayerVO);
			packet.setMotion(0+"");		//방장 인덱스
			packet.setAction(Protocol.MAKEROOM);
			
			Packing.sender(thisPlayerVO.getPwSocket(), packet);
			lobbyReloadBroadcast();
			
//			packet.setAction(Protocol.CHANGEMASTER);
//			Packing.sender(thisPlayerVO.getPwSocket(), packet);

			break;

		case Protocol.EXITROOM:
			Room room = ro.getRoom(thisPlayerVO.getRoomNo());
			room.exitPlayer(thisPlayerVO);
			thisPlayerVO.setRoomNo(0);
			room.roomSpeakerNotThisPlayer(packet, thisPlayerVO.getNo());
			
			break;

		case Protocol.ENTERROOM:
			int roomNo = packet.getPlayerVO().getRoomNo();	//입장할 방 번호 받음
			thisPlayerVO.setRoomNo(roomNo);
			int index = ro.joinRoom(roomNo, thisPlayerVO);
			thisPlayerVO.setIndex(index);	//자신이 몇번쨰 index인지 저장
			Packet pak = new Packet(Protocol.ENTEROTHERROOM, thisPlayerVO);
			pak.setMotion(index +"");
			pak.setPlayerVO(thisPlayerVO);
			ro.getRoom(roomNo).roomSpeakerNotThisPlayer(pak,thisPlayerVO.getNo());
			packet.setRoomPlayerList(ro.getRoom(roomNo).getList());
			packet.setPlayerVO(thisPlayerVO);
			packet.setMotion(ro.getRoom(roomNo).getMasterIndex().toString());
			Packing.sender(thisPlayerVO.getPwSocket(), packet);
			
			lobbyExitBroadcast();

			break;

		case Protocol.EXITLOBBY:
			for (int i = 0; i < lobbyPlayerList.size(); i++) {
				if (lobbyPlayerList.get(i).getNo() == thisPlayerVO.getNo()) {
					lobbyPlayerList.remove(i);
					break;
				}
				lobbyReloadBroadcast();				
			} //for
			
			break;

		// 로비에 있는 소켓에게 입장 playerVO와 그 소켓들의 목록을 자신 소켓에 보냄
		case Protocol.ENTERLOBBY:
			if (thisPlayerVO.getNic() == null) {
				thisPlayerVO = packet.getPlayerVO();
				thisPlayerVO.setSocketWithBrPw(socket);
			}
			lobbyPlayerList.add(thisPlayerVO); // 로비 리스트에 자신 추가
			
//			packet.setPlayerList(lobbyPlayerList); // 자신에게 로비에 출력할 입장된 사람 보냄
//			packet.setRoomMap(ro.getAllRoom());
//			Packing.sender(thisPlayerVO.getPwSocket(), packet);
			
			thisPlayerVO.saveExceptPw(dao.selectOnePlayerWithNo(thisPlayerVO.getNo()));
			lobbyReloadBroadcast();
			break;

		case Protocol.RELOADLOBBYLIST:
			packet.setRoomMap(ro.getAllRoom());
			packet.setPlayerList(lobbyPlayerList);
			
			lobbyBroadcast(packet);

			break;
			
		case Protocol.GAMESTART:
			ro.getRoom(thisPlayerVO.getRoomNo()).gameStart();
			break;
			
		case Protocol.BET:
			RoomOperator.getInstance().getRoom(thisPlayerVO.getRoomNo()).bet(packet.getMotion());
			break;
			
		case Protocol.JOIN:
			try {
				Packing.sender(thisPlayerVO.getPwSocket(), Protocol.JOIN, Integer.toString(serverDAO.playerJoin(packet.getPlayerVO())));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			break;
			
		case Protocol.SELECTID:
			Packing.sender(thisPlayerVO.getPwSocket(), Protocol.SELECTID, serverDAO.selectID(packet.getMotion()) ? "true" : "false");
			
		case Protocol.SELECTNICK:
			Packing.sender(thisPlayerVO.getPwSocket(), Protocol.SELECTNICK, serverDAO.selectNick(packet.getMotion()) ? "true" : "false");
					
		case Protocol.SELECTONEPLAYERWITHNO:
			Packing.sender(thisPlayerVO.getPwSocket(), Protocol.SELECTONEPLAYERWITHNO, serverDAO.selectOnePlayerWithNo(packet.getPlayerVO().getNo()));
					
		} // switch
	} // packetAnalysiser();
 
} // ServerPacketController();
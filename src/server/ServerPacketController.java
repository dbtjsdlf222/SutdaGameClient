package server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dao.PlayerDAO;
import operator.RoomOperator;
import util.Packing;
import vo.Packet;
import vo.PlayerVO;
import vo.Protocol;

public class ServerPacketController {
	
	private static final Logger logger = LogManager.getLogger();
	
	private PlayerVO thisPlayerVO = new PlayerVO();
	private static ArrayList<PlayerVO> lobbyPlayerList = new ArrayList<PlayerVO>();
	private ObjectMapper mapper = new ObjectMapper();
	private RoomOperator ro = RoomOperator.ro; 
	private PlayerDAO dao = new PlayerDAO();
	private Socket socket;
	private int roomIndex;
	
	public ServerPacketController(Socket socket) {
		this.socket = socket;
	}

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
			lobbyPlayerList.add(thisPlayerVO);
			packet.setAction(Protocol.ENTERLOBBY);
			this.packetAnalysiser(packet);

			String id = packet.getPlayerVO().getID();
			String pw = packet.getPlayerVO().getPassword();

			PlayerVO vo = dao.login(id, pw);

			if (vo != null)
				thisPlayerVO = vo;

			try {
				packet.setPlayerVO(vo);
				PrintWriter socketPw;
				socketPw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
				Packing.sender(socketPw, Protocol.LOGIN, vo);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
			break;

		case Protocol.MAKEROOM:
			thisPlayerVO.setRoomNo(ro.makeRoom(thisPlayerVO));
			packet.getPlayerVO().setRoomNo(thisPlayerVO.getRoomNo());
			lobbyExitBroadcast();
			
			ro.getRoom(thisPlayerVO.getRoomNo()).setMasterNo(thisPlayerVO.getNo());
			thisPlayerVO.setIndex(0);	//첫 플레이어로 초기화
			roomIndex = 0;
			packet.setPlayerVO(thisPlayerVO);
			packet.setAction(Protocol.ENTEROTHERROOM);
			Packing.sender(thisPlayerVO.getPwSocket(), packet);
			lobbyReloadBroadcast();
			
			packet.setAction(Protocol.CHANGEMASTER);
			packet.setMotion(0+"");
			Packing.sender(thisPlayerVO.getPwSocket(), packet);
//			this.packetAnalysiser(packet.setAction(Protocol.CHANGEMASTER));

			break;

		case Protocol.EXITROOM:
			Room room = ro.getRoom(thisPlayerVO.getRoomNo());
			room.exitPlayer(thisPlayerVO);
			packet.setAction(Protocol.EXITOTHERROOM);
			packet.setMotion(roomIndex+"");
			room.roomSpeaker(packet);
			if (ro.getRoom(thisPlayerVO.getRoomNo()).getList().size() <= 0) {
				ro.removeRoom(thisPlayerVO.getRoomNo());
				this.lobbyReloadBroadcast();
			}
			thisPlayerVO.setRoomNo(0);
			
			break;

		case Protocol.ENTERROOM:
			int roomNo = packet.getPlayerVO().getRoomNo();	//입장할 방 번호 받음
			thisPlayerVO.setRoomNo(roomNo);
			Packet pak = new Packet(Protocol.ENTEROTHERROOM, thisPlayerVO);
			int index = ro.joinRoom(roomNo, thisPlayerVO);
			roomIndex = index;	//index 저장
			thisPlayerVO.setIndex(index);	//자신이 몇번쨰 index인지 저장
			pak.setMotion(index +"");
			ro.getRoom(roomNo).roomSpeaker(pak);
			packet.setRoomPlayerList(ro.getRoom(roomNo).getList());
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
			packet.setPlayerList(lobbyPlayerList); // 자신에게 로비에 출력할 입장된 사람 보냄
			packet.setRoomMap(ro.getAllRoom());
			
			lobbyReloadBroadcast();

//			Packing.sender(thisPlayerVO.getPwSocket(), packet);
			break;

		case Protocol.RELOADLOBBYLIST:
			packet.setRoomMap(ro.getAllRoom());
			packet.setPlayerList(lobbyPlayerList);
			
			lobbyBroadcast(packet);

			break;
			
		case Protocol.GAMESTART:
			ro.getRoom(thisPlayerVO.getRoomNo()).gameStart();
			
		} // switch
	} // runMainGame
 
	public void exitPlayer() {

		if (thisPlayerVO.getRoomNo() != 0) {
			ro.getRoom(thisPlayerVO.getRoomNo()).exitPlayer(thisPlayerVO);
			Packet packet = new Packet();
			packet.setPlayerVO(thisPlayerVO);
			ro.getRoom(thisPlayerVO.getRoomNo()).roomSpeaker(new Packet(Protocol.MESSAGE, "알림 [" + thisPlayerVO.getNic() + "]님이 퇴실하셨습니다."));
			packet.setAction(Protocol.EXITOTHERROOM);
			packet.setMotion(thisPlayerVO.getIndex()+"");
			ro.getRoom(thisPlayerVO.getRoomNo()).roomSpeaker(packet);
			if (ro.getRoom(thisPlayerVO.getRoomNo()).getList().size() <= 0) {
				ro.removeRoom(thisPlayerVO.getRoomNo());
				this.lobbyReloadBroadcast();
			}
		} else {
			lobbyExitBroadcast();
		} // if~else
		
	} //exitPlayer

	public void lobbyBroadcast(Packet packet) {
		for (int i = 0; i < lobbyPlayerList.size(); i++) {
			Packing.sender(lobbyPlayerList.get(i).getPwSocket(), packet);
		} //for
	} //broadcast

	public void lobbyExitBroadcast() {
		for (int i = 0; i < lobbyPlayerList.size(); i++) {
			if(lobbyPlayerList.get(i).getNo() == thisPlayerVO.getNo()) {
				lobbyPlayerList.remove(i);
			}
		} //for
		Packet packet = new Packet();
		packet.setAction(Protocol.RELOADLOBBYLIST);
		packet.setPlayerList(lobbyPlayerList);
		packet.setRoomMap(ro.getAllRoom());
		for(PlayerVO player : lobbyPlayerList)
			Packing.sender(player.getPwSocket(), packet);
	}
	
	public void lobbyReloadBroadcast() {
		Packet packet = new Packet();
		packet.setAction(Protocol.RELOADLOBBYLIST);
		packet.setPlayerList(lobbyPlayerList);
		packet.setRoomMap(ro.getAllRoom());
		for (int i = 0; i < lobbyPlayerList.size(); i++) {
			Packing.sender(lobbyPlayerList.get(i).getPwSocket(), packet);
		} //for
	}

	public PlayerVO getThisPlayerVO() { return thisPlayerVO; }
	
}

package server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import com.fasterxml.jackson.core.JsonProcessingException;

import operator.RoomOperator;
import util.Packing;
import vo.Packet;
import vo.PlayerVO;
import vo.Protocol;

public class ServerPacketController extends ServerMethod {
	
	public ServerPacketController(Socket socket) {
		super.socket = socket;
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
			
			thisPlayerVO.setRoomNo(0);
			room.exitPlayer(thisPlayerVO);
			
			room.roomSpeakerNotThisPlayer(packet, thisPlayerVO.getNo());
			
			
			thisPlayerVO.setRoomNo(0);
			
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
			break;
			
		case Protocol.BET:
			RoomOperator.getInstance().getRoom(thisPlayerVO.getRoomNo()).bet(packet.getMotion());
			break;
		} // switch
	} // runMainGame
 
	
}

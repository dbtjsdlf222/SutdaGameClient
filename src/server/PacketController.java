package server;

import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import operator.RoomOperator;
import vo.Packet;
import vo.PlayerVO;
import vo.Protocol;

public class PacketController {
	
	private PlayerVO thisPlayerVO;
	private static ArrayList<PlayerVO> lobbyPlayerList = new ArrayList<PlayerVO>();
	private ObjectMapper mapper = new ObjectMapper();
	private RoomOperator ro = RoomOperator.getRoomOperator();
	
	public PacketController() { }
	
	public void packetAnalysiser(Packet packet) throws JsonProcessingException {

		switch (packet.getAction()) {
		case Protocol.MESSAGE:
			if (thisPlayerVO.getLocation().equals(Protocol.LOBBY)) {
				for (PlayerVO playerVO : lobbyPlayerList) {
					playerVO.getPwSocket().println(mapper.writeValueAsString(packet));
				}
			} else 
				ro.getRoom(packet.getPlayerVO().getRoomNo()).roomSpeaker(packet);
			
			break;

		case Protocol.FIRSTENTER:
			thisPlayerVO = packet.getPlayerVO();
			lobbyPlayerList.add(thisPlayerVO);
			packet.setAction(Protocol.ENTERLOBBY);
			this.packetAnalysiser(packet);
			break;

		case Protocol.EXITROOM:
			ro.getRoom(packet.getPlayerVO().getRoomNo()).exitPlayer(thisPlayerVO);
			ro.getRoom(packet.getPlayerVO().getRoomNo()).roomSpeaker(packet);
			thisPlayerVO.setRoomNo(0);
			
			//로비에 있는 소켓에게 입장 playerVO와 그 소켓들의 목록을 자신 소켓에 보냄
		case Protocol.ENTERLOBBY:
			for (int i = 0; i < lobbyPlayerList.size(); i++) 
				lobbyPlayerList.get(i).getPwSocket().println(thisPlayerVO);
			
			packet.setPlayerList(lobbyPlayerList);
			thisPlayerVO.getPwSocket().println(packet);
			break;

		case Protocol.MAKEROOM:
			ro.makeRoom(packet.getPlayerVO());
			break;
			
		case Protocol.LISTROOMPLAYER:
			packet.getPlayerVO().getPwSocket().println(mapper);
			break;
			
		case Protocol.ENTERROOM:
			packet.getRoom();
			break;
		} // switch
	} // runMainGame
	
//	public void exitPlayer() {
//		
//		System.err.println(thisPlayerVO.getNic() + "님이 나가셨습니다.");
//		
//		for (int j = 0; j < ; j++) {
//			if (playerList.get(j).getNo() == thisPlayerVO.getNo())
//				playerList.remove(j);
//		}
//	}
//
//	public void localBroadcasting(Packet packet) throws JsonProcessingException {
//		for (int i = 0; i < playerList.size(); i++) {
//			if (packet.getPlayerVO().getLocation().equals(playerList.get(i).getLocation()))
//				playerList.get(i).getPwSocket().println(mapper.writeValueAsString(packet));
//		}
//	}

	
}

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
				
				lobbyPlayerList.get(i).getPwSocket().println(new Packet(Protocol.OTHERPLAYERENTHER,thisVoToString()));
			
			packet.setPlayerList(lobbyPlayerList);
			thisPlayerVO.getPwSocket().println(packet);
			break;

		case Protocol.MAKEROOM:
			ro.makeRoom(packet.getPlayerVO());
			break;
			
		case Protocol.ENTERROOM:
			packet.getRoom();
			break;
		} // switch
	} // runMainGame
	
	public void exitPlayer() {
		
		if(thisPlayerVO.getRoomNo()!=0) {
			ro.getRoom(thisPlayerVO.getRoomNo()).roomSpeaker(new Packet(Protocol.MESSAGE,"["+thisPlayerVO.getNic()+"]님이 퇴실하셨습니다."));
			ro.getRoom(thisPlayerVO.getRoomNo()).roomSpeaker(new Packet(Protocol.EXITROOM,thisVoToString()));
			ro.getRoom(thisPlayerVO.getRoomNo()).exitPlayer(thisPlayerVO);
		} else {
			for (int i = 0; i < lobbyPlayerList.size(); i++) {
				lobbyPlayerList.get(i).getPwSocket().println(new Packet(Protocol.EXITLOBBY,thisPlayerVO));
				if(lobbyPlayerList.get(i).getNo()==thisPlayerVO.getNo()){
					lobbyPlayerList.remove(i);
					break;
				}
			}
		} // if~else
		System.err.println(thisPlayerVO.getNic() + "님이 나가셨습니다.");
	}

	public void lobbyBroadcast(Packet packet) {
		for (int i = 0; i < lobbyPlayerList.size(); i++) {
			lobbyPlayerList.get(i).getPwSocket().println(packet);
		}
	}
	
	public String thisVoToString() {
		try {
			return mapper.writeValueAsString(thisPlayerVO);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} 
		return null;
		
	}
}

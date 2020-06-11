package server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dao.PlayerDAO;
import operator.RoomOperator;
import vo.Packet;
import vo.PlayerVO;
import vo.Protocol;

public class ServerPacketController {
	
	private PlayerVO thisPlayerVO;
	private static ArrayList<PlayerVO> lobbyPlayerList = new ArrayList<PlayerVO>();
	private ObjectMapper mapper = new ObjectMapper();
	private RoomOperator ro = RoomOperator.getRoomOperator();
	private PlayerDAO dao = new PlayerDAO();
	private Socket socket;
	
	public ServerPacketController(Socket socket) {
		this.socket = socket;
	}
	
	
	public void packetAnalysiser(Packet packet) throws JsonProcessingException {

		switch (packet.getAction()) {
		case Protocol.MESSAGE:
			if (thisPlayerVO.getRoomNo()==0) {
				for (PlayerVO playerVO : lobbyPlayerList) {
					playerVO.getPwSocket().println(mapper.writeValueAsString(packet));
				}
			} else 
				ro.getRoom(packet.getPlayerVO().getRoomNo()).roomSpeaker(packet);
			
			break;

		case Protocol.LOGIN:
			lobbyPlayerList.add(thisPlayerVO);
			packet.setAction(Protocol.ENTERLOBBY);
			this.packetAnalysiser(packet);
			
			String id = packet.getPlayerVO().getID();
			String pw = packet.getPlayerVO().getPassword();
			
			PlayerVO vo = dao.login(id, pw);
			
			if(vo!=null) 
				thisPlayerVO = vo;
			
			try {
				packet.setPlayerVO(vo);
				PrintWriter socketPw;
				socketPw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
				socketPw.println(mapper.writeValueAsString(new Packet(Protocol.LOGIN,vo)));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			break;

		case Protocol.EXITROOM:
			ro.getRoom(packet.getPlayerVO().getRoomNo()).exitPlayer(thisPlayerVO);
			ro.getRoom(packet.getPlayerVO().getRoomNo()).roomSpeaker(packet);
			thisPlayerVO.setRoomNo(0);
			if(ro.getRoom(packet.getPlayerVO().getRoomNo()).getList().size() <= 0) {
				ro.removeRoom(packet.getPlayerVO().getRoomNo());
			}
			
			//로비에 있는 소켓에게 입장 playerVO와 그 소켓들의 목록을 자신 소켓에 보냄
		case Protocol.ENTERLOBBY:
			for (int i = 0; i < lobbyPlayerList.size(); i++)
				lobbyPlayerList.get(i).getPwSocket().println(new Packet(Protocol.ENTEROTHERLOBBY,thisPlayerVO));
			
			packet.setPlayerList(lobbyPlayerList);	//자신에게 로비에 출력할 입장된 사람 보냄
			lobbyPlayerList.add(thisPlayerVO);		//로비 리스트에 자신 추가
			packet.setRoomMap(ro.getAllRoom());
			
			thisPlayerVO.getPwSocket().println(packet);
			break;

		case Protocol.MAKEROOM:
			ro.makeRoom(packet.getPlayerVO());
			packet.setAction(Protocol.EXITLOBBY);
			this.packetAnalysiser(packet);
			break;
			
		case Protocol.ENTERROOM:
			int roomNo = packet.getPlayerVO().getRoomNo();
			ro.getRoom(roomNo).roomSpeaker(new Packet(Protocol.ENTEROTHERROOM,thisPlayerVO));
			ro.joinRoom(roomNo, thisPlayerVO);
			thisPlayerVO.setRoomNo(roomNo);
			
		case Protocol.EXITLOBBY:
			for (int i = 0; i < lobbyPlayerList.size(); i++) {
				if(lobbyPlayerList.get(i).getNo()==thisPlayerVO.getNo())
					lobbyPlayerList.remove(i);
				
				this.lobbyBroadcast(new Packet(Protocol.EXITOTHERLOBBY,thisPlayerVO));
			}
			
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

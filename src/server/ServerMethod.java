package server;

import java.net.Socket;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.PlayerDAO;
import operator.RoomOperator;
import util.Packing;
import vo.Packet;
import vo.PlayerVO;
import vo.Protocol;

public class ServerMethod {
	protected static final Logger logger = LogManager.getLogger();
	protected PlayerVO thisPlayerVO = new PlayerVO();
	protected static ArrayList<PlayerVO> lobbyPlayerList = new ArrayList<PlayerVO>();
	protected RoomOperator ro = RoomOperator.getInstance(); 
	protected PlayerDAO dao = new PlayerDAO();
	protected Socket socket;
	
	public void exitPlayer() {

		if (thisPlayerVO.getRoomNo() != 0) {
			ro.getRoom(thisPlayerVO.getRoomNo()).exitPlayer(thisPlayerVO);
//			Packet packet = new Packet();
//			packet.setPlayerVO(thisPlayerVO);
//			packet.setAction(Protocol.EXITOTHERROOM);
//			packet.setMotion(thisPlayerVO.getIndex()+"");
//			ro.getRoom(thisPlayerVO.getRoomNo()).roomSpeaker(packet);
			
//			if (ro.getRoom(thisPlayerVO.getRoomNo()).getList().size() <= 0) {
//				ro.removeRoom(thisPlayerVO.getRoomNo());
//				lobbyReloadBroadcast();
//			}
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

	public static ArrayList<PlayerVO> getLobbyPlayerList() {
		return lobbyPlayerList;
	}
	
	
}

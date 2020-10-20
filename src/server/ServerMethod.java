package server;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.ServerDAO;
import operator.RoomOperator;
import util.Packing;
import vo.Packet;
import vo.PlayerVO;
import vo.Protocol;

public class ServerMethod {
	protected static final Logger logger = LogManager.getLogger();
	protected PlayerVO thisPlayerVO = new PlayerVO();
	protected static Hashtable<String, PlayerVO> lobbyPlayerList = new Hashtable<String,PlayerVO>();
	protected RoomOperator ro = RoomOperator.getInstance(); 
	protected ServerDAO serverDAO = new ServerDAO();
	protected Socket socket;
	protected static Hashtable<String, PrintWriter> playerOnlineList = new Hashtable<String, PrintWriter>();
	
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
		playerOnlineList.remove(thisPlayerVO.getNic());		
	} //exitPlayer

	public void lobbyBroadcast(Packet packet) {
		for (Entry<String,PlayerVO> e : lobbyPlayerList.entrySet()) {
			Packing.sender(e.getValue().getPwSocket(), packet);
		}
	} //broadcast

	public void extraMoney() {
		serverDAO.extraMoney(thisPlayerVO);
	}
	
	public void lobbyExitBroadcast() {
		
		lobbyPlayerList.remove(thisPlayerVO.getNic());
		
		Packet packet = new Packet();
		packet.setProtocol(Protocol.RELOAD_LOBBY_LIST);
		packet.setPlayerList(lobbyPlayerList);
		packet.setRoomMap(ro.getAllRoom());
		lobbyBroadcast(packet);
	}
	
	public void lobbyReloadBroadcast() {
		Packet packet = new Packet();
		packet.setProtocol(Protocol.RELOAD_LOBBY_LIST);
		packet.setPlayerList(lobbyPlayerList);
		packet.setRoomMap(ro.getAllRoom());
		lobbyBroadcast(packet);
	}

	public PlayerVO getThisPlayerVO() { return thisPlayerVO; }

	public static Map<String, PlayerVO> getLobbyPlayerList() {
		return lobbyPlayerList;
	}
	
	public static void main(String[] args) {
		long n=17;
		System.out.println(f(n));
	}
	public static long f(long n) {
		if(n==0) {
			return 1;
		}else {
			return n*f(n-1);
		}
	}
}

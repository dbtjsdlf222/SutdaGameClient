package client.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import server.Room;
import util.Packing;
import util.Protocol;
import vo.Packet;
import vo.PlayerVO;

public class ClientPacketSender {
	public static ClientPacketSender instance = new ClientPacketSender();

	private ClientPacketSender() {}

	public void enterLobby() {
		Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.ENTER_LOBBY, PlayerVO.myVO);
	} // enterLobby();

	public void makeRoom(Room room) {
		Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.MAKE_ROOM, room);
	} // makeRoom();

	public void enterRoom(int roomNo) {
		PlayerVO.myVO.setRoomNo(roomNo);
		Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.ENTER_ROOM, PlayerVO.myVO);
	} // enterRoom();
	public void reloadPlayerList() {
		Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.RELOAD_PlAYERLIST, PlayerVO.myVO);
	}

	public void exitRoom() {
		Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.EXIT_ROOM, PlayerVO.myVO);
	} // exitRoom();

	public void kick(String nic) {
		Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.KICK, nic);
	} // exitRoom();

	public void login(String id, String pw) {
		Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.LOGIN, new PlayerVO(id, pw));
	} // login();
	
	public void offline(PlayerVO vo) {
		Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.OFFLINE, vo);
	} // offline();

	public void join(PlayerVO vo) {
		Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.JOIN, vo);
	} // join();
	
	public void password(Room room) {
		Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.PASSWORD, room);
	}
	
	public void selectId(String id) {
		Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.SELECT_ID, id);
	} //selectId();
	
	public void selectNick(String nick) {
		Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.SELECT_NICK, nick);
	} //selectNick();
	
	public void playerSave(PlayerVO vo) {
		Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.PLAYER_SAVE, vo);
	}
	
	public void selectOnePlayerWithNo(int playerVO) {
		Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.SELECT_ONE_PLAYER_WITH_NO, Integer.toString(playerVO));
	}
	
	public void extraMoney() {
		Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.EXTRAMONEY);
	}
	
	
	
	public boolean connectToServer(String ip,int port) {
		
		try(Socket socket = new Socket(ip, port)) {
			
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			if(br.readLine().equals(Protocol.CONNECT_SUCCESS)) {
				socket.close();
				return true;
			} else {
				socket.close();
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	} // connectToServer();
} // class
package client.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import server.Room;
import util.Packing;
import vo.PlayerVO;
import vo.Protocol;

public class ClientPacketSender {
	public static ClientPacketSender instance = new ClientPacketSender();

	private ClientPacketSender() {}

	public void enterLobby() {
		Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.ENTERLOBBY, PlayerVO.myVO);
	} // enterLobby();

	public void makeRoom(Room room) {
		Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.MAKEROOM, room);
	} // makeRoom();

	public void enterRoom(int roomNo) {
		PlayerVO.myVO.setRoomNo(roomNo);
		Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.ENTERROOM, PlayerVO.myVO);
	} // enterRoom();

	public void exitRoom() {
		Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.EXITROOM, PlayerVO.myVO);
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
	
	public void selectId(String id) {
		Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.SELECTID, id);
	} //selectId();
	
	public void selectNick(String nick) {
		Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.SELECTNICK, nick);
	} //selectNick();
	
	public void playerSave(PlayerVO vo) {
		Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.PLAYERSAVE, vo);
	}
	
	public void selectOnePlayerWithNo(int playerVO) {
		Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.SELECTONEPLAYERWITHNO, Integer.toString(playerVO));
	}
	
	
	
	public boolean connectToServer(String ip) {
		
		try(Socket socket = new Socket(ip, 4888)) {
			
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			if(br.readLine().equals(Protocol.CONNECTSUCCESS)) {
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
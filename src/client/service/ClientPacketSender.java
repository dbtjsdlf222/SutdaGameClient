package client.service;

import util.Packing;
import vo.PlayerVO;
import vo.Protocol;

public class ClientPacketSender {
	public static ClientPacketSender instance = new ClientPacketSender();

	private ClientPacketSender() {}

	public void enterLobby() {
		Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.ENTERLOBBY, PlayerVO.myVO);
	} // enterLobby();

	public void makeRoom() {
		Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.MAKEROOM, PlayerVO.myVO);
	} // makeRoom();

	public void enterRoom(int roomNo) {
		PlayerVO.myVO.setRoomNo(roomNo);
		Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.ENTERROOM, PlayerVO.myVO);
	} // enterRoom();

	public void exitRoom() {
		Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.EXITROOM, PlayerVO.myVO);
	} // exitRoom();

	public void login(String id, String pw) {
		PlayerVO.myVO.setID(id);
		PlayerVO.myVO.setPassword(pw);
		Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.LOGIN, PlayerVO.myVO);
	} // login();
	
	public PlayerVO getVo() { return PlayerVO.myVO; }
	
} // class

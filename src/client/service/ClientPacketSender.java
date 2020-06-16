package client.service;

import java.io.PrintWriter;
import java.net.Socket;

import util.Packing;
import vo.PlayerVO;
import vo.Protocol;

public class ClientPacketSender {

	public static ClientPacketSender instance = new ClientPacketSender();
	private Socket socket;
	private PlayerVO vo;
	private PrintWriter pw;

	private ClientPacketSender() {}

	public void setClientPacketSender(PlayerVO vo) {
		System.out.println("setClientPacketSender: " + vo);
		this.socket = vo.getSocket();
		this.vo = vo;
		this.pw = vo.getPwSocket();
	} // setClientPacketSender();

	public void enterLobby() {
		System.out.println("enterLobby작동함 pw : " + pw);
		Packing.sender(pw, Protocol.ENTERLOBBY, vo);
	} // enterLobby();

	public void makeRoom() {
		System.out.println("makeroom작동함 pw : " + pw);
		Packing.sender(pw, Protocol.MAKEROOM, vo);
	} // makeRoom();

	public void enterRoom(int roomNo) {
		vo.setRoomNo(roomNo);
		Packing.sender(pw, Protocol.ENTERROOM, vo);
	} // enterRoom();

	public void exitRoom() {
		Packing.sender(pw, Protocol.EXITROOM, vo);
	} // exitRoom();

	public void login(String id, String pw) {
		vo.setID(id);
		vo.setPassword(pw);
		Packing.sender(this.pw, Protocol.LOGIN, vo);
	} // login();
	
	public PlayerVO getVo() { return vo; }
	
} // class

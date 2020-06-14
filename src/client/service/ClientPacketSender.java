package client.service;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import client.ui.MainScreen;
import util.Packing;
import vo.Packet;
import vo.PlayerVO;
import vo.Protocol;

public class ClientPacketSender {
	private Socket socket;
	private PlayerVO vo;
	private ObjectMapper mapper = new ObjectMapper();
	private PrintWriter pw;

	public ClientPacketSender(Socket socket, PlayerVO vo) {
		this.socket = socket;
		this.vo = vo;
		try {
			this.pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	} //con

	public void makeRoom() {
		Packing.sender(pw, Protocol.MAKEROOM, vo);
	} //makeRoom
	
	public void enterRoom() {
		Packing.sender(pw, Protocol.ENTERROOM, vo);
	} //enterRoom
	
	public void exitRoom() {
		Packing.sender(pw, Protocol.EXITROOM, vo);
	} //exitRoom
	
	public void login(String id, String pw) {
		vo.setID(id);
		vo.setPassword(pw);
		Packing.sender(this.pw, Protocol.LOGIN, vo);
	}
} //class

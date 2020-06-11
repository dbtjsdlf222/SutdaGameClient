package client.service;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;

import vo.PlayerVO;
import vo.Protocol;

public class ClientPacketSender {
	private Socket socket;
	private PlayerVO vo;
	private ObjectMapper mapper = new ObjectMapper();
	private PrintWriter pw;
			
	public void makeRoom() {
		pw.println(mapper.writeValueAsString(Protocol.MAKEROOM,
				vo));
	}
	
	public ClientPacketSender(Socket socket, PlayerVO vo) {
		this.socket = socket;
		this.vo = vo;
		try {
			this.pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}

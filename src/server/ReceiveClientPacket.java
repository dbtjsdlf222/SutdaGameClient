package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import vo.Protocol;
import vo.Packet;
import vo.PlayerVO;

public class ReceiveClientPacket extends Thread { // Server

	private Socket socket;
	private PlayerVO thisPlayerVO;
	public static ArrayList<PlayerVO> playerList = new ArrayList<PlayerVO>();

	@Override
	public void run() {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
			ObjectMapper mapper = new ObjectMapper();

			try {
				while (true) {
					String packetStr = br.readLine();
//					System.out.println(packetStr);
					Packet packet = mapper.readValue(packetStr, Packet.class);
					analysisPacket(packet); // action에 따라서 동작 실행
				}
			} catch (SocketException e) {
				this.exitPlayer(thisPlayerVO);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} // try~catch
	} // run()

	public ReceiveClientPacket(Socket socket) {
		this.socket = socket;
	}
	
	public void exitPlayer(PlayerVO vo) {
		System.err.println(thisPlayerVO.getNic()+"님이 나가셨습니다.");
		for (int j = 0; j < playerList.size(); j++) {
			if(playerList.get(j).getNo()==thisPlayerVO.getNo())
				playerList.remove(j);
		}
	}
	
	public void analysisPacket(Packet packet) {
		ObjectMapper mapper = new ObjectMapper();

		switch (packet.getAction()) {
		case Protocol.MESSAGE:
			try {
				for (int j = 0; j < playerList.size(); j++) {
					if (packet.getPlayerVO().getLocation().equals(playerList.get(j).getLocation())) {
						PrintWriter pw = playerList.get(j).getPwSocket();
						pw.println(mapper.writeValueAsString(packet));
					} // if
				} // for

			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case Protocol.JOINPLAYER:
			thisPlayerVO = packet.getPlayerVO();
			thisPlayerVO.setSocketWithBrPw(socket);
			playerList.add(thisPlayerVO);
			break;
			
		case Protocol.CHANGELOCATION:
			

		} // switch
	} // runMainGame
} // ReceiveClientPacket

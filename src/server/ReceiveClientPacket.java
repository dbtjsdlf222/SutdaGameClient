package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import javazoom.jl.player.Player;
import vo.Protocol;
import vo.Packet;
import vo.PlayerVO;
import vo.Room;

public class ReceiveClientPacket extends Thread { // Sever

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
					System.out.println(packetStr);
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
				// RoomOperator operator = RoomOperator.getRoomOperator();
				// Room room = operator.getRoom(packet.getRoomNo());
				// room.roomChat(packet);
				// System.out.println(packet);
				for (int j = 0; j < PlayerVO.playerList.size(); j++) {
					if (packet.getPlayerVO().getLocation().equals(PlayerVO.playerList.get(j).getLocation())) {
						PrintWriter pw = PlayerVO.playerList.get(j).getPwSocket();
						pw.println(packet.getMotion());
					} // if
				} // for
				packet.getPlayerVO().getLocation();

			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case Protocol.JOINPLAYER:
			playerList.add(packet.getPlayerVO());
			thisPlayerVO = packet.getPlayerVO();
			break;

		} // switch
	} // runMainGame
} // ReceiveClientPacket

package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;

import javazoom.jl.player.Player;
import vo.Protocol;
import vo.Packet;
import vo.PlayerVO;
import vo.Room;

public class ReceiveClientPacket extends Thread {

	private Socket socket;

	@Override
	public void run() {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
			ObjectMapper mapper = new ObjectMapper();

			while (true) {
				String packetStr = br.readLine();
				Packet packet = mapper.convertValue(packetStr, Packet.class);
				analysisPacket(packet);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} // try~catch
	} // run()

	public ReceiveClientPacket(Socket socket) {
		this.socket = socket;
	}

	public void analysisPacket(Packet packet) {
		switch (packet.getAction()) {
		case Protocol.MESSAGE:
			try {
//				RoomOperator operator = RoomOperator.getRoomOperator();
//				Room room = operator.getRoom(packet.getRoomNo());
//				room.roomChat(packet);
//				System.out.println(packet);
				for (int j = 0; j < PlayerVO.playerList.size(); j++) {
					if(packet.getPlayerVO().getLocation().equals(PlayerVO.playerList.get(j).getLocation())) {
						PrintWriter pw = PlayerVO.playerList.get(j).getPwSocket();
						pw.println(packet.getMotion());
					} //if
				} //for
				packet.getPlayerVO().getLocation();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		} // switch
	} // runMainGame
} // remote

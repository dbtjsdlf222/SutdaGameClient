package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;

import vo.Packet;
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
		case OrderType.MESSAGE:
			RoomOperator operator = RoomOperator.getRoomOperator();
			Room room = operator.getRoom(packet.getRoomNo());
			room.roomChat(packet.getMotion());
			break;
		} // switch

	} // runMainGame
} // remote

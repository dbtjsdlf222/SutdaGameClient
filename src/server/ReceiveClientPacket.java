package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

import com.fasterxml.jackson.databind.ObjectMapper;

import vo.Packet;

public class ReceiveClientPacket extends Thread { // Server

	private Socket socket;

	public ReceiveClientPacket(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		PacketController packetController = new PacketController();
		ObjectMapper mapper = new ObjectMapper();

		try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

			try {
				while (true) {
					String packetStr = br.readLine();
					System.out.println(packetStr);
					Packet packet = mapper.readValue(packetStr, Packet.class);
					try {
						packetController.packetAnalysiser(packet); // action에 따라서 동작 실행
					} catch (Exception e) {
						e.printStackTrace();
					}
				} // while
			} catch (SocketException e) {
//				packetController.exitPlayer();
			} // try~catch

		} catch (IOException e) {
			e.printStackTrace();
		} // try~catch
	} // run()

} // ReceiveClientPacket
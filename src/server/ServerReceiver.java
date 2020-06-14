package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

import com.fasterxml.jackson.databind.ObjectMapper;

import vo.Packet;

public class ServerReceiver extends Thread { // Server

	private Socket socket;

	public ServerReceiver(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		ServerPacketController packetController = new ServerPacketController(socket);
		ObjectMapper mapper = new ObjectMapper();

		try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

			try {
				while (true) {
					String packetStr = br.readLine();
					try {
						Packet packet = mapper.readValue(packetStr, Packet.class);
						packetController.packetAnalysiser(packet); // action에 따라서 동작 실행
					}catch(NullPointerException e){
						e.printStackTrace();
						System.err.println("packetStr: "+packetStr);
				}catch (Exception e) {
						System.out.println(packetStr);
						e.printStackTrace();
						break;
					}
				} // while
			} catch (SocketException e) {
				packetController.exitPlayer();
			} // try~catch

		} catch (IOException e) {
			e.printStackTrace();
		} // try~catch
	} // run()

} // ReceiveClientPacket
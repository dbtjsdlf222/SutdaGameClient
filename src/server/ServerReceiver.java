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
			String packetStr = "";
			try {
				while (true) {
					packetStr = br.readLine();
					if (packetStr == null) {
						System.err.println("[Receive(ERROR(" +
											packetController.getThisPlayerVO().getNo() + ", " +
											packetController.getThisPlayerVO().getNic() +
											"))] NULL Entered");
						break;
					}
					Packet packet = mapper.readValue(packetStr, Packet.class);
					packetController.packetAnalysiser(packet); // action에 따라서 동작 실행
				} // while
			} catch (NullPointerException e) { 
				e.printStackTrace();
				
			} catch (SocketException e) {
				System.err.println("[" + e.getMessage() + 
								   "(" + packetController.getThisPlayerVO().getNo() + ", "
								   	   + packetController.getThisPlayerVO().getNic() + 
								   ")]");
				packetController.exitPlayer();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		} // try~catch;
	} // run();

} // ReceiveClientPacket();
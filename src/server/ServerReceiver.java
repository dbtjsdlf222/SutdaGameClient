package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import util.Packing;
import vo.Packet;
import vo.Protocol;

public class ServerReceiver extends Thread { // Server

	private static final Logger logger = LogManager.getLogger();

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
						logger.error("[Receive(ERROR(" +
								packetController.getThisPlayerVO().getNo() + ", " +
								packetController.getThisPlayerVO().getNic() +
								"))] NULL Entered");
						break;
					}
					Packet packet = mapper.readValue(packetStr, Packet.class);
					packetController.packetAnalysiser(packet); // action에 따라서 동작 실행
				} // while
			} catch (NullPointerException e) {
				logger.error(e.getMessage(), e);

			} catch (SocketException e) {
				logger.error("[" + e.getMessage() + 
						"(" + packetController.getThisPlayerVO().getNo() + ", "
						+ packetController.getThisPlayerVO().getNic() + 
						")]");
				packetController.exitPlayer();
			}

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	} // run();

} // ReceiveClientPacket();
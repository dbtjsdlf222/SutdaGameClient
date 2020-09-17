package client.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import vo.Packet;

public class ClientReceiver extends Thread {
	
	private static final Logger logger = LogManager.getLogger();
	
	private Socket socket;
	public ClientReceiver(Socket socket) { this.socket = socket; }

	@Override
	public void run() {
		ClientPacketController packetController = new ClientPacketController();
		String packetStr = "";
		ObjectMapper mapper = new ObjectMapper();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"))) {
			while (true) {
				packetStr = br.readLine();
				logger.debug(packetStr);
				Packet packet = mapper.readValue(packetStr, Packet.class);
				packetController.controller(packet);
			}
		} catch(java.net.SocketException e1) {
			logger.error(e1.getMessage(), e1);
		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
	} // run
} // class
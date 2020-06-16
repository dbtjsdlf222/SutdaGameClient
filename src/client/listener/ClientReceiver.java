package client.listener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JScrollPane;

import com.fasterxml.jackson.databind.ObjectMapper;

import client.service.ClientPacketController;
import operator.ChattingOperator;
import vo.Packet;

public class ClientReceiver extends Thread {

	private Socket socket;
	public ClientReceiver(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		ClientPacketController packetController = new ClientPacketController();
		String packetStr = "";
		ObjectMapper mapper = new ObjectMapper();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"))) {
			while (true) {
				packetStr = br.readLine();
				Packet packet = mapper.readValue(packetStr, Packet.class);
				packetController.controller(packet);
			}
		} catch(java.net.SocketException e1) {
			e1.printStackTrace();
			System.err.println(packetStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // run
} // class
package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;

import vo.Packet;

public class ReceiveClientPacket extends Thread {
	
	@Override
	public void run() {
		
	}

	public ReceiveClientPacket(Socket socket) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))){
			
		while (true) {
			String packetStr = br.readLine();
			ObjectMapper json = new ObjectMapper();
			Packet packet = json.convertValue(json, Packet.class);
			
		}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getPacket(int key) {

		switch (key) {
		case 1:
			break;
		} // switch

	} // runMainGame
} // remote

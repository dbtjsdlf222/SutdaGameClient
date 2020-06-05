package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;

import vo.Packet;

public class ReceiveClientPacket extends Thread {
	
	private Socket socket;
	
	@Override
	public void run() {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))){
			
		while (true) {
			String packetStr = br.readLine();
			ObjectMapper mapper = new ObjectMapper();
			Packet packet = mapper.convertValue(mapper, Packet.class);
			System.out.println("성공 : "+packet);
		}
			
		} catch (IOException e) {
			e.printStackTrace();
		} //try~catch
	} //run()

	public ReceiveClientPacket(Socket socket) {
		System.out.println(socket);
		this.socket = socket;
	}

	public void getPacket(int key) {

		switch (key) {
		case 1:
			break;
		} // switch

	} // runMainGame
} // remote

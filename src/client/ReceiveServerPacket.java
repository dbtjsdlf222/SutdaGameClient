package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;

import vo.Packet;

public class ReceiveServerPacket extends Thread {
	private Socket socket;
	
	public ReceiveServerPacket(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		ObjectMapper mapper = new ObjectMapper();
		try(BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"))) {
			System.out.println(socket.getLocalAddress());
			while (true) {
				String packetStr = br.readLine();
				Packet packet = mapper.readValue(packetStr, Packet.class);
				System.out.println(packet.toString());
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
	} //run
} //class
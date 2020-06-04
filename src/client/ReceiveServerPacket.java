package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;

import vo.Packet;

public class ReceiveServerPacket extends Thread {
	Socket socket;
	
	public ReceiveServerPacket(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		ObjectMapper mapper;
		try(BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"))) {
			
			while (true) {
				String packetStr = br.readLine();
				mapper = new ObjectMapper();
				Packet packet = mapper.convertValue(packetStr, Packet.class);
				System.out.println(packet.toString());
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
	} //run
} //class
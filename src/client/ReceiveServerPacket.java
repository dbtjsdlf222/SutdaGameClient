package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;

import vo.Protocol;
import vo.Packet;

public class ReceiveServerPacket extends Thread {
	private Socket socket;

	public ReceiveServerPacket(Socket socket) {
		System.out.println("socket:"+ socket);
		this.socket = socket;
	}

	@Override
	public void run() {
		ObjectMapper mapper = new ObjectMapper();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"))) {
			
			while (true) {
				String packetStr = br.readLine();
				Packet packet = mapper.readValue(packetStr, Packet.class);
				switch (packet.getAction()) {
					case Protocol.MESSAGE:
						System.out.println("ReceiveServerPacket: "+packet.getMotion()); // 작동확인함
						ChattingOperator.chatArea.append(packet.getPlayerVO().getNic()+": "+ packet.getMotion()+"\n");
					break;
				}
			}
		} catch (Exception e) {
			//종료시 실행 구역
			e.printStackTrace();
		}
	} // run
} // class
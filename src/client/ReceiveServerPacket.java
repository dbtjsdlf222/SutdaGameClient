package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JScrollPane;

import com.fasterxml.jackson.databind.ObjectMapper;

import vo.Protocol;
import vo.Packet;

public class ReceiveServerPacket extends Thread {
	private Socket socket;
	public static JScrollPane scrollPane = new JScrollPane(ChattingOperator.chatArea);

	public ReceiveServerPacket(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(6, 15, 508, 275);
		
		ObjectMapper mapper = new ObjectMapper();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"))) {
			
			while (true) {
				String packetStr = br.readLine();
//				System.out.println(packetStr);
				Packet packet = mapper.readValue(packetStr, Packet.class);
				switch (packet.getAction()) {
					case Protocol.MESSAGE:
						ChattingOperator.chatArea.append(packet.getPlayerVO().getNic()+": "+ packet.getMotion()+"\n");
						scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
						break;
				}
			}
		} catch (Exception e) {
			//종료시 실행 구역
			e.printStackTrace();
		}
	} // run
} // class
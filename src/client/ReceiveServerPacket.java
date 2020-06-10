package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JScrollPane;

import com.fasterxml.jackson.databind.ObjectMapper;

import operator.ChattingOperator;
import operator.ListViewOperator;
import vo.Protocol;
import vo.Room;
import vo.Packet;
import vo.PlayerVO;

public class ReceiveServerPacket extends Thread {
	private Socket socket;
	


	public ReceiveServerPacket(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 15, 638, 195);
		scrollPane.setBorder(null);

		ObjectMapper mapper = new ObjectMapper();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"))) {

			while (true) {
				String packetStr = br.readLine();
//				System.out.println(packetStr);
				Packet packet = mapper.readValue(packetStr, Packet.class);

				
			}
		} catch (Exception e) {
			// 종료시 실행 구역
			e.printStackTrace();
		}
	} // run
} // class
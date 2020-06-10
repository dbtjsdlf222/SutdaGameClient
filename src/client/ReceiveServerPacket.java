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
	public static JScrollPane scrollPane = new JScrollPane(ChattingOperator.chatArea);


	public ReceiveServerPacket(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 15, 698, 195);
		scrollPane.setBorder(null);

		ObjectMapper mapper = new ObjectMapper();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"))) {

			while (true) {
				String packetStr = br.readLine();
//				System.out.println(packetStr);
				Packet packet = mapper.readValue(packetStr, Packet.class);

				switch (packet.getAction()) {
				case Protocol.MESSAGE: // 채팅
					ChattingOperator.chatArea.append(packet.getPlayerVO().getNic() + ": " + packet.getMotion() + "\n");
					scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
					break;

				case Protocol.JOINPLAYER: // 플레이어가 입장시
					if (packet.getPlayerVO().getLocation().equals(Protocol.LOBBY)) {
						// 로비 입장시
						for (PlayerVO playervo: packet.getPlayerList()) {
							Lobby.tArea.append(("닉네임 : " + packet.getPlayerVO().getNic() + "　판수 : " + (packet.getPlayerVO().getWin()+packet.getPlayerVO().getLose())+ "　머니 : " + packet.getPlayerVO().getMoney())+ "\n");
						}
					} else {
						// 룸 입장시
					}
					
				case Protocol.EXITPLAYER:
					if (packet.getPlayerVO().getLocation().equals(Protocol.LOBBY)) {
						// 로비 퇴실시
					} else {
						// 룸 퇴실시
					}
					
				case Protocol.CHANGELOCATION:
					if (packet.getPlayerVO().getLocation().equals(Protocol.LOBBY)) {
						ArrayList<PlayerVO> lobbyPlayerList = packet.getPlayerVO().getPlayerList();
						//로비 접속중 사람 리스트
						
						ArrayList<Room> lobbyRoomList = packet.getRoom();
						//로비 룸 리스트
					} else {
						ArrayList<PlayerVO> lobbyPlayerList = packet.getPlayerVO().getPlayerList();
					}
					break;

					
				}
			}
		} catch (Exception e) {
			// 종료시 실행 구역
			e.printStackTrace();
		}
	} // run
} // class
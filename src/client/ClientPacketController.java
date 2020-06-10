package client;


import javax.swing.JScrollPane;

import operator.ChattingOperator;
import vo.Packet;
import vo.Protocol;

public class ClientPacketController {
	
	public static JScrollPane scrollPane = new JScrollPane(ChattingOperator.chatArea);
	
	public ClientPacketController() { }

	public void packetController(Packet packet) {
		switch (packet.getAction()) {
		case Protocol.MESSAGE: // 채팅
			ChattingOperator.chatArea.append(packet.getPlayerVO().getNic() + ": " + packet.getMotion() + "\n");
			scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
			break;

		case Protocol.JOINPLAYER: // 플레이어가 입장시
			System.out.println(packet.getPlayerVO().getID() + "2");
			if (packet.getPlayerVO().getLocation().equals(Protocol.LOBBY)) {
				// 로비 입장시
				System.out.println(packet.getPlayerVO().getID());
				// for (PlayerVO playervo: packet.getPlayerList()) {
				// Lobby.tArea.append(("닉네임 : " + packet.getPlayerVO().getNic() + " 판수 : " +
				// (packet.getPlayerVO().getWin()+packet.getPlayerVO().getLose())+ " 머니 : " +
				// packet.getPlayerVO().getMoney())+ "\n");
				// }
			} else {
				// 룸 입장시
			}

		} //switch
	} //method
} //class

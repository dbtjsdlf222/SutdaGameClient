package client.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.fasterxml.jackson.databind.ObjectMapper;

import client.ui.Lobby;
import operator.ChattingOperator;
import server.Room;
import vo.Packet;
import vo.PlayerVO;
import vo.Protocol;

public class ClientPacketController {

	public static JScrollPane scrollPane = new JScrollPane(ChattingOperator.chatArea);
	static String b[] = { "닉네임", "판수", "돈" };
	static String[][] n = new String[99][99];
	public static DefaultTableModel model = new DefaultTableModel(b, 0);
	public static JTable jT = new JTable(model);
	public static JScrollPane plScroll = new JScrollPane(jT);
	ArrayList<PlayerVO> lobbyPlayerList;
	
	public ClientPacketController() {
	}

	private ObjectMapper mapper = new ObjectMapper();

	public void packetController(Packet packet) {
		switch (packet.getAction()) {
		case Protocol.MESSAGE: // 채팅
			ChattingOperator.chatArea.append(packet.getPlayerVO().getNic() + ": " + packet.getMotion() + "\n");
			scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
			break;

		case Protocol.ENTERLOBBY:
			lobbyPlayerList = packet.getPlayerList();
			System.out.println("프로");

			for (int i = 0; i < lobbyPlayerList.size(); i++) {
				n[i][0] = lobbyPlayerList.get(i).getNic();
				n[i][1] = (lobbyPlayerList.get(i).getWin() + lobbyPlayerList.get(i).getLose()) + "";
				n[i][2] = lobbyPlayerList.get(i).getMoney() + "";
				model.addRow(n);
			}

			ArrayList<Room> roomList = new ArrayList<>();
			Map<Integer, Room> map = packet.getRoomMap();
			Iterator<Integer> keys = map.keySet().iterator();
			while (keys.hasNext()) {
				int key = keys.next();
				Room value = map.get(key);
				roomList.add(value);
				System.out.println("키 : " + key + ", 값 : " + value);
			}
			break;

		case Protocol.LOGIN:
			break;

		} // switch
	} // method


} // class
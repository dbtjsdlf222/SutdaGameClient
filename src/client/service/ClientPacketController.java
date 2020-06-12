package client.service;

import java.awt.Color;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

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
	@SuppressWarnings("serial")
	public static DefaultTableModel model = new DefaultTableModel(b, 0) {
		public boolean isCellEditable(int row, int column) {
		      return false;
		    }
		};
	public static JTable jT = new JTable(model);
	public static JScrollPane plScroll = new JScrollPane(jT);
	public static JPanel lobbyPan = new JPanel();
	ArrayList<PlayerVO> lobbyPlayerList;
	DecimalFormat fm = new DecimalFormat("###,###");
	
	public ClientPacketController() {
	}

	public void packetController(Packet packet) {

		switch (packet.getAction()) {
		case Protocol.MESSAGE: // 채팅
			ChattingOperator.chatArea.append(packet.getPlayerVO().getNic() + ": " + packet.getMotion() + "\n");
			scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
			break;
		case Protocol.RELOADPLAYERLIST:
		case Protocol.ENTERLOBBY:
			
			lobbyPlayerList = packet.getPlayerList();
			model = (DefaultTableModel)jT.getModel();
			model.setNumRows(0);
			for (int i = 0; i < lobbyPlayerList.size(); i++) {
				n[i][0] = lobbyPlayerList.get(i).getNic();
				n[i][1] = (lobbyPlayerList.get(i).getWin() + lobbyPlayerList.get(i).getLose()) + "";
				n[i][2] = fm.format((lobbyPlayerList.get(i).getMoney()))+"";
				model.addRow(n[i]);
			}
			model = new DefaultTableModel(b, 0) {
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			jT = new JTable(model);
			plScroll = new JScrollPane(jT);
			System.out.println("1");
			Map<Integer, Room> map = packet.getRoomMap();
			Iterator<Integer> keys = map.keySet().iterator();
			while (keys.hasNext()) {
				int key = keys.next();
				Room value = map.get(key);
				JButton jb = new JButton(
						value.getRoomNo() + "번방　　　　" + value.getPlayerSize() + "/5　　　　　" + value.getMaster() + "");
				lobbyPan.add(jb);
				System.out.println(value.getRoomNo());
			}

			break;

		case Protocol.LOGIN:
			if (packet.getPlayerVO().getNic() == null) {
				System.out.println("아이디나 비밀번호가 틀렸습니다.");
			} else {
				new Lobby(packet.getPlayerVO());
			}
			break;

		} // switch
	} // method

} // class
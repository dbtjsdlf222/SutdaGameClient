package client.service;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
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
	public static JPanel lobbyPan = new JPanel();
	ArrayList<PlayerVO> lobbyPlayerList;
	
	public ClientPacketController() {}

	private ObjectMapper mapper = new ObjectMapper();

	public void packetController(Packet packet) {
		
		switch (packet.getAction()) {
		case Protocol.MESSAGE: // 채팅
			ChattingOperator.chatArea.append(packet.getPlayerVO().getNic() + ": " + packet.getMotion() + "\n");
			scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
			break;
		case Protocol.EXITOTHERLOBBY:
		case Protocol.ENTEROTHERLOBBY:		
		case Protocol.EXITLOBBY:
		case Protocol.ENTERLOBBY:
			
			model = (DefaultTableModel)jT.getModel();
			model.setNumRows(0);
			lobbyPlayerList = packet.getPlayerList();
			System.out.println(packet.getPlayerList());
			for (int i = 0; i < lobbyPlayerList.size(); i++) {
				n[i][0] = lobbyPlayerList.get(i).getNic();
				n[i][1] = (lobbyPlayerList.get(i).getWin() + lobbyPlayerList.get(i).getLose()) + "";
				n[i][2] = lobbyPlayerList.get(i).getMoney() + "";
				model.addRow(n[i]);
			}

			Map<Integer, Room> map = packet.getRoomMap();
			Iterator<Integer> keys = map.keySet().iterator();
			while (keys.hasNext()) {
				int key = keys.next();
				Room value = map.get(key);
				JButton jb = new JButton(value.getRoomNo()+"번방　　　　"+value.getPlayerSize()+"/5　　　　　"+value.getMaster()+"");
				lobbyPan.add(jb);
				System.out.println(value.getRoomNo());
			}
			
			break;

		case Protocol.LOGIN:
			if(packet.getPlayerVO().getNic()==null) {
				System.out.println("아이디나 비밀번호가 틀렸습니다.");
			}else{
				new Lobby(packet.getPlayerVO());
			}
			break;

		} // switch
	} // method


} // class
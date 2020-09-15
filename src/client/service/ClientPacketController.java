package client.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import client.ui.Lobby;
import client.ui.RoomScreen;
import operator.ChattingOperator;
import server.Room;
import vo.Packet;
import vo.PlayerVO;
import vo.Protocol;

@SuppressWarnings("serial")
public class ClientPacketController {

	private static final Logger logger = LogManager.getLogger();

	public static JScrollPane scrollPane = new JScrollPane(ChattingOperator.chatArea);
	private static String pb[] = { "닉네임", "판수", "돈" };
	private DecimalFormat fm = new DecimalFormat("###,###");
	private static String[][] pn = new String[255][255];

	public static DefaultTableModel pLmodel = new DefaultTableModel(pb, 0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	public static JTable playerJT = new JTable(pLmodel);
	public static JScrollPane plScroll = new JScrollPane(playerJT);
	public static JPanel plobbyPan = new JPanel();

	private static String rb[] = { "방번호", "방장 닉네임", "인원", "상태" };
	public static String[][] rn = new String[99][99];

	public static DefaultTableModel rLmodel = new DefaultTableModel(rb, 0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	public static JTable roomJT = new JTable(rLmodel);
	public static JScrollPane rlScroll = new JScrollPane(roomJT);
	public static JPanel rlobbyPan = new JPanel();
	public static  JLabel userCha = new JLabel();
	
	public ClientPacketController() { }

	public void controller(Packet packet) {

		logger.info("[Receive(" + Protocol.getName(packet.getAction()) + ")] " + packet);

		switch (packet.getAction()) {
		case Protocol.MESSAGE: // 채팅
			ChattingOperator.chatArea.append(packet.getMotion() + "\n");
			scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
	
			break;

		case Protocol.ENTERLOBBY:
		case Protocol.RELOADLOBBYLIST:

			ArrayList<PlayerVO> lobbyPlayerList = packet.getPlayerList();
			// playerList
			for (int i = 0; i < ((DefaultTableModel) playerJT.getModel()).getRowCount(); i++) {
				((DefaultTableModel) playerJT.getModel()).removeRow(i);
			}
			pLmodel.getDataVector().removeAllElements();
			pLmodel.fireTableDataChanged();
			for (int i = 0; i < lobbyPlayerList.size(); i++) {
				pn[i][0] = lobbyPlayerList.get(i).getNic();
				pn[i][1] = (lobbyPlayerList.get(i).getWin() + lobbyPlayerList.get(i).getLose()) + "";
				pn[i][2] = fm.format((lobbyPlayerList.get(i).getMoney())) + "";
				pLmodel.addRow(pn[i]);
			}
			
			// roomList
			for (int i = 0; i < ((DefaultTableModel) roomJT.getModel()).getRowCount(); i++) {
				((DefaultTableModel) roomJT.getModel()).removeRow(i);
			}
			
			rLmodel.getDataVector().removeAllElements();
			rLmodel.fireTableDataChanged();
			Map<Integer, Room> map = packet.getRoomMap();
			Iterator<Integer> keys = map.keySet().iterator();
			int i = 0;
			while (keys.hasNext()) {
				int key = keys.next();
				Room value = map.get(key);
				rn[i][0] = Integer.toString(value.getRoomNo());
				rn[i][1] = value.getMaster();
				rn[i][2] = value.getPlayerSize() + "/5";
				rn[i][3] = value.isGameStarted() ? "게임중" : "대기중";
				rLmodel.addRow(rn[i]);
				i++;
			}
			break;

		case Protocol.MAKEROOM:
			RoomScreen.getInstance().mainScreen();
			RoomScreen.getInstance().enterPlayer(packet.getPlayerVO(), packet.getPlayerVO().getIndex());
			RoomScreen.getInstance().changeMaster(Integer.parseInt(packet.getMotion()));
			RoomScreen.getInstance().startBtnSet();
			break;
			
		case Protocol.ENTERROOM:
			RoomScreen.getInstance().mainScreen();
			RoomScreen.getInstance().enterPlayerList(packet.getRoomPlayerList(), packet.getPlayerVO().getIndex());
			RoomScreen.getInstance().changeMaster(Integer.parseInt(packet.getMotion()));
			break;
			
		case Protocol.ENTEROTHERROOM:
//			RoomScreen.getInstance().mainScreen();
			RoomScreen.getInstance().enterPlayer(packet.getPlayerVO(), packet.getPlayerVO().getIndex());
			break;

		case Protocol.EXITOTHERROOM:
			RoomScreen.getInstance().exitPlayer(Integer.parseInt(packet.getMotion()));
			break;

		case Protocol.CHANGEMASTER:
			RoomScreen.getInstance().changeMaster(Integer.parseInt(packet.getMotion()));
			break;

		case Protocol.CARD:
			RoomScreen.getInstance().receiveCard(packet.getCard());
			break;

		case Protocol.OPENCARD:
				RoomScreen.getInstance().openCard(packet.getRoomPlayerList());
			break;

		case Protocol.TURN:
			RoomScreen.getInstance().turn(Integer.parseInt(packet.getMotion()));	//이 차례의 사람 노란 테두리
			break;

		case Protocol.SETBUTTON:
			RoomScreen.getInstance().setButtonAndPrice(packet.getButtonArr()); // 버튼&베팅비용 세팅
			break;
			
		case Protocol.STARTPAY:
			try {
				RoomScreen.getInstance().startPay(Integer.parseInt(packet.getMotion()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		// Motion(Protocol.OTHERBET, turn + "/" + proBet +"/"+money+"/"totalMoney)
		case Protocol.OTHERBET:
			try {
				String[] sp = packet.getMotion().split("/");
				RoomScreen.getInstance().betAlert(Integer.parseInt(sp[0]), sp[1], sp[2],sp[3]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		// Motion(String winerMsg / int winerIdx / String winerHaveMoney)
		// [승자가 나갈시] Motion(String winerMsg)
		case Protocol.GAMEOVER :
			if(packet.getMotion().indexOf("/") != -1) {
				String[] strArr = packet.getMotion().split("/");
				RoomScreen.getInstance().gameOver(strArr[0],Integer.parseInt(strArr[1]),strArr[2]);
				RoomScreen.getInstance().changeMaster(Integer.parseInt(strArr[1]));
			} else {
				RoomScreen.getInstance().gameOver(packet.getMotion());
			}
			
			break;
			
		case Protocol.DRAW:
			System.out.println("[Receive]DRAW");
			JOptionPane.showMessageDialog(null, "재경기");
			break;
			
		case Protocol.RUNOUTMONEY:
			System.out.println("[Receive]RUNOUTMONEY");
			break;
			
		case Protocol.RELOADMYVO:
			PlayerVO.myVO.saveExceptPw(packet.getPlayerVO());
			break;

		case Protocol.SENDOFF:
			RoomScreen.getInstance().sendOff();
			break;
			
		} // switch
	} // controller();
} // class
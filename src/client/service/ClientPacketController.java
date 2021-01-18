package client.service;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import client.ui.Invited;
import client.ui.Lobby;
import client.ui.PasswordInput;
import client.ui.RoomScreen;
import client.ui.ShowErrorPane;
import operator.ChattingOperator;
import server.Room;
import util.MoneyFormat;
import util.Protocol;
import vo.Packet;
import vo.PlayerVO;

@SuppressWarnings("serial")
public class ClientPacketController {

	private static final Logger logger = LoggerFactory.getLogger(ClientPacketController.class);

	public static JScrollPane scrollPane = new JScrollPane(ChattingOperator.chatArea);
	private static String pb[] = { "닉네임", "판수", "돈" };
	public static String[][] pn = new String[999][999];

	public static DefaultTableModel pLmodel = new DefaultTableModel(pb, 0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	public static JTable playerJT = new JTable(pLmodel);
	public static JScrollPane plScroll = new JScrollPane(playerJT);
	public static JPanel plobbyPan = new JPanel();

	private static String rb[] = { "방번호", "공개방", "방제목", "판돈", "인원", "상태" };
	public static String[][] rn = new String[999][999];

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

		logger.info("[Receive(" + Protocol.getName(packet.getProtocol()) + ")] " + packet);

		switch (packet.getProtocol()) {
		case Protocol.SERVER_MESSAGE:
			ChattingOperator.chatArea.append("<SYSTEM> " + packet.getMotion() + "\n");
			scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
			break;
			
		case Protocol.MESSAGE: // 채팅
			ChattingOperator.chatArea.append(packet.getMotion() + "\n");
			scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
			break;

		case Protocol.WHISPER:
			ChattingOperator.chatArea.append("<귓속말> " + packet.getMotion() + "\n");
			break;
		case Protocol.RELOAD_INFO_MONEY:
		//INFO
			Lobby.getInstance().infoMoneyLbl.setText(MoneyFormat.format(PlayerVO.myVO.getMoney())+"");
		case Protocol.ENTER_LOBBY:
		case Protocol.RELOAD_LOBBY_LIST:
			Map<String,PlayerVO> lobbyPlayerList = packet.getPlayerList();
			
			
			// PlayerList
			for (int i = 0; i < ((DefaultTableModel) playerJT.getModel()).getRowCount(); i++) {
				((DefaultTableModel) playerJT.getModel()).removeRow(i);
			}
			pLmodel.getDataVector().removeAllElements();
			pLmodel.fireTableDataChanged();
			int i = 0;
			for (Entry<String, PlayerVO> e:lobbyPlayerList.entrySet()) {
				pn[i][0] = e.getValue().getNic();
				pn[i][1] = (e.getValue().getWin() + e.getValue().getLose()) + "";
				pn[i][2] = MoneyFormat.format((e.getValue().getMoney())) + "";
				pLmodel.addRow(pn[i++]);
			}
			
			// roomList
			for (int j = 0; j < ((DefaultTableModel) roomJT.getModel()).getRowCount(); j++) {
				((DefaultTableModel) roomJT.getModel()).removeRow(j);
			}
			rLmodel.getDataVector().removeAllElements();
			rLmodel.fireTableDataChanged();
			 
			roomJT.getColumn("방번호").setPreferredWidth(200);
			roomJT.getColumn("공개방").setPreferredWidth(200);
			roomJT.getColumn("방제목").setPreferredWidth(1250);
			roomJT.getColumn("판돈").setPreferredWidth(400);
			roomJT.getColumn("인원").setPreferredWidth(300);
			roomJT.getColumn("상태").setPreferredWidth(300);
			
			Map<Integer, Room> map = packet.getRoomMap();
			Iterator<Integer> keys = map.keySet().iterator();
			int z = 0;
			while (keys.hasNext()) {
				int key = keys.next();
				Room value = map.get(key);
				rn[z][0] = Integer.toString(value.getRoomNo());
				rn[z][1] = value.isPrivateRoom() ? "비공개" : "공개";
				rn[z][2] = value.getTitle();
				rn[z][3] = MoneyFormat.format(value.getStartMoney());
				rn[z][4] = value.getPlayerSize() + "/"+value.getMaxPlayer();
				rn[z][5] = value.isGameStarted() ? "게임중" : "대기중";
				rLmodel.addRow(rn[z++]);
			}
			break;
			
		case Protocol.MAKE_ROOM:
			RoomScreen.getInstance().setRoom(packet.getRoom());
			RoomScreen.getInstance().mainScreen();
			RoomScreen.getInstance().setRoomMaster(Integer.parseInt(packet.getMotion()));
			RoomScreen.getInstance().enterPlayer(packet.getPlayerVO(), packet.getPlayerVO().getIndex());
			RoomScreen.getInstance().changeMaster(Integer.parseInt(packet.getMotion()));
			RoomScreen.getInstance().startBtnSet();
			ChattingOperator.chatArea.setText("<SYSTEM> " + packet.getPlayerVO().getRoomNo()+"번방의 입장하셨습니다.\n");
			break;
			
		case Protocol.GET_INVITE:
			Invited.getInstance().setVOROOM(packet.getPlayerVO(), packet.getRoom());
				if(!Invited.getInstance().isReceiving())
					Invited.getInstance().runUI();
			break;

		case Protocol.PASSWORD:
			if (packet.getMotion().equals("true")) {
				PasswordInput.getInstance().dispose();
			} else {
				ShowErrorPane moneyErrorPane = new ShowErrorPane("비밀번호가 틀렸습니다.");
			}
			break;
			
		case Protocol.ENTER_ROOM:
			RoomScreen.getInstance().setRoom(packet.getRoom());
			RoomScreen.getInstance().mainScreen();
			RoomScreen.getInstance().setRoomMaster(Integer.parseInt(packet.getMotion()));
			
			RoomScreen.getInstance().enterPlayerList(packet.getRoomPlayerList(), packet.getPlayerVO().getIndex());
			RoomScreen.getInstance().changeMaster(Integer.parseInt(packet.getMotion()));
			ChattingOperator.chatArea.setText("<SYSTEM> " + packet.getPlayerVO().getRoomNo()+"번방의 입장하셨습니다.\n");
			break;
			
		case Protocol.ENTER_OTHER_ROOM:
//			RoomScreen.getInstance().mainScreen();
			RoomScreen.getInstance().enterPlayer(packet.getPlayerVO(), packet.getPlayerVO().getIndex());
			break;
		
		case Protocol.KICK:
			RoomScreen.getInstance().sendOff();
			ChattingOperator.chatArea.setText("<SYSTEM>" + packet.getRoom().getRoomNo() + "번방에서 추방당하셨습니다.");
			break;

		case Protocol.EXIT_ROOM:
			ChattingOperator.chatArea.setText("");
			Lobby.getInstance().lobbyScreen();
			break;

		case Protocol.EXIT_OTHER_ROOM:
			RoomScreen.getInstance().exitPlayer(Integer.parseInt(packet.getMotion()));
			break;

		case Protocol.CHANGE_MASTER:
			RoomScreen.getInstance().changeMaster(Integer.parseInt(packet.getMotion()));
			break;
		case Protocol.RELOAD_PlAYERLIST:
			RoomScreen.getInstance().enterPlayerList(packet.getRoomPlayerList(), packet.getPlayerVO().getIndex());
			break;

		case Protocol.CARD:
			RoomScreen.getInstance().receiveCard(packet.getCard());
			break;

		case Protocol.OPEN_CARD:
				RoomScreen.getInstance().openCard(packet.getRoomPlayerList());
			break;

		case Protocol.TURN:
			RoomScreen.getInstance().turn(Integer.parseInt(packet.getMotion()));	//이 차례의 사람 노란 테두리
			break;

		case Protocol.SET_BUTTON:
			RoomScreen.getInstance().setButtonAndPrice(packet.getButtonArr()); // 버튼&베팅비용 세팅
			break;
			
		case Protocol.SHOWNEEDMONEY:
			if(packet.getButtonArr() != null)
				RoomScreen.getInstance().showNeedMoney(packet.getButtonArr());
			break;
			
		case Protocol.START_PAY:
			try {
				RoomScreen.getInstance().startPay(Integer.parseInt(packet.getMotion()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		// Motion(Protocol.OTHERBET, turn + "/" + proBet +"/"+money+"/"totalMoney)
		case Protocol.OTHER_BET:
			try {
				String[] sp = packet.getMotion().split("/");
				RoomScreen.getInstance().betAlert(Integer.parseInt(sp[0]), sp[1], sp[2],sp[3]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		// Motion(String winerMsg / int winerIdx / String winerHaveMoney)
		// [승자가 나갈시] Motion(String winerMsg)
		case Protocol.GAME_OVER :
			if(packet.getMotion().indexOf("/") != -1) {
				String[] strArr = packet.getMotion().split("/");
				RoomScreen.getInstance().gameOver(strArr[0],Integer.parseInt(strArr[1]),Integer.parseInt(strArr[2])+"");
			} else {
				RoomScreen.getInstance().gameOver(packet.getMotion());
			}
			
			break;
			
		case Protocol.DRAW:
			JOptionPane.showMessageDialog(null, "재경기");
			break;
			
		case Protocol.RUN_OUT_MONEY:
			break;
			
		case Protocol.RELOAD_MY_VO:
			PlayerVO.myVO.saveExceptPw(packet.getPlayerVO());
			break;

		case Protocol.SEND_OFF:
			RoomScreen.getInstance().sendOff();
			break;

			default :
				//알수 없는 프로토콜 입니다.
			
		} // switch
	} // controller();
} // class
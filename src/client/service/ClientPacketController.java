package client.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import client.ui.Lobby;
import client.ui.RoomScreen;
import operator.ChattingOperator;
import server.Room;
import vo.Packet;
import vo.PlayerVO;
import vo.Protocol;

public class ClientPacketController {
   
   public static JScrollPane scrollPane = new JScrollPane(ChattingOperator.chatArea);
   private static String pb[] = { "닉네임", "판수", "돈" };
   private static String[][] pn = new String[99][99];
   
   public static DefaultTableModel pLmodel = new DefaultTableModel(pb, 0) {
      public boolean isCellEditable(int row, int column) {
         return false;
      }
   };
   public static JTable playerJT = new JTable(pLmodel);
   public static JScrollPane plScroll = new JScrollPane(playerJT);
   public static JPanel plobbyPan = new JPanel();

   
   private static String rb[] = { "방번호", "방장 닉네임", "인원", "상태"};
   public static String[][] rn = new String[99][99];
   
   public static DefaultTableModel rLmodel = new DefaultTableModel(rb, 0) {
      public boolean isCellEditable(int row, int column) {
         return false;
      }
   };
   public static JTable roomJT = new JTable(rLmodel);
   public static JScrollPane rlScroll = new JScrollPane(roomJT);
   public static JPanel rlobbyPan = new JPanel();
   
   DecimalFormat fm = new DecimalFormat("###,###");

   public ClientPacketController() {}

   public void controller(Packet packet) {

      switch (packet.getAction()) {
      case Protocol.MESSAGE: // 채팅
         ChattingOperator.chatArea.append(packet.getMotion() + "\n");
         scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
         break;
         
      case Protocol.RELOADLOBBYLIST:
      case Protocol.ENTERLOBBY:
         
         ArrayList<PlayerVO> lobbyPlayerList = packet.getPlayerList();
         //playerList
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
         
         //roomList
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
            System.out.println("getRoomNo(): "+value.getRoomNo());
            rn[i][0] = value.getRoomNo()+"";
            rn[i][1] = value.getMaster()+"";
            rn[i][2] = value.getPlayerSize() + "/5";
            rn[i][3] = value.isGameStarted()? "게임중" : "대기중";
            JButton jb = new JButton(value.getRoomNo()+"");
            rLmodel.addRow(rn[i]);
            i++;
         }
         break;

      case Protocol.LOGIN:
         if (packet.getPlayerVO().getNic() == null) {
            System.out.println("아이디나 비밀번호가 틀렸습니다.");
         } else {
            new Lobby();
         }
         break;
         
      case Protocol.ENTEROTHERROOM:
         RoomScreen.instance.enterPlayerOther(packet.getPlayerVO(),packet.getPlayerVO().getIndex());
         System.out.println("ENTEROTHERROOM"+packet.getPlayerVO());
         break;
         
      case Protocol.EXITOTHERROOM:
    	 System.out.println("Protocol.EXITOTHERROOM: getIndex ["+ packet.getPlayerVO().getIndex()+"]");
         RoomScreen.instance.exitPlayer(Integer.parseInt(packet.getMotion()));
         break;
         
      case Protocol.ENTERROOM:
         RoomScreen.instance.enterPlayerList(packet.getRoomPlayerList(),packet.getPlayerVO().getIndex());
         break;
        
      case Protocol.CHANGEMASTER:
//    	  MainScreen.instance.changeMaster(Integer.parseInt(packet.getMotion()));
    	  break;
      } // switch
   } // controller();
} // class
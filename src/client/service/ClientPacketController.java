package client.service;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import client.ui.Lobby;
import operator.ChattingOperator;
import server.Room;
import vo.Packet;
import vo.PlayerVO;
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

      case Protocol.ENTERLOBBY:
         ArrayList<PlayerVO> lobbyPlayerList = packet.getPlayerList();
         
         String[][] n = new String[99][99];
         Lobby.model.addRow(n);
 		Lobby.jT = new JTable(Lobby.model);
 		Lobby.plScroll = new JScrollPane(Lobby.jT);
       
 		for (int i = 0; i < lobbyPlayerList.size(); i++) {
 			n[i][0] = lobbyPlayerList.get(i).getNic();
 			n[i][1]	= (lobbyPlayerList.get(i).getWin()+lobbyPlayerList.get(i).getLose())+"";
 			n[i][2] = lobbyPlayerList.get(i).getMoney()+"";
 		}
         
         ArrayList<Room> roomList = new ArrayList<>();
         
         Map<Integer, Room> map = packet.getRoomMap();
             Iterator<Integer> keys = map.keySet().iterator();
                 while(keys.hasNext() ){
                     int key = keys.next();
                     Room value = map.get(key);
                     roomList.add(value);
                     System.out.println("키 : "+key+", 값 : "+value);
                 }
                 break;
                 
      case Protocol.LOGIN:
    	  break;
         
      } //switch
   } //method
} //class
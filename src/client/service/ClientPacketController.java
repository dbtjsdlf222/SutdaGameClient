package client.service;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JScrollPane;

import operator.ChattingOperator;
import server.Room;
import vo.Packet;
import vo.PlayerVO;
import vo.Protocol;

public class ClientPacketController {
   
   public static JScrollPane scrollPane = new JScrollPane(ChattingOperator.chatArea);
   
   public ClientPacketController() { }
   
   public void packetController(Packet packet) {
      
      scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      scrollPane.setBounds(10, 15, 638, 195);
      scrollPane.setBorder(null);

      
      switch (packet.getAction()) {
      case Protocol.MESSAGE: // 채팅
         ChattingOperator.chatArea.append(packet.getPlayerVO().getNic() + ": " + packet.getMotion() + "\n");
         scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
         break;

      case Protocol.ENTERLOBBY:
         ArrayList<PlayerVO> lobbyPlayerList = packet.getPlayerList();
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
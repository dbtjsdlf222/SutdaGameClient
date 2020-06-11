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
         packet.getPlayerVO();
         
      } //switch
   } //method
} //class
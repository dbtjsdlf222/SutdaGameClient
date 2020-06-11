package vo;

import java.util.ArrayList;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import server.Room;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Packet {
   private String action;
   private String motion;
   private PlayerVO playerVO;
   @JsonIgnore
   private Map<Integer,Room> roomMap;
   private ArrayList<PlayerVO> playerList;
   
   public Packet() { }
   
   public Packet(String action, PlayerVO playerVO) {
      this.action = action;
      this.playerVO = playerVO;
   }
   
   public Packet(String action, String motion, PlayerVO playerVO) {
      this.action = action;
      this.motion = motion;
      this.playerVO = playerVO;
   }
   public Packet(String action, String motion) {
      this.action = action;
      this.motion = motion;
   }
   
   public Map<Integer, Room> getRoomMap() {
	return roomMap;
}

public void setRoomMap(Map<Integer, Room> room) {
	this.roomMap = room;
}

public ArrayList<PlayerVO> getPlayerList() {
      return playerList;
   }

   public void setPlayerList(ArrayList<PlayerVO> playerList) {
      this.playerList = playerList;
   }

   public PlayerVO getPlayerVO() {
      return playerVO;
   }

   public void setPlayerVO(PlayerVO playerVO) {
      this.playerVO = playerVO;
   }

   public String getAction() {
      return action;
   }

   public void setAction(String action) {
      this.action = action;
   }

   public String getMotion() {
      return motion;
   }

   public void setMotion(String motion) {
      this.motion = motion;
   }
}
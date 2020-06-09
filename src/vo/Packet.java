package vo;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Packet<T> {
	private String action;
	private String motion;
	private PlayerVO playerVO;
	private ArrayList<Room> room;
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

	public ArrayList<Room> getRoom() {
		return room;
	}

	public void setRoom(ArrayList<Room> room) {
		this.room = room;
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

	@Override
	public String toString() {
		return action +": "+ motion;
	}
}
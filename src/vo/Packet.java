package vo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Packet {
	private String action;
	private String motion;
	private Integer roomNo=null;
	private PlayerVO playerVO;
	
	public Packet(String action, String motion, int roomNo, PlayerVO playerVO) {
		this.action = action;
		this.motion = motion;
		this.roomNo = roomNo;
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

	public PlayerVO getPlayerVO() {
		return playerVO;
	}

	public void setPlayerVO(PlayerVO playerVO) {
		this.playerVO = playerVO;
	}

	public Packet() { }
	
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

	public Integer getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}
	@Override
	public String toString() {
		return action +": "+ motion;
	}
}
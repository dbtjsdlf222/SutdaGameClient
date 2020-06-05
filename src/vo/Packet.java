package vo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Packet {
	private String action;
	private String motion;
	private int roomNo;
	private PlayerVOsunil playerVO;
	
	public Packet(String action, String motion, int roomNo, PlayerVOsunil playerVO) {
		this.action = action;
		this.motion = motion;
		this.roomNo = roomNo;
		this.playerVO = playerVO;
	}
	public Packet(String action, String motion, PlayerVOsunil playerVO) {
		this.action = action;
		this.motion = motion;
		this.playerVO = playerVO;
	}

	public PlayerVOsunil getPlayerVO() {
		return playerVO;
	}

	public void setPlayerVO(PlayerVOsunil playerVO) {
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

	public int getRoomNo() {
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
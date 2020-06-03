package Server;

import java.util.ArrayList;

import vo.PlayerVOsunil;
import vo.Room;

public class RoomOperator {
	private ArrayList<Room> roomList = new ArrayList<>();
	
	public void makeRoom(PlayerVOsunil pVO) {
		Room room = new Room();
		room.joinPlayer(pVO);
		roomList.add(room);
	}
	String message;
}
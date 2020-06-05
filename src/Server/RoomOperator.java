package Server;

import java.util.ArrayList;

import vo.PlayerVOsunil;
import vo.Room;

public class RoomOperator {
	private ArrayList<Room> roomList = new ArrayList<>();
	private static RoomOperator ro;
	private RoomOperator() {}

	public static RoomOperator getRoomOperator() {
		if(ro==null) 
			ro = new RoomOperator();
		return ro;
	}

	public Room getRoom(int roomNo) {
		return roomList.get(roomNo);
	}
	
	public void makeRoom(PlayerVOsunil pVO) {
		Room room = new Room();
		room.joinPlayer(pVO);
		roomList.add(room);
	}
	
	public void joinRoom(int roomNo,PlayerVOsunil playerVO) {
		roomList.get(roomNo).joinPlayer(playerVO);
	}
	
}
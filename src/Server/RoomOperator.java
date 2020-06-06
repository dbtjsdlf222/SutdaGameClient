package Server;

import java.util.ArrayList;

import vo.PlayerVO;
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
	
	public ArrayList<Room> getAllRoom() {
		return roomList;
	}
	
	
	public void makeRoom(PlayerVO pVO) {
		Room room = new Room();
		room.joinPlayer(pVO);
		roomList.add(room);
	}
	
	public void joinRoom(int roomNo,PlayerVO playerVO) {
		roomList.get(roomNo).joinPlayer(playerVO);
	}
	
}
package Server;

import java.util.ArrayList;

import vo.PlayerVO;
import vo.Room;

public class RoomOperator {
	private ArrayList<Room> roomList = new ArrayList<>();
			
	public void makeRoom(PlayerVO pVO) {
		Room room = new Room();
		room.joinPlayer(pVO);
		roomList.add(room);
	}
	
}
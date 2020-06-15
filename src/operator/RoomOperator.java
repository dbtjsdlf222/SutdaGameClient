package operator;

import java.util.HashMap;
import java.util.Map;

import server.Room;
import vo.PlayerVO;

public class RoomOperator {
	public static RoomOperator ro = new RoomOperator();
	private Map<Integer, Room> roomMap = new HashMap<Integer, Room>();
	
	static {}
	
	private RoomOperator() {}

	public void removeRoom(int roomNo) {
		roomMap.remove(roomNo);
	}
	public Room getRoom(int roomNo) {
		return roomMap.get(roomNo);
	} // for

	public Map<Integer, Room> getAllRoom() {
		return roomMap;
	}

	public int makeRoom(PlayerVO pVO) {
		Room room = new Room();
		room.joinPlayer(pVO);
		roomMap.put(room.getRoomNo(), room);
		return room.getRoomNo();
	}

	public int joinRoom(int roomNo, PlayerVO playerVO) {
		
		return roomMap.get(roomNo).joinPlayer(playerVO);
		
	}

}
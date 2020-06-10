package operator;

import java.util.HashMap;
import java.util.Map;

import vo.PlayerVO;
import vo.Room;

public class RoomOperator {
	private Map<Integer, Room> roomMap = new HashMap<Integer, Room>();
	private static RoomOperator ro;

	private RoomOperator() {}

	public static RoomOperator getRoomOperator() {
		if (ro == null)
			ro = new RoomOperator();
		return ro;
	}

	public Room getRoom(int roomNo) {
		return roomMap.get(roomNo);
	} // for

	public Map<Integer, Room> getAllRoom() {
		return roomMap;
	}

	public void makeRoom(PlayerVO pVO) {
		Room room = new Room();
		room.joinPlayer(pVO);
		roomMap.put(room.getRoomNo(), room);
	}

	public void joinRoom(int roomNo, PlayerVO playerVO) {
		roomMap.get(roomNo).joinPlayer(playerVO);
	}

}
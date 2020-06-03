package vo;

import java.util.ArrayList;

public class RoomVO {
	private static int increaseRoomNo = 0; 
	private int roomNo;
	private ArrayList<PlayerVO> list = new ArrayList<>();
	
	public RoomVO() {
		roomNo = increaseRoomNo++;
	}
	
	public void joinPlayer(PlayerVO vo) {
		list.add(vo);
	}
	public void exitPlayer(PlayerVO vo) {
		for (int i = 0; i < list.size(); i++) {
			vo.
			list.remove();
		}
	}
	
}

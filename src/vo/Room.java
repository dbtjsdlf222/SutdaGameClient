package vo;

import java.util.ArrayList;

public class Room {
	private static int increaseRoomNo = 0; 
	private int roomNo; //방 번호
	private int startMoney;	//시작 금액
	private ArrayList<PlayerVO> list = new ArrayList<>(); //방안에 있는 사람 리스트
	
	public Room() {
		roomNo = increaseRoomNo++;
	}
	
	public void joinPlayer(PlayerVO vo) {
		list.add(vo);
	}
	public void exitPlayer(PlayerVO vo) {
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getNic().equals(vo.getNic())) {
				
			}
		}
	} //exitPlayer
}
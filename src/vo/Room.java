package vo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Room {
	private static int increaseRoomNo = 0; 
	private int roomNo; //방 번호
	private int startMoney;	//시작 금액
	private ArrayList<PlayerVO> list = new ArrayList<>(); //방안에 있는 사람 리스트
	private float[] card = new float[20];
	private Queue shuffle = new LinkedList();
	private String master;
	private int cardLevel;
	
	public static void main(String[] args) {
		new Room();
	}
	
	//생성자
	@SuppressWarnings("unchecked")
	public Room() {
		roomNo = increaseRoomNo++;
		float cardSetNo=1;
		
		for (; cardSetNo <= 10.5; cardSetNo+=0.5) {
			shuffle.offer(cardSetNo+"");
		}
		
		for (int i = 0; i < card.length; i++) {
			
		}
	} //Room()
	
	public void swap(int[] a, int i, int j) {
		int temp = cardList[i];
		cardList[i] = arr[i];
		cardList[j]=temp;
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
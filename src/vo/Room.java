package vo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Room {
	private static int increaseRoomNo = 0;
	private int roomNo; // 방 번호
	private int startMoney; // 시작 금액
	private ArrayList<PlayerVO> list = new ArrayList<>(); // 방안에 있는 사람 리스트
	private float[] cardArr = new float[20];
	private Queue<Float> shuffledCard = new LinkedList();
	private String master;
	private int cardLevel;

	public static void main(String[] args) {
		new Room();
	}

	// 생성자
	public Room() {
		roomNo = increaseRoomNo++;
		cardShuffle();
		for (int i = 0; i < cardArr.length-1; i++) {
			System.out.println(rollCard());
		}
	} // Room()
	
	public void cardShuffle() {
		float cardSetNo = 1;

		Random ran = new Random();

		for (int i = 0; i < 20; i++) {
			cardArr[i] = cardSetNo;
			System.out.println(cardSetNo);
			cardSetNo += 0.5;
		}

		for (int i = 0; i < cardArr.length - 1; i++) {
			swap(cardArr, i, ran.nextInt(cardArr.length - i) + i);
			shuffledCard.offer(cardArr[i]);
		}
	}
	
	public float rollCard() {
		return shuffledCard.poll();		
	}
	
	public void swap(float[] arr, int i, int j) {
		float temp = arr[i];
		arr[i] = arr[i];
		arr[j] = temp;
	}

	public void joinPlayer(PlayerVO vo) {
		list.add(vo);
	}

	public void exitPlayer(PlayerVO vo) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getNic().equals(vo.getNic())) {

			}
		}
	} // exitPlayer
}
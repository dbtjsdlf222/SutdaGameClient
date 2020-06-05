package vo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Room {
	private static int increaseRoomNo = 0;
	private int roomNo; // 방 번호
	private int startMoney; // 시작 금액
	protected ArrayList<PlayerVOsunil> list = new ArrayList<>(); // 방안에 있는 사람 리스트
	private float[] cardArr = new float[20]; // 카드각
	private Queue<Float> shuffledCard = new LinkedList(); // 위에서 부터 카드 한장씩 배분하기위한 queue
	private String master; // 방장 or 선판 이긴거
	private boolean gameStarted = false;

	// 생성자
	public Room() {
		roomNo = increaseRoomNo++;
		cardShuffle();
		for (int i = 0; i < cardArr.length - 1; i++) {
			System.out.println(rollCard());
		}
	} // Room()

	public void cardShuffle() {
		float cardSetNo = 1;

		Random ran = new Random();

		for (int i = 0; i < 20; i++) {
			cardArr[i] = cardSetNo;
			cardSetNo += 0.5;
		}

		for (int i = 0; i < cardArr.length; i++) {
			swap(cardArr, i, ran.nextInt(cardArr.length - i) + i);
			shuffledCard.offer(cardArr[i]);
		}
	}

	public float rollCard() {
		return shuffledCard.poll();
	}

	public void roomSpeaker(Packet pac) {
		ObjectMapper objectMapper = new ObjectMapper();
		for (int i = 0; i < cardArr.length; i++) {
			try {
				list.get(i).getPwSocket().println(objectMapper.writeValueAsString(pac));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		} // for

	} // roomSpeaker

	public void swap(float[] arr, int i, int j) {
		float temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	public void joinPlayer(PlayerVOsunil vo) {
		list.add(vo);
	}

	public void exitPlayer(PlayerVOsunil vo) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getNic().equals(vo.getNic())) {
				list.remove(i);
			}
		}
	} // exitPlayer

	public static int getIncreaseRoomNo() {
		return increaseRoomNo;
	}

	public static void setIncreaseRoomNo(int increaseRoomNo) {
		Room.increaseRoomNo = increaseRoomNo;
	}

	public int getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}

	public int getStartMoney() {
		return startMoney;
	}

	public void setStartMoney(int startMoney) {
		this.startMoney = startMoney;
	}

	public ArrayList<PlayerVOsunil> getList() {
		return list;
	}

	public void setList(ArrayList<PlayerVOsunil> list) {
		this.list = list;
	}

	public float[] getCardArr() {
		return cardArr;
	}

	public void setCardArr(float[] cardArr) {
		this.cardArr = cardArr;
	}

	public Queue<Float> getShuffledCard() {
		return shuffledCard;
	}

	public void setShuffledCard(Queue<Float> shuffledCard) {
		this.shuffledCard = shuffledCard;
	}

	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	public boolean isGameStarted() {
		return gameStarted;
	}

	public void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}

}
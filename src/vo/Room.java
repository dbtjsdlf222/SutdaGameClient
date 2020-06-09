package vo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import client.MainScreen;

public class Room {
	private static int increaseRoomNo = 0;
	private int roomNo; // 방 번호
	private int startMoney; // 시작 금액
	protected ArrayList<PlayerVO> list = new ArrayList<>(); // 방안에 있는 사람 리스트
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
		for (int i = 0; i < list.size(); i++) {
			try {
				list.get(i).getPwSocket().println(objectMapper.writeValueAsString(pac));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		} // for
	} // roomSpeaker

	public void roomChat(Packet packet) {
		for (int i = 0; i < list.size(); i++) {
			list.get(i).getPwSocket().println(packet);
		} // for
	} // roomChat

	public void swap(float[] arr, int i, int j) {
		float temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	public void joinPlayer(PlayerVO vo) {
		list.add(vo);
	}

	public void exitPlayer(PlayerVO vo) {
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

//	public void moneyCheck() {
//		if (money >= startMoney) {
//			System.out.println("입장");
//		} else if (list.money < startMoney) {
//			System.out.println("입장 불가");
//		}
//	} // 판돈 체크 후 입장 여부 확인

	public void gameStart() {

	} // 방장이 게임 시작

//	public void bet() {
//		String bet;
//		int total = 0;
//		int i;
//		
//		for (i = 0; i < 100; i++) {
//			int beforeBet (i =- 1);
//			
//			
//			switch (bet) {
//			case "하프":
//				total = ++total / 2;
//				break;
//			case "쿼터":
//				total = ++total / 4;
//				break;
//			case "체크":
//				total = total;
//				break;
//			case "올인":
//				total = ++PlayerVO.money;
//				break;
//			case "콜":
//				total = ++beforeBet;
//				break;
//			case "다이":
//				System.out.println("관전");
//				break;
//			}
//		}
//	}

	public ArrayList<PlayerVO> getList() {
		return list;
	}

	public void setList(ArrayList<PlayerVO> list) {
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
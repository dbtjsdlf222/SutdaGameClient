package server;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import util.Packing;
import vo.Packet;
import vo.PlayerVO;
import vo.Protocol;

public class Room {
	private static int increaseRoomNo = 1;
	private int roomNo; // 방 번호
	private int startMoney; // 시작 금액
	private Map<Integer, PlayerVO> playerMap = new ConcurrentHashMap<Integer, PlayerVO>(); // 방안에 있는 사람 리스트
	private float[] cardArr = new float[20]; // 카드각
	private Queue<Float> shuffledCard = new LinkedList<>(); // 위에서 부터 카드 한장씩 배분하기위한 queue
	private Integer masterIndex; // 방장 or 선판 이긴거
	private String master;

	private boolean gameStarted = false;

	// 생성자
	public Room() {
		roomNo = increaseRoomNo++;
		cardShuffle();
	} // Room()

	@JsonIgnore
	public int getPlayerSize() {
		return playerMap.size();
	}

	public void rollPlayerCard() {
		Packet packet = new Packet();
		for (Entry<Integer, PlayerVO> s : playerMap.entrySet()) {
			if (s.getValue().isLive()) {
				packet.setAction(Protocol.CARD1);
				packet.setCard1(pollOneCard());
				Packing.sender(s.getValue().getPwSocket(), packet);
			}
		}
	} // setPlayerCard();

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
	} // cardShuffle();

	public float pollOneCard() {
		return shuffledCard.poll();
	}

	public void gameStart() {
		cardShuffle(); // 카드큐를 섞는다
		Packet packet = new Packet();
		for (Entry<Integer, PlayerVO> s : playerMap.entrySet()) {
			packet.setAction(Protocol.CARD1);
			packet.setCard1(pollOneCard()); // 첫 번째 카드를 배분한다

			s.getValue().setLive(true);
			Packing.sender(s.getValue().getPwSocket(), packet);
		} // for
	} // 방장이 게임 시작

	public void roomSpeaker(Packet pac) {

		System.out.print("[Send(roomSpeaker(" + roomNo);
		Iterator<Entry<Integer, PlayerVO>> iterator = playerMap.entrySet().iterator();
		if (iterator.hasNext()) {
			System.out.print("(" + iterator.next().getValue().getNic());
			while (iterator.hasNext())
				System.out.print(", " + iterator.next().getValue().getNic());
			System.out.print(")");
		}
		System.out.println(", " + Protocol.getName(pac.getAction()) + "))] " + pac);

		ObjectMapper objectMapper = new ObjectMapper();
		for (Entry<Integer, PlayerVO> s : playerMap.entrySet()) {
			Packing.sender(playerMap.get(s.getKey()).getPwSocket(), pac);
		}
	} // roomSpeaker();

	public void roomChat(Packet packet) {
		for (int i = 0; i < playerMap.size(); i++) {
			playerMap.get(i).getPwSocket().println(packet);
		} // for
	} // roomChat();

	public void swap(float[] arr, int i, int j) {
		float temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	public int joinPlayer(PlayerVO vo) {

		for (int i = 0; i < 5; i++) {
			if (playerMap.get(i) == null) {
				playerMap.put(i, vo);

				return i;
			} // if
		} // for
		return 0;
	} // join

	public void exitPlayer(PlayerVO vo) {

		int playerIndex = getPlayerIndex(vo.getNo());
		playerMap.remove(playerIndex);

		if (masterIndex == playerIndex) {

			int size = playerMap.size();

			if (size < 1)
				return;

			int random = (int) (Math.random() * size);

			Iterator<Entry<Integer, PlayerVO>> iterator = playerMap.entrySet().iterator();
			Entry<Integer, PlayerVO> entry;
			entry = iterator.next();
			for (int i = 0; i < random; i++)
				entry = iterator.next();

			this.roomSpeaker(new Packet(Protocol.CHANGEMASTER, entry.getKey().toString()));
			setMasterNo(entry.getValue().getNo());

		}

//		for (Entry<Integer, PlayerVO> set : playerMap.entrySet()) {
//			int i = set.getKey();
//
//			if (set.getValue().getNo() == vo.getNo()) {
//				playerMap.remove(i);
//				try {
//					if (masterIndex == i && playerMap.size() <= 0) { // 퇴장 플레이어가 방장이 아니고 다른 플레이어가 없으면 종료
//						return;
//					}
//				} catch (NullPointerException e) {
//					e.printStackTrace();
//				}
//				continue;
//			}
//
//			masterIndex = set.getKey();
//			this.roomSpeaker(new Packet(Protocol.CHANGEMASTER, masterIndex + ""));
//			return;
//		} // for
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

	// public void moneyCheck() {
	// if (money >= startMoney) {
	// System.out.println("입장");
	// } else if (list.money < startMoney) {
	// System.out.println("입장 불가");
	// }
	// } // 판돈 체크 후 입장 여부 확인

	public void playerCardSet(int i) {

	}

	// public void bet() {
	// String bet;
	// int total = 0;
	// int i;
	//
	// for (i = 0; i < 100; i++) {
	// int beforeBet (i =- 1);
	//
	//
	// switch (bet) {
	// case "하프":
	// total = ++total / 2;
	// break;
	// case "쿼터":
	// total = ++total / 4;
	// break;
	// case "체크":
	// total = total;
	// break;
	// case "올인":
	// total = ++PlayerVO.money;
	// break;
	// case "콜":
	// total = ++beforeBet;
	// break;
	// case "다이":
	// System.out.println("관전");
	// break;
	// }
	// }
	// }

	public Map<Integer, PlayerVO> getList() {
		return playerMap;
	}

	public void setList(Map<Integer, PlayerVO> map) {
		this.playerMap = map;
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

	public int getPlayerIndex(int playerNo) {

		for (Entry<Integer, PlayerVO> entry : playerMap.entrySet()) {
			if (entry.getValue().getNo() == playerNo) {
				return entry.getKey();
			}
		}
		return -1;

	} // getPlayerIndex();

	public void setMaster(String str) {
		master = str;
	}

	public void setMasterNo(int no) {

		int index = getPlayerIndex(no);
		if (index != -1) {
			masterIndex = index;
			setMaster(playerMap.get(index).getNic());
		}

	}

	public boolean isGameStarted() {
		return gameStarted;
	}

	public void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}

}
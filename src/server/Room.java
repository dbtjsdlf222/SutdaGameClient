package server;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

import util.Packing;
import vo.Packet;
import vo.PlayerVO;
import vo.Protocol;

public class Room {
	
	private static final Logger logger = LogManager.getLogger();
	
	private static int increaseRoomNo = 1;
	private int roomNo; // 방 번호
	private int startMoney; // 시작 금액
	private Map<Integer, PlayerVO> playerMap = new ConcurrentHashMap<Integer, PlayerVO>(); // 방안에 있는 사람 리스트
	private float[] cardArr = new float[20]; // 카드각
	private Queue<Float> shuffledCard = new LinkedList<>(); // 위에서 부터 카드 한장씩 배분하기위한 queue
	private Integer masterIndex; // 방장 or 선판 이긴거
	private String master; // 방장(선)이 누구인지
	private int totalMoney; // 총 배팅액의 합
	private int beforeBet; // 전 플레이어의 배팅액
	private int round; // 몇번째 카드 배팅인지
	private int lastBetIdx; // 마지막으로 배팅한 플레이어가 몇번째 사람인지
	private int turn; // 누구의 차례인지

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

	public void handOutCard() {
		for (Entry<Integer, PlayerVO> s : playerMap.entrySet()) {
			Packet packet = new Packet();
			if (s.getValue().isLive()) {
				packet.setAction(Protocol.CARD);
				if (round == 1) { 		// 1번 카드 배분
					packet.setCard_(pollOneCard());
					round = 2;
				} else if (round == 2) { 	// 2번 카드 배분
					beforeBet = 0;		// 전 타임 배팅 머니 0으로 초기화
					packet.setCard_(0, pollOneCard());
				} else 					// 재경기시 1,2번 카드 배분
					packet.setCard_(pollOneCard(), pollOneCard());
				Packing.sender(s.getValue().getPwSocket(), packet);
			} // if
		} // for
	} // setPlayerCard();

	public void setLiveTrue() {
		for (Entry<Integer, PlayerVO> s : playerMap.entrySet()) {
			s.getValue().setLive(true);
		}
	}
	
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
	} // pollOneCard();

	public String[] setButton() {
		
		return null;
	}
	
	public void gameStart() {
		round = 1; // 1라운드
		turn = masterIndex; // 첫 차례는 방장부터
		cardShuffle(); // 카드큐를 섞는다
		setLiveTrue(); 
		handOutCard(); // 카드배분
		String [] buttonArr = {Protocol.Die,Protocol.Ddadang+"_",Protocol.Call+"_",Protocol.Quater+"_",Protocol.Half,Protocol.Allin+"_"};
		Packing.sender(playerMap.get(masterIndex).getPwSocket(),new Packet(Protocol.TURN, buttonArr,turn+""));
		roomSpeaker(new Packet(Protocol.TURN, masterIndex+""));
		roomSpeaker(new Packet(Protocol.PAY, startMoney+""));
	} // gameStart();

	public void roomSpeaker(Packet pac) {
		
		String message = "";
		
		message += "[Send(roomSpeaker(" + roomNo;
		Iterator<Entry<Integer, PlayerVO>> iterator = playerMap.entrySet().iterator();
		if (iterator.hasNext()) {

			message +="(" + iterator.next().getValue().getNic();

			while (iterator.hasNext())
				message += ", " + iterator.next().getValue().getNic();

			message += ")";

		}
		message += ", " + Protocol.getName(pac.getAction()) + "))] " + pac;
		
		logger.info(message);
		
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

	public void turn(int idx, String proBet) {
		bet(proBet);
	}

	public void bet(String proBet) {
		int betMoney = 0;
		switch (proBet) {
		case Protocol.Half:
			betMoney = beforeBet+(totalMoney/2);
			totalMoney += betMoney;
			break;
			
		case Protocol.Quater:
			betMoney = beforeBet+(totalMoney/4);
			totalMoney += betMoney;
			break;
			
		case Protocol.Call:
			betMoney = beforeBet;
			totalMoney += betMoney;
			break;
			
		case Protocol.Allin:
			betMoney = beforeBet+ playerMap.get(turn).getMoney();
			totalMoney = betMoney;  
			break;
			
		case Protocol.Check:
			betMoney = 0;
			break;
			
		case Protocol.Pping:
			betMoney = startMoney;
			totalMoney += betMoney; 
			break;
			
		case Protocol.Ddadang:
			betMoney = beforeBet * 2;
			totalMoney += betMoney; 
			break;
			
		case Protocol.Die:
			playerMap.get(turn).setLive(false);
			int i = 0;
			int winerIdx = 0;
			
			for (Entry<Integer, PlayerVO> s : playerMap.entrySet()) {
				if(s.getValue().isLive()) {
					i++;
					winerIdx = s.getKey();
				} //if
			} //for
			
			//생존 플레이어가 한명일 경우 winer 인덱스에 있는 사람이 승리 
			if(i <= 1)
				gameOver(winerIdx);
			
			//방장이 죽으면 다음 턴 사람한테 넘어간다
			if(turn == masterIndex) {
				for (int j = turn; j < 5; j++) {
					if(playerMap.get(j) == null||!(playerMap.get(j).isLive()))
						continue;
					
					Packet packet = new Packet(Protocol.CHANGEMASTER, j+"");
					roomSpeaker(packet);
				} //for
			} //if
			break;	//case die;
		} // switch
		
		beforeBet =  betMoney;
		playerMap.get(turn).pay(betMoney);	//배팅 한 만큼 Vo에서 뺌
		
	} // bet();

	// 승자에게 돈 이동
	public void gameOver(int winer) {
		playerMap.get(winer).setMoney(playerMap.get(winer).getMoney() + totalMoney);
	}

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

	} //setMasterNo();

	public boolean isGameStarted() {
		return gameStarted;
	}

	public void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}

}
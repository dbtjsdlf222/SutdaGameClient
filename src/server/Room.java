package server;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;

import util.CalcCardLevel;
import util.Packing;
import vo.Packet;
import vo.PlayerVO;
import vo.Protocol;

public class Room extends ServerMethod {

	private static int increaseRoomNo = 10000;
	private int roomNo;		// 방 번호
	private long startMoney; // 시작 금액
	private String roomTitle = "";
	private String roomPw = "";
	private String personnel = "5"; 
	private Map<Integer, PlayerVO> playerMap = new ConcurrentHashMap<Integer, PlayerVO>(); // 방안에 있는 사람 리스트
	private float[] cardArr = new float[20]; // 카드각
	private Queue<Float> shuffledCard = new LinkedList<>(); // 위에서 부터 카드 한장씩 배분하기위한 queue
	private Integer masterIndex; // 방장 or 선판 이긴거
	private String master; 		 // 방장(선)이 누구인지
	private long totalMoney; 	 // 총 배팅액의 합
	private long beforeBetMoney;	 	 // 전 플레이어의 배팅액
	private int round; 			 // 몇번째 카드 배팅인지
	private int lastBetIdx;	 	 // 마지막으로 배팅한 플레이어가 몇번째 사람인지
	private int turn; 			 // 누구의 차례인지
	private boolean round1First = false;	// 첫 차례 버튼세팅 조건 변수
	private boolean round2First = false;	// 첫 차례 버튼세팅 조건 변수
	private boolean gameStarted = false;
	private boolean allIn = false;
	
	
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
			packet.setAction(Protocol.CARD);
			if (s.getValue().isLive()) {

				if (round == 1) { // 1라운드 일경우 첫번째 카드 배분
					float card = pollOneCard();
					packet.setCard_(card);
					s.getValue().setCard1(card);

				} else if (round == 2) { // 2라운드 일경우 두번째 카드 배분
					float card = pollOneCard();
					s.getValue().setCard2(card);
					packet.setCard_(0, card);
//					turn = masterIndex;
					round2First = true;
				}  else if (round == 3) { // 재경기시 1,2번 카드 배분
					float c1 = pollOneCard();
					float c2 = pollOneCard();
					packet.setCard_(c1, c2);
					s.getValue().setCard1(c1);
					s.getValue().setCard2(c2);
				}
				Packing.sender(s.getValue().getPwSocket(), packet);
			} else {
				Packing.sender(s.getValue().getPwSocket(), packet);
			}
		} // for
	} // setPlayerCard();

	/**
	 * 카드 큐 섞기
	 */
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

	/**
	 * @return 플레이어가 배팅 가능한 버튼 배열 return
	 */
	public String[] setButton() {
		String[] arr = new String[10];
		long turnHaveMoney = playerMap.get(turn).getMoney();
		arr[0] = Protocol.Die;
		
		// 두번째 라운드때 첫번째 사람은 따당 콜 대신 체크를 넣는다
		if (round2First) {
			arr[1] = Protocol.Check;
			arr[6] = "-";
			
			arr[2] = Protocol.Pping;
			arr[7] = startMoney + "";
			if(startMoney > turnHaveMoney)
				arr[2] += "_";
			
		} else {
			if(beforeBetMoney > turnHaveMoney)
				allIn = true;
			
			arr[1] = Protocol.Call;
			arr[6] = beforeBetMoney + "";
					
			arr[2] = Protocol.Ddadang;
			arr[7] = beforeBetMoney*2 + "";
			if(beforeBetMoney * 2 > turnHaveMoney)
				arr[2] += "_";
		}
		arr[3] = Protocol.Quater;
		arr[8] = (totalMoney/4) + beforeBetMoney + "";
			if((totalMoney/4) + beforeBetMoney > turnHaveMoney)
				arr[3]+= "_";
				
		arr[4] = Protocol.Half;
		arr[9] = (totalMoney/2) + beforeBetMoney + "";
			if((totalMoney/2) + beforeBetMoney > turnHaveMoney)
				arr[4]+= "_";
		
		arr[5] = Protocol.Allin;

		if (round == 1) {
			if(round1First) {	//1라운드 첫 턴은 다이 하프만 가능
				arr[1] += "_";
				arr[2] += "_";
				arr[3] += "_";
			}
			arr[5] += "_"; // 1라운드 올인 버튼 비활성화
		} else {
			if(beforeBetMoney > playerMap.get(turn).getMoney()) {
				arr[5] += "_";
			}
		} //if (round == 1) else
		return arr;
	} //setButton();

	/**
	 * 게임시작 
	 */
	public void gameStart() {
		gameReset();
		handOutCard();		// 1라운드 카드배분
		turnProgress();
		gameStarted = true;
		roomSpeaker(new Packet(Protocol.TURN, masterIndex + ""));
		roomSpeaker(new Packet(Protocol.STARTPAY, startMoney + ""));
		lobbyReloadBroadcast(); 
	} // gameStart();

	
	public void roomSpeaker(Packet pac) {

		String message = "";

		message += "[Send(roomSpeaker(" + roomNo;
		Iterator<Entry<Integer, PlayerVO>> iterator = playerMap.entrySet().iterator();
		if (iterator.hasNext()) {

			message += "(" + iterator.next().getValue().getNic();

			while (iterator.hasNext())
				message += ", " + iterator.next().getValue().getNic();

			message += ")";

		}
		message += ", " + Protocol.getName(pac.getAction()) + "))] " + pac;

		logger.info(message);

		for (Entry<Integer, PlayerVO> s : playerMap.entrySet()) {
			Packing.sender(playerMap.get(s.getKey()).getPwSocket(), pac);
		}
	} // roomSpeaker();
	
	/**
	 * 특정 플레이어를 제외하고 브로드케스팅
	 * @param pac	packet
	 * @param no	제외할 playerNo
	 */
	public void roomSpeakerNotThisPlayer(Packet pac, int no) {
		
		String message = "";
		
		message += "[Send(roomSpeaker(" + roomNo;
		Iterator<Entry<Integer, PlayerVO>> iterator = playerMap.entrySet().iterator();
		if (iterator.hasNext()) {
			
			message += "(" + iterator.next().getValue().getNic();
			
			while (iterator.hasNext())
				message += ", " + iterator.next().getValue().getNic();
			
			message += ")";
			
		}
		message += ", " + Protocol.getName(pac.getAction()) + "))] " + pac;
		
		logger.info(message);
		
		for (Entry<Integer, PlayerVO> s : playerMap.entrySet()) {
			if(s.getValue().getNo()==no) continue;
			Packing.sender(playerMap.get(s.getKey()).getPwSocket(), pac);
		}
	} // roomSpeaker();

	public void roomChat(Packet packet) {
		for (Entry<Integer, PlayerVO> set : playerMap.entrySet()) {
			Packing.sender(set.getValue().getPwSocket(), packet);
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
				vo.setIndex(i);
				return i;
			} // if
		} // for
		logger.error("방번호:"+roomNo+" 인원초과");
		return 0;
	} // join

	public void exitPlayer(PlayerVO vo) {

		//랜덤으로 방장 지정
		// int playerIndex = getPlayerIndex(vo.getNo());
		// playerMap.remove(playerIndex);
		// static 스레드 활용하여 턴제한시간 7초 플레이어가 자신의 턴일때 나갔을 경우 timeSet(0);

		// if (masterIndex == playerIndex) {
		//
		// int size = playerMap.size();
		//
		// if (size < 1)
		// return;
		//
		// int random = (int) (Math.random() * size);
		//
		// Iterator<Entry<Integer, PlayerVO>> iterator =
		// playerMap.entrySet().iterator();
		// Entry<Integer, PlayerVO> entry;
		// entry = iterator.next();
		// for (int i = 0; i < random; i++)
		// entry = iterator.next();
		//
		// masterIndex = entry.getKey();
		// master = entry.getValue().getNic();
		// this.roomSpeaker(new Packet(Protocol.CHANGEMASTER,
		// entry.getKey().toString()));
		// setMasterNo(entry.getValue().getNo());
		//
		// }

		//다음 사람에게 방장 지정
		int playerIndex = getPlayerIndex(vo.getNo());
		playerMap.remove(playerIndex);
		if(isGameStarted()) {
			vo.gameLose();
			serverDAO.playerSave(vo);
		}
		
		if(playerMap.size() <= 0) {
			ro.removeRoom(roomNo);
			
		} else {
			Packet packet = new Packet();
			packet.setAction(Protocol.EXITOTHERROOM);
			packet.setMotion(Integer.toString(playerIndex));
			roomSpeaker(packet);
			roomSpeaker(new Packet(Protocol.MESSAGE, "알림 ["+ vo.getNic() + "]님이 퇴실하셨습니다."));
		
			if (masterIndex == playerIndex) {
				//방장 다음차례의 사람을 방장으로 지정 
				for (int i = 1; i < 5; i++) {
					int idx = (playerIndex + i) % 5;
					
					if(playerMap.get(idx)==null) continue;
					
					if(isGameStarted()) {
						if(playerMap.get(idx).isLive()) { 
							masterIndex = idx;
							this.roomSpeaker(new Packet(Protocol.CHANGEMASTER, masterIndex + ""));
							break;
						}	//if						
					} else {
						masterIndex = idx;
						this.roomSpeaker(new Packet(Protocol.CHANGEMASTER, masterIndex + ""));
						break;
					} //if~else
				} //for
			}
		} //if(playerMap.size() <= 0)
		lobbyReloadBroadcast();
	} // exitPlayer();

		// public void moneyCheck() {
	// if (money >= startMoney) {
	// System.out.println("입장");
	// } else if (list.money < startMoney) {
	// System.out.println("입장 불가");
	// }
	// } // 판돈 체크 후 입장 여부 확인

	/**
	 * 턴을 증가시키고 사용자 사용가능 버튼을 전송하고 누구 턴인지 브로드캐스트
	 */
	public void turnProgress() {
		
		//다음 턴 사람의 index를 turn에 넣음
		for (int i = 0; i < 4; i++) {
			turn++;
			turn %= 5;
			if(playerMap.get(turn)==null)
				continue;
			if(playerMap.get(turn).isLive())
				break;
			
		} //for
		String[] arr = setButton();
		
		//차례 클라이언트에게 button배열 전송
		Packing.sender(playerMap.get(turn).getPwSocket(), new Packet(Protocol.SETBUTTON, arr));
		Packet packet = new Packet(Protocol.TURN, turn + "");
		roomSpeaker(packet);
	} //turnProgress();

	public void bet(String proBet) {
		long betMoney = 0;

		// 1라운드 첫 배팅한 사람은 다이 하프만 가능
		if(round1First) {
			if(!proBet.equals(Protocol.Die)) {
				round1First = false;
			}
		}
		
		// 2라운드 첫 배팅한 사람이 죽을 경우 다음 사람도 체크 삥 버튼으로 보냄
		if(round2First) {
			if(!Protocol.Die.equals(proBet)) {
				round2First = false;
			}
		} //if(round2First);
		
		switch (proBet) {
		case Protocol.Half:
			betMoney = beforeBetMoney + (totalMoney / 2);
			totalMoney += betMoney;
			lastBetIdx = turn;
			logger.debug("Bet:[" + proBet+"] totalMoney:["+ totalMoney+"] betMoney:["+betMoney+"] beforeBet:["+beforeBetMoney+"]");
			break;

		case Protocol.Quater:
			betMoney = beforeBetMoney + (totalMoney / 4);
			totalMoney += betMoney;
			lastBetIdx = turn;
			logger.debug("Bet:[" + proBet+"] totalMoney:["+ totalMoney+"] betMoney:["+betMoney+"] beforeBet:["+beforeBetMoney+"]");
			break;

		case Protocol.Call:
			if(playerMap.get(turn).getMoney() < beforeBetMoney) {
				playerMap.get(turn).setAllIn(true);
				totalMoney += playerMap.get(turn).getMoney();
			} else {
				betMoney = beforeBetMoney;
				totalMoney += betMoney;
			}
			
			logger.debug("Bet:[" + proBet+"] totalMoney:["+ totalMoney+"] betMoney:["+betMoney+"] beforeBet:["+beforeBetMoney+"]");
			int nextTurn = turn ;
			
			for (int j = 1; j < 5; j++) {
				nextTurn = (nextTurn + j) % 5;
				
				if (playerMap.get(nextTurn) == null || !playerMap.get(nextTurn).isLive()) {
					continue;
				} else {
					if(nextTurn == lastBetIdx) {	//다음 사람이 마지막 판돈 올린 사람이면
						if (round == 1) { 	// 1번째 카드 승부
							round = 2;
							handOutCard(); 	// 2번째 카드 카드 배분
							beforeBetMoney = 0;
						} else { // round 2~3
							playerMap.get(turn).pay(betMoney); // 배팅 한 만큼 VO에서 뺌
							roomSpeaker(new Packet(Protocol.OTHERBET, turn + "/" + proBet + "/" + playerMap.get(turn).getMoney()+"/"+totalMoney));
							gameResult();
							return;
						}
						break;
					} //if
				} //if
			} // for
			break; // Call

		case Protocol.Allin:
			betMoney = beforeBetMoney + playerMap.get(turn).getMoney();
			playerMap.get(turn).setAllIn(true);
			totalMoney += betMoney;    
			lastBetIdx = turn;
			logger.debug("Bet:[" + proBet+"] totalMoney:["+ totalMoney+"] betMoney:["+betMoney+"] beforeBet:["+beforeBetMoney+"]");
			break;

		case Protocol.Check:
			betMoney = 0;
			lastBetIdx = turn;
			logger.debug("Bet:[" + proBet+"] totalMoney:["+ totalMoney+"] betMoney:["+betMoney+"] beforeBet:["+beforeBetMoney+"]");
			break;

		case Protocol.Pping:
			betMoney = startMoney;
			totalMoney += betMoney;
			lastBetIdx = turn;
			logger.debug("Bet:[" + proBet+"] totalMoney:["+ totalMoney+"] betMoney:["+betMoney+"] beforeBet:["+beforeBetMoney+"]");
			break;

		case Protocol.Ddadang:
			betMoney = beforeBetMoney * 2;
			totalMoney += betMoney;
			lastBetIdx = turn;
			logger.debug("Bet:[" + proBet+"] totalMoney:["+ totalMoney+"] betMoney:["+betMoney+"] beforeBet:["+beforeBetMoney+"]");
			break;

		case Protocol.Die:
			playerMap.get(turn).setLive(false);
			int i = 0;
			int winerIdx = 0;

			for (Entry<Integer, PlayerVO> s : playerMap.entrySet()) {
				if (s.getValue().isLive()) {
					i++;
					winerIdx = s.getKey();
				} // if
			} // for

			// 생존 플레이어가 한명일 경우 winer 인덱스에 있는 사람이 승리
			if (i <= 1) {
				gameOver(winerIdx,null);
				roomSpeaker(new Packet(Protocol.OTHERBET, turn + "/" + proBet + "/" + playerMap.get(turn).getMoney()+"/"+totalMoney));
				return;
			}
			// 방장이 죽으면 다음 턴 사람한테 넘어간다
			if (turn == masterIndex) {
				int temp = 0;
				for (int j = 1; j < 5; j++) {
					temp = turn + j;
					if (playerMap.get(temp) == null || !(playerMap.get(temp).isLive()))
						continue;
					
					Packet packet = new Packet(Protocol.CHANGEMASTER, temp + "");
					roomSpeaker(packet);
					break;
				} // for
			} // if (turn == masterIndex)
			break; // case die;
		} // switch
		beforeBetMoney = betMoney;
		
		playerMap.get(turn).pay(betMoney); // 배팅 한 만큼 VO에서 뺌
		
		roomSpeaker(new Packet(Protocol.OTHERBET, turn + "/" + proBet + "/" + playerMap.get(turn).getMoney()+"/"+totalMoney));
		
		turnProgress();
	} // bet();
	
	/**
	 * 플레이어 패 비교
	 * OPENCARD 브로드캐스팅
	 */
	public void gameResult() {
		round = 3;	//재경기시 카드 2개를 주기위함
		roomSpeaker(new Packet(Protocol.OPENCARD,playerMap));
		new CalcCardLevel().getWinner(roomNo, playerMap);
	} // gameResult();
	
	public void draw() {
		roomSpeaker(new Packet(Protocol.DRAW));
		handOutCard();
		turnProgress();
	}
	
	/**
	 * @param winerIdx 승자 idx
	 */
	// 승자에게 돈 이동
	public void gameOver(int winerIdx, String cardName) {
		try {
			//올인시 자신이 건돈 만큼만 사람들한테서 받음
			if(playerMap.get(winerIdx).isAllIn()) {
				int winerNo = playerMap.get(winerIdx).getNo();
				long winerBetMoney = playerMap.get(winerIdx).getBetMoney();
				for (Entry<Integer,PlayerVO> s : playerMap.entrySet()) {
					if(s.getValue().getNo() == winerNo)
						continue;
					if(winerBetMoney < s.getValue().getBetMoney()) {
						s.getValue().balance(winerBetMoney - s.getValue().getBetMoney());
					}
					if(s.getValue().isAllIn()) {
						if(s.getValue().getMoney() < startMoney)
							Packing.sender(s.getValue().getPwSocket(), new Packet(Protocol.SENDOFF));
					}
				} //for
			} else {
				playerMap.get(winerIdx).winMoney(totalMoney);
			}
			
		} catch (NullPointerException e) {
			roomSpeaker(new Packet(Protocol.GAMEOVER,"승자가 게임을 포기하였습니다."));
		}
		gameStarted = false;
		String winMsg;
		if(cardName==null) {
			winMsg = playerMap.get(winerIdx).getNic()+"님의 기권승입니다. "+"/";
		} else {
			winMsg = playerMap.get(winerIdx).getNic()+"님이 "+cardName+"(으)로 승입니다. "+"/";
		}
		roomSpeaker(new Packet(Protocol.GAMEOVER,
					winMsg+
					winerIdx+"/" +
					totalMoney + "/" +
					playerMap.get(winerIdx).getMoney()
				));
		
		//플레이어 DB에 저장
		for (int i = 0; i < 5; i++) {
			if(playerMap.get(i) == null)
				continue;
				
			if(winerIdx==i) 
				playerMap.get(i).gameWin();
			 else 
				playerMap.get(i).gameLose();
			
			serverDAO.playerSave(playerMap.get(i));
		} //for
		
		for (Entry<Integer, PlayerVO> s : playerMap.entrySet()) {
			PlayerVO vo = serverDAO.selectOnePlayerWithNo(s.getValue().getNo());
			Packing.sender(s.getValue().getPwSocket(), new Packet(Protocol.RELOADMYVO,vo)); 
		} //for
		
		lobbyReloadBroadcast();
	} // gameOver();

	/**
	 * Room 초기화
	 */
	public void gameReset() {
		round = 1;	 // 1라운드
		round1First = true;
		round2First = false;
		turn = masterIndex-1; // turnProgress()에서 turn+1 을하고 진행
		startMoney = 100000;
		gameStarted = true;
		cardShuffle(); 		// 카드큐를 섞는다
		lastBetIdx = 0;
		beforeBetMoney = 0;
		for (Entry<Integer, PlayerVO> playerVO : playerMap.entrySet()) {
			playerVO.getValue().setLive(true);
			playerVO.getValue().pay(startMoney);
			totalMoney += startMoney;
		}
	}
	
	/**
	 * @param playerNo  
	 * @return 서버상 몇번째 idx return
	 */
	public int getPlayerIndex(int playerNo) {
		for (Entry<Integer, PlayerVO> entry : playerMap.entrySet()) {
			if (entry.getValue().getNo() == playerNo) {
				return entry.getKey();
			}
		}
		return -1;
	} // getPlayerIndex();
	
	public void setMasterNo(int no) {
		int index = getPlayerIndex(no);
		if (index != -1) {
			masterIndex = index;
			setMaster(playerMap.get(index).getNic());
		}
	} // setMasterNo();
	
	public String getRoomPw() {
		return roomPw;
	}

	public void setRoomPw(String roomPw) {
		this.roomPw = roomPw;
	}

	public String getPersonnel() {
		return personnel;
	}

	public void setPersonnel(String personnel) {
		this.personnel = personnel;
	}

	public Map<Integer, PlayerVO> getPlayerMap() {
		return playerMap;
	}

	public void setPlayerMap(Map<Integer, PlayerVO> playerMap) {
		this.playerMap = playerMap;
	}

	public long getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(long totalMoney) {
		this.totalMoney = totalMoney;
	}

	public long getBeforeBetMoney() {
		return beforeBetMoney;
	}

	public void setBeforeBetMoney(long beforeBetMoney) {
		this.beforeBetMoney = beforeBetMoney;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public int getLastBetIdx() {
		return lastBetIdx;
	}

	public void setLastBetIdx(int lastBetIdx) {
		this.lastBetIdx = lastBetIdx;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public boolean isRound1First() {
		return round1First;
	}

	public void setRound1First(boolean round1First) {
		this.round1First = round1First;
	}

	public boolean isRound2First() {
		return round2First;
	}

	public void setRound2First(boolean round2First) {
		this.round2First = round2First;
	}

	public boolean isAllIn() {
		return allIn;
	}

	public void setAllIn(boolean allIn) {
		this.allIn = allIn;
	}

	public void setRoomTitle(String str) {roomTitle = str; }
 	public void setMaster(String str) { master = str; }
	public Map<Integer, PlayerVO> getList() { return playerMap; }
	public void setList(Map<Integer, PlayerVO> map) { 		this.playerMap = map; }
 	public float[] getCardArr() { return cardArr; }
 	public void setCardArr(float[] cardArr) { this.cardArr = cardArr; }
 	public Queue<Float> getShuffledCard() { return shuffledCard; }
 	public void setShuffledCard(Queue<Float> shuffledCard) { this.shuffledCard = shuffledCard; }
	public String getRoomTitle() { return roomTitle; }
 	public String getMaster() { return master; }
 	public Integer getMasterIndex() { 		return masterIndex; }
 	public void setMasterIndex(Integer masterIndex) { this.masterIndex = masterIndex; }
 	public boolean isGameStarted() { return gameStarted; }
 	public void setGameStarted(boolean gameStarted) { this.gameStarted = gameStarted; }
	public int getRoomNo() { return roomNo; }
	public void setRoomNo(int roomNo) { this.roomNo = roomNo; }
	public long getStartMoney() { return startMoney; }
	public void setStartMoney(long startMoney) { this.startMoney = startMoney; }
} //class
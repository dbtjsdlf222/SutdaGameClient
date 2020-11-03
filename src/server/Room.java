package server;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonIgnore;

import util.Packing;
import vo.Packet;
import vo.PlayerVO;
import vo.Protocol;

public class Room {

	private static final Logger logger = LoggerFactory.getLogger(Room.class);
	private int roomNo;		// 방 번호
	private long startMoney; // 시작 금액
	private String title = "";
	private String password = "";
	private int maxPlayer;
	private boolean  privateRoom = false;	//비밀번호방
	private Map<Integer, PlayerVO> playerMap = new Hashtable<Integer, PlayerVO>(); // 방안에 있는 사람 리스트
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
	
	@JsonIgnore
	public int getPlayerSize() {
		return playerMap.size();
	}


	
	public int getMaxPlayer() { return maxPlayer; }
	public String getPassword() { return password; }
	public Map<Integer, PlayerVO> getPlayerMap() { return playerMap;}
	public long getTotalMoney() { return totalMoney; }
	public long getBeforeBetMoney() { return beforeBetMoney;} 
	public int getRound() { return round;}
	public int getLastBetIdx() { return lastBetIdx; }
	public int getTurn() { return turn; } 
	public boolean isRound1First() { return round1First; }
	public boolean isRound2First() { return round2First; }
	public boolean isAllIn() { return allIn; }
	public Map<Integer, PlayerVO> getList() { return playerMap; }
	public float[] getCardArr() { return cardArr; }
	public Queue<Float> getShuffledCard() { return shuffledCard; }
	public String getTitle() { return title; }
	public Integer getMasterIndex() { 		return masterIndex; }
	public boolean isGameStarted() { return gameStarted; }
	public int getRoomNo() { return roomNo; }
	public long getStartMoney() { return startMoney; }
	public String getMaster() { return master; }
	public boolean isPrivateRoom() { return privateRoom; }
	



	public void setMaxPlayer(int maxPlayer) {this.maxPlayer = maxPlayer; }
	public void setPassword(String password) { this.password = password; }
	public void setPlayerMap(Map<Integer, PlayerVO> playerMap) { this.playerMap = playerMap;}
	public void setTotalMoney(long totalMoney) { this.totalMoney = totalMoney;}
	public void setBeforeBetMoney(long beforeBetMoney) { this.beforeBetMoney = beforeBetMoney; }
	public void setRound(int round) {this.round = round; } 
	public void setLastBetIdx(int lastBetIdx) { this.lastBetIdx = lastBetIdx; }
	public void setTurn(int turn) {this.turn = turn; } 
	public void setRound1First(boolean round1First) { this.round1First = round1First; }
	public void setRound2First(boolean round2First) { this.round2First = round2First; }
	public void setAllIn(boolean allIn) { this.allIn = allIn; }
	public void setTitle(String str) {title = str; }
 	public void setMaster(String str) { master = str; }
	public void setList(Map<Integer, PlayerVO> map) { this.playerMap = map; }
 	public void setCardArr(float[] cardArr) { this.cardArr = cardArr; }
 	public void setShuffledCard(Queue<Float> shuffledCard) { this.shuffledCard = shuffledCard; }
 	public void setMasterIndex(Integer masterIndex) { this.masterIndex = masterIndex; }
 	public void setGameStarted(boolean gameStarted) { this.gameStarted = gameStarted; }
	public void setRoomNo(int roomNo) { this.roomNo = roomNo; }
	public void setStartMoney(long startMoney) { this.startMoney = startMoney; }
	public void setPrivateRoom(boolean privateRoom) { this.privateRoom = privateRoom; }
} //class
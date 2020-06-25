package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import operator.RoomOperator;
import vo.PlayerVO;

public class CalcCardLevel implements Comparable {

	private static List<CalcCardLevel> playerCardList = new ArrayList<>();
	private int idx;
	private float card1;
	private float card2;
	private int cardLevel = 0;
	private String cardName;
	private Integer ddaeng= null;
	private Integer catchDdaeng = null;
	private Integer gusa= null;
	private Integer mungGusa= null;
	private Integer amhaeng= null;
	private Integer kwang138= null;
	private Integer kwang38= null;
	
	public CalcCardLevel() { }

	@SuppressWarnings("unchecked")
	public void getWinner(int roomNo,Map<Integer, PlayerVO> playerMap) {
		
		CardLevel(playerMap);
		
		if(mungGusa!=null) {
			System.out.println("멍구사 재경기");
			RoomOperator.getInstance().getRoom(roomNo).handOutCard();
			return;
		} else if (gusa!=null) {
			System.out.println("구사 재경기");
			RoomOperator.getInstance().getRoom(roomNo).handOutCard();
			return;
		}
		
		Collections.sort(playerCardList);
		
		if(playerCardList.get(0).getCardLevel() != playerCardList.get(1).getCardLevel()) {
			RoomOperator.getInstance().getRoom(roomNo).gameOver(playerCardList.get(0).getIdx());
		} else {
			System.out.println("동점 재경기");
			for (int i = 2; i < 5; i++) {
				try {
					if(playerCardList.get(0).getCardLevel() != playerCardList.get(i).getCardLevel()) {
						playerMap.get(playerCardList.get(i).getIdx()).setLive(false);
						
					}
				} catch (Exception e) { } //try~catch
			} //for
			RoomOperator.getInstance().getRoom(roomNo).draw();
		} //if~else
	} // getWinner();

	public void CardLevel(Map<Integer, PlayerVO> playerMap) {
		for (int i = 0; i < 5; i++) {
			
			if (playerMap.get(i) == null)
				continue;
			float card1 = playerMap.get(i).getCard1();
			float card2 = playerMap.get(i).getCard2();
			CalcCardLevel cal = new CalcCardLevel(card1, card2);
			cal.idx = i;
			if ((card1 == 3 && card2 == 8) || (card1 == 8 && card2 == 3)) { // 38광땡
				cal.cardLevel = 3800;
				cal.setCardName("38광땡");
				gusa = null;
				mungGusa = null;
			} else if ((card1 == 1 && card2 == 8) || (card1 == 8 && card2 == 1)) { // 18광땡
				cal.cardLevel = 1800;
				cal.setCardName("18광땡");
				gusa = null;
				mungGusa = null;
				
				if(amhaeng!=null) 
					playerCardList.get(amhaeng).setCardLevel(2000);
				 else 
					kwang138=i;
				
			} else if ((card1 == 1 && card2 == 3) || (card1 == 3 && card2 == 1)) { // 13광땡
				cal.cardLevel = 1300;
				cal.setCardName("13광땡");
				gusa = null;
				mungGusa = null;
				if(amhaeng!=null)
					playerCardList.get(amhaeng).setCardLevel(2000);
				else
					kwang138=i;
			}

			if (Math.abs(card1) == Math.abs(card2)) {
				cal.cardLevel = (int) (Math.abs(card1) * 100); // 땡
				gusa = null;
				if (cal.cardLevel == 1000) {
					cal.setCardName("장땡");
					mungGusa = null;
				} else {
					cal.setCardName(cal.cardLevel + "땡");
					if (catchDdaeng != null) {
						playerCardList.get(catchDdaeng).setCardLevel(950);
					}
				} // if
				gusa = null;
				ddaeng = i;
			} // 떙

			if (((Math.abs(card1) == 1) && (Math.abs(card2) == 2))
					|| ((Math.abs(card1) == 2) && (Math.abs(card2) == 1))) { // 알리
				cal.cardLevel = 90;
				cal.setCardName("알리");
			} else if (((Math.abs(card1) == 1) && (Math.abs(card2) == 4))
					|| ((Math.abs(card1) == 4) && (Math.abs(card2) == 1))) { // 독사
				cal.cardLevel = 80;
				cal.setCardName("독사");
			} else if (((Math.abs(card1) == 1) && (Math.abs(card2) == 9))
					|| ((Math.abs(card1) == 9) && (Math.abs(card2) == 1))) { // 구삥
				cal.cardLevel = 70;
				cal.setCardName("구삥");
			} else if (((Math.abs(card1) == 1) && (Math.abs(card2) == 10))
					|| ((Math.abs(card1) == 10) && (Math.abs(card2) == 1))) { // 장삥
				cal.cardLevel = 60;
				cal.setCardName("장삥");
			} else if (((Math.abs(card1) == 4) && (Math.abs(card2) == 10))
					|| ((Math.abs(card1) == 10) && (Math.abs(card2) == 4))) { // 장사
				cal.cardLevel = 50;
				cal.setCardName("장사");
			} else if (((Math.abs(card1) == 4) && (Math.abs(card2) == 6))
					|| ((Math.abs(card1) == 6) && (Math.abs(card2) == 4))) { // 세륙
				cal.cardLevel = 40;
				cal.setCardName("세륙");
			}

			if (((Math.abs(card1) == 3) && (Math.abs(card2) == 7))
			 || ((Math.abs(card1) == 7) && (Math.abs(card2) == 3))) {
				cal.cardLevel = 0;
				cal.cardName = "땡잡이";
				ddaeng = i;
				if (ddaeng!=null && playerCardList.get(ddaeng).cardLevel != 1000) {	//떙을 가진 사람이 있으면 9땡보다 높게 책정
					cal.cardLevel = 950;	//장땡은 못잡으니 1000 보다 아래인 950으로 책정
				} else {
					catchDdaeng = i;
				}
			} // 땡잡이

			if ((card1 == 4 && card2 == 7) || (card1 == 7 && card2 == 4)) {
				cal.setCardName("암행어사");
				if(kwang138!=null) {
					cal.setCardLevel(2000);
					cal.cardLevel = 2000;
				} else {
					amhaeng = i;
					cal.cardLevel = 1;
				}
			} // if
			
			// 암행어사
			if ((card1 == 4 && card2 == 9.5) || ((card1 == 4.5) && (Math.abs(card2) == 9))
			 || (card1 == 9.5 && card2 == 4) || ((Math.abs(card1) == 9) && (card2 == 4.5))) {

				cal.setCardName("구사"); //알리
				if(ddaeng==null && kwang138==null && kwang38==null) {
					gusa = i;
				}
				cal.cardLevel = 3;
				cal.setCardName("3끗");
				continue;
			} else if ((card1 == 4 && card2 == 9) || (card1 == 9 && card2 == 4)) {
				cal.setCardName("멍구사");
				if(kwang138==null && kwang38==null) {
					if(ddaeng!=null) {
						if(!(playerCardList.get(ddaeng).getCardName().equals("장땡")))
							mungGusa = i;
					} else {
						mungGusa = i;
					}
				}
				cal.cardLevel = 3;
			}

			cal.cardLevel = (int) ((Math.abs(card1) + Math.abs(card2)) % 10); // 끗

			if (cal.cardLevel == 0) {
				cal.setCardName("망통");
			} else {
				cal.setCardName(cal.cardLevel + "끗");
			}
			
			playerCardList.add(cal);
		} // for
	} // CardLevel()

	@Override
	public int compareTo(Object o) {
		if(o instanceof CalcCardLevel) {
			CalcCardLevel ccl = (CalcCardLevel)o;
			
			if(cardLevel > ccl.getCardLevel()) {
				return -1;
			} else if (cardLevel == ccl.getCardLevel()) {
				return 0;
			} else {
				return 1;
			}
		}
		return 0;
	} //compareTo();
	
	public float getCard1() { return card1;}
	public void setCard1(float card1) {this.card1 = card1;}
	public float getCard2() {return card2;}
	public void setCard2(float card2) {this.card2 = card2;}
	public int getCardLevel() { return cardLevel; }
	public void setCardLevel(int cardLevel) { this.cardLevel = cardLevel; }
	public String getCardName() { return cardName; }
	public void setCardName(String cardName) { this.cardName = cardName;}
	public int getIdx() { return idx;}
	public void setIdx(int idx) { this.idx = idx; }
	public CalcCardLevel(float card1, float card2) {
		this.card1 = card1;
		this.card2 = card2;
//		CardLevel(idx, card1, card2);
	}
} //class
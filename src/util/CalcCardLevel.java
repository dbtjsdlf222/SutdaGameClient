package util;

import java.util.ArrayList;
import java.util.Map;

import server.Room;
import vo.PlayerVO;

public class CalcCardLevel {

	private static ArrayList<CalcCardLevel> playerCardList = new ArrayList<>();

	private int playerIdx;
	private float card1;
	private float card2;
	private int cardLevel = 0;
	private String CardName;
	private boolean ddaeng = false;
	private boolean gusa = false;
	private boolean amhaeng = false;

	public CalcCardLevel() { }

	public CalcCardLevel(int idx, float card1, float card2) {
		this.playerIdx = idx;
		this.card1 = card1;
		this.card2 = card2;
		CardLevel(idx, card1, card2);
	}

	public void getWinner(Map<Integer, PlayerVO> playerMap) {
		for (int i = 0; i < 5; i++) {
			if (playerMap.get(i) == null)
				continue;

			playerCardList.add(new CalcCardLevel(i, playerMap.get(i).getCard1(), playerMap.get(i).getCard2()));
		}
	} // getWinner();

	public void CardLevel(int i, float card1, float card2) {

		if ((card1 == 3 && card2 == 8) || (card1 == 8 && card2 == 3)) { // 38광땡
			cardLevel = 3800;
			CardName = "38광땡";
		} else if ((card1 == 1 && card2 == 8) || (card1 == 8 && card2 == 1)) { // 18광땡
			cardLevel = 1800;
			CardName = "18광땡";
		} else if ((card1 == 1 && card2 == 3) || (card1 == 3 && card2 == 1)) { // 13광땡
			cardLevel = 1300;
			CardName = "13광땡";
		}
		
		if (Math.abs(card1) == Math.abs(card2)) {
			cardLevel = (int) (Math.abs(card1) * 100); // 땡
			
			if(cardLevel == 10){
				CardName ="장땡";
			} else {
				CardName = cardLevel+"땡";
			}
			return;
		}
		if (((Math.abs(card1) == 1) && (Math.abs(card2) == 2)) || ((Math.abs(card1) == 2) && (Math.abs(card2) == 1))) { // 알리
			cardLevel = 90;
			CardName = "알리";
		} else if (((Math.abs(card1) == 1) && (Math.abs(card2) == 4))
				|| ((Math.abs(card1) == 4) && (Math.abs(card2) == 1))) { // 독사
			cardLevel = 80;
			CardName = "독사";
		} else if (((Math.abs(card1) == 1) && (Math.abs(card2) == 9))
				|| ((Math.abs(card1) == 9) && (Math.abs(card2) == 1))) { // 구삥
			cardLevel = 70;
			CardName = "구삥";
		} else if (((Math.abs(card1) == 1) && (Math.abs(card2) == 10))
				|| ((Math.abs(card1) == 10) && (Math.abs(card2) == 1))) { // 장삥
			cardLevel = 60;
			CardName = "장삥";
		} else if (((Math.abs(card1) == 4) && (Math.abs(card2) == 10))
				|| ((Math.abs(card1) == 10) && (Math.abs(card2) == 4))) { // 장사
			cardLevel = 50;
			CardName = "장사";
		} else if (((Math.abs(card1) == 4) && (Math.abs(card2) == 6))
				|| ((Math.abs(card1) == 6) && (Math.abs(card2) == 4))) { // 세륙
			cardLevel = 40;
			CardName = "세륙";
		}

		cardLevel = (int) ((Math.abs(card1) + Math.abs(card2)) % 10); // 끗
		
		if (cardLevel == 0) {
			CardName = "망통";
		} else {
			CardName = cardLevel + "끗";	
		}
	}

	//땡잡이
	public void ddaeng() {
		if (((Math.abs(card1) == 3) && (Math.abs(card2) == 7)) || ((Math.abs(card1) == 7) && (Math.abs(card2) == 3))) {
			cardLevel = 0;
			CardName = "망통";
			ddaeng = true;
			for (int i = 0; i < playerCardList.size(); i++) {
//				if (Math.abs(playerCardList.get(i).card1) == Math.abs(playerCardList.get(i).card2)) {
//					cardLevel = 950;	//장땡은 못잡으니 1000 보다 아래인 950으로 세팅
//					CardName = "땡잡이";
//				}
			}
		}
	} // ddaeng();

	//암행어사
	public boolean amhaeng() {
		if ((card1 == 4 && card2 == 7) || (card1 == 7 && card2 == 4)) {
			CardName = "암행어사";
			for (int i = 0; i < playerCardList.size(); i++) {
				if (playerCardList.get(i).cardLevel == 1800) {
					cardLevel = 2000;
					break;
				} else if (playerCardList.get(i).cardLevel == 1300) {
					cardLevel = 1500;
					break;
				}
			} //for
		} //if
		return true;
	} // amhaeng();

	public void gusa() {
		if ((card1 == 4 && card2 == 9.5) || ((card1 == 4.5) && (Math.abs(card2) == 9)) || (card1 == 9.5 && card2 == 4)
				|| ((Math.abs(card1) == 9) && (card2 == 4.5))) {
			if (cardLevel <= 90) {
				CardName = "구사";
				// return rematch();
			} else if (cardLevel >= 100) {
				cardLevel = 3;
				CardName = "3끗";
			}
		}
	} // 구사 재경기

	public void mungsa() {
		if ((card1 == 4 && card2 == 9) || (card1 == 9 && card2 == 4)) {
			if (cardLevel <= 900) {
				CardName = "멍구사";
				// return rematch();
			} else if (cardLevel >= 1000) {
				cardLevel = 3;
				CardName = "3끗";
			}
		}
	}// 멍텅구리 구사 재경기

}
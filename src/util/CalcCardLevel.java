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
	private int getCardLevel = 0;
	private String CardName;
	private boolean ddaeng = false;
	private boolean gusa = false;
	private boolean amhaeng = false;

	public CalcCardLevel() {
	}

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

	public int CardLevel(int i, float card1, float card2) {

		if ((card1 == 3 && card2 == 8) || (card1 == 8 && card2 == 3)) { // 38광땡
			getCardLevel = 3800;
			CardName = "38광땡";
		} else if ((card1 == 1 && card2 == 8) || (card1 == 8 && card2 == 1)) { // 18광땡
			getCardLevel = 1800;
			CardName = "18광땡";
		} else if ((card1 == 1 && card2 == 3) || (card1 == 3 && card2 == 1)) { // 13광땡
			getCardLevel = 1300;
			CardName = "13광땡";
		}

		if (Math.abs(card1) == Math.abs(card2))
			getCardLevel = (int) (Math.abs(card1) * 100); // 땡

		if (((Math.abs(card1) == 1) && (Math.abs(card2) == 2)) || ((Math.abs(card1) == 2) && (Math.abs(card2) == 1))) { // 알리
			getCardLevel = 90;
			CardName = "알리";
		} else if (((Math.abs(card1) == 1) && (Math.abs(card2) == 4))
				|| ((Math.abs(card1) == 4) && (Math.abs(card2) == 1))) { // 독사
			getCardLevel = 80;
			CardName = "독사";
		} else if (((Math.abs(card1) == 1) && (Math.abs(card2) == 9))
				|| ((Math.abs(card1) == 9) && (Math.abs(card2) == 1))) { // 구삥
			getCardLevel = 70;
			CardName = "구삥";
		} else if (((Math.abs(card1) == 1) && (Math.abs(card2) == 10))
				|| ((Math.abs(card1) == 10) && (Math.abs(card2) == 1))) { // 장삥
			getCardLevel = 60;
			CardName = "장삥";
		} else if (((Math.abs(card1) == 4) && (Math.abs(card2) == 10))
				|| ((Math.abs(card1) == 10) && (Math.abs(card2) == 4))) { // 장사
			getCardLevel = 50;
			CardName = "장사";
		} else if (((Math.abs(card1) == 4) && (Math.abs(card2) == 6))
				|| ((Math.abs(card1) == 6) && (Math.abs(card2) == 4))) { // 세륙
			getCardLevel = 40;
			CardName = "세륙";
		}

		getCardLevel = (int) ((Math.abs(card1) + Math.abs(card2)) % 10); // 끗
		CardName = (getCardLevel) + "끗";
		if (getCardLevel == 0)
			CardName = "망통";
		return getCardLevel;
	}

	public boolean ddaeng() {
		if (((Math.abs(card1) == 3) && (Math.abs(card2) == 7)) || ((Math.abs(card1) == 7) && (Math.abs(card2) == 3))) {
			for (int i = 0; i < playerCardList.size(); i++) {
				if (Math.abs(playerCardList.get(i).card1) == Math.abs(playerCardList.get(i).card2)) {
					getCardLevel = 950;
					CardName = "땡잡이";
				} else if (Math.abs(card1) != Math.abs(card2)) {
					getCardLevel = 0;
					CardName = "망통";
				}
			}
		}
		return true;
	} // 땡잡이

	public boolean amhaeng() {
		if ((card1 == 4 && card2 == 7) || (card1 == 7 && card2 == 4)) {
			for (int i = 0; i < playerCardList.size(); i++) {
				if (playerCardList.get(i).getCardLevel() == 1800) {
					getCardLevel = 2000;
					CardName = "암행어사";
				} else if (playerCardList.get(i).getCardLevel() == 1300) {
					getCardLevel = 1500;
					CardName = "암행어사";
				}
			}
		}
		return true;
	} // 암행어사

	public void gusa() {
		if ((card1 == 4 && card2 == 9.5) || ((card1 == 4.5) && (Math.abs(card2) == 9)) || (card1 == 9.5 && card2 == 4)
				|| ((Math.abs(card1) == 9) && (card2 == 4.5))) {
			if (getCardLevel <= 90) {
				CardName = "구사";
				// return rematch();
			} else if (getCardLevel >= 100) {
				getCardLevel = 3;
				CardName = "3끗";
			}
		}

	} // 구사 재경기

	public void mungsa() {
		if ((card1 == 4 && card2 == 9) || (card1 == 9 && card2 == 4)) {
			if (getCardLevel <= 900) {
				CardName = "멍구사";
				// return rematch();
			} else if (getCardLevel >= 1000) {
				getCardLevel = 3;
				CardName = "3끗";
			}
		}
	}// 멍텅구리 구사 재경기

}
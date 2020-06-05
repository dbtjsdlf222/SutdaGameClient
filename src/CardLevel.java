import java.util.ArrayList;

import vo.PlayerVO;
import vo.PlayerVOsunil;
import vo.Room;

public class CardLevel extends Room {
	int card[] = new int[20];
	ArrayList<PlayerVO> playerVO = new ArrayList<>();
	public float card1;
	public float card2;
	private boolean ddaeng = false;
	private boolean gusa = false;
	private boolean mungsa = false;
	private boolean amhaeng = false;
	private int getCardLevel = 0;

	public CardLevel() {
		super();
	}

	public boolean ddaeng() {
		if (((Math.abs(card1) == 3) && (Math.abs(card2) == 7)) || ((Math.abs(card1) == 7) && (Math.abs(card2) == 3))) {
			for (int i = 0; i < list.size(); i++) {
				if (Math.abs(list.get(i).getCard1()) == Math.abs(list.get(i).getCard2())) {
					getCardLevel = 950;
				} else if (Math.abs(card1) != Math.abs(card2)) {
					getCardLevel = 0;
				}
			}

		}
		return true;
	} // 땡잡이

	public boolean amhaeng() {
		if ((card1 == 4 && card2 == 7) || (card1 == 7 && card2 == 4)) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getCardLevel() == 1800) {
					getCardLevel = 2000;
				} else if (list.get(i).getCardLevel() == 1300) {
					getCardLevel = 1500;
				}
			}
		}
		return true;
	} // 암행어사

	public void gusa() {
		if ((card1 == 4 && card2 == 9.5) || ((card1 == 4.5) && (Math.abs(card2) == 9)) || (card1 == 9.5 && card2 == 4) || ((Math.abs(card1) == 9) && (card2 == 4.5))){
			return rematch();
		}
	} // 구사 재경기

	public void mungsa() {
		if ((card1 == 4 && card2 == 9) || (card1 == 9 && card2 == 4)) {
			return rematch();
		}
	} // 멍텅구리 구사 재경기

	public int getCardLevel(float card1, float card2) {
		int getCardLevel = 0;

		if ((card1 == 3 && card2 == 8) || (card1 == 8 && card2 == 3)) { // 38광땡
			getCardLevel = 3800;
		} else if ((card1 == 1 && card2 == 8) || (card1 == 8 && card2 == 1)) { // 18광땡
			getCardLevel = 1800;
		} else if ((card1 == 1 && card2 == 3) || (card1 == 3 && card2 == 1)) { // 13광땡
			getCardLevel = 1300;
		}

		amhaeng();

		if (Math.abs(card1) == Math.abs(card2))
			getCardLevel = (int) (Math.abs(card1) * 100); // 땡

		if (((Math.abs(card1) == 1) && (Math.abs(card2) == 2)) || ((Math.abs(card1) == 2) && (Math.abs(card2) == 1))) {	//알리
			getCardLevel = 90;
		} else if (((Math.abs(card1) == 1) && (Math.abs(card2) == 4)) || ((Math.abs(card1) == 4) && (Math.abs(card2) == 1))) { // 독사
			getCardLevel = 80;
		} else if (((Math.abs(card1) == 1) && (Math.abs(card2) == 9)) || ((Math.abs(card1) == 9) && (Math.abs(card2) == 1))) { // 구삥
			getCardLevel = 70;
		} else if (((Math.abs(card1) == 1) && (Math.abs(card2) == 10)) || ((Math.abs(card1) == 10) && (Math.abs(card2) == 1))) { // 장삥
			getCardLevel = 60;
		} else if (((Math.abs(card1) == 4) && (Math.abs(card2) == 10)) || ((Math.abs(card1) == 10) && (Math.abs(card2) == 4))) { // 장사
			getCardLevel = 50;
		} else if (((Math.abs(card1) == 4) && (Math.abs(card2) == 6)) || ((Math.abs(card1) == 6) && (Math.abs(card2) == 4))) { // 세륙
			getCardLevel = 40;
		}

		getCardLevel = (int) ((Math.abs(card1) + Math.abs(card2)) % 10); // 끗

		ddaeng();

		return getCardLevel;
	}
}
import java.util.ArrayList;

import vo.PlayerVO;
import vo.PlayerVO;
import vo.Room;

public class CardLevel extends Room {
	int card[] = new int[20];
	ArrayList<PlayerVO> playerVO = new ArrayList<>();
	public float card1;
	public float card2;
	private int getCardLevel = 0;

	public CardLevel() {
		super();
	}

	public int CardLevel() {

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

		return getCardLevel;
	}
	
	Jokbo jokbo = new Jokbo();
}
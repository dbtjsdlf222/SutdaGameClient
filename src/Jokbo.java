import java.util.ArrayList;
import java.util.Arrays;

import vo.PlayerVO;

public class Jokbo {
	int card[] = new int[20];
	ArrayList<PlayerVO> playerVO = new ArrayList<>();
	public float card1;
	public float card2;
	private boolean ddaeng = false;
	private boolean gusa = false;
	private boolean mungsa = false;
	private boolean amhaeng = false;
	private int getCardLevel = 0;

	public Jokbo() {
		super();
	}

	public boolean ddaeng() {
		if (((Math.abs(card1) == 3) && (Math.abs(card2) == 7)) || ((Math.abs(card1) == 7) && (Math.abs(card2) == 3))) {
			for (int i = 0; i < playerVO.size(); i++) {
				if (Math.abs(playerVO.get(i).getCard1()) == Math.abs(playerVO.get(i).getCard2())) {
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
			for (int i = 0; i < playerVO.size(); i++) {
				if (playerVO.get(i).getCardLevel() == 1800) {
					getCardLevel = 2000;
				} else if (playerVO.get(i).getCardLevel() == 1300) {
					getCardLevel = 1500;
				}
			}
		}
		return true;
	} // 암행어사

	public void gusa() {
		if ((card1 == 4 && card2 == 9.5) || ((card1 == 4.5) && (Math.abs(card2) == 9)) || (card1 == 9.5 && card2 == 4)
				|| ((Math.abs(card1) == 9) && (card2 == 4.5))) {
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

	@Override
	public String toString() {
		return "Jokbo [card=" + Arrays.toString(card) + ", playerVO=" + playerVO + ", card1=" + card1 + ", card2="
				+ card2 + ", ddaeng=" + ddaeng + ", gusa=" + gusa + ", mungsa=" + mungsa + ", amhaeng=" + amhaeng
				+ ", getCardLevel=" + getCardLevel + "]";
	}
}

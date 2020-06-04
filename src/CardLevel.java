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

	public CardLevel() {
		super();
	}

	public static void main(String[] args) {

	}

	public int getCardLevel(float card1, float card2) {
		int getCardLevel = 0;

		if ((card1 == 3 && card2 == 8) || (card1 == 8 && card2 == 3)) { // 38광땡
			getCardLevel = 3800;
		} else if ((card1 == 1 && card2 == 8) || (card1 == 8 && card2 == 1)) { // 18광땡
			getCardLevel = 1800;
		} else if ((card1 == 1 && card2 == 3) || (card1 == 3 && card2 == 1)) { // 13광땡
			getCardLevel = 1300;
		}

		if (Math.abs(card1) == Math.abs(card2))
			getCardLevel = (int) (Math.abs(card1) * 100); // 땡

		if (((card1 == 1 || card1 == 1.5) && (card2 == 2 || card2 == 2.5))
				|| ((card1 == 2 || card1 == 2.5) && (card2 == 1 || card2 == 1.5))) { // 알리
			getCardLevel = 90;
		} else if (((card1 == 1 || card1 == 1.5) && (card2 == 4 || card2 == 4.5))
				|| ((card1 == 4 || card1 == 4.5) && (card2 == 1 || card2 == 1.5))) { // 독사
			getCardLevel = 80;
		} else if (((card1 == 1 || card1 == 1.5) && (card2 == 9 || card2 == 9.5))
				|| ((card1 == 9 || card1 == 9.5) && (card2 == 1 || card2 == 1.5))) { // 구삥
			getCardLevel = 70;
		} else if (((card1 == 1 || card1 == 1.5) && (card2 == 10 || card2 == 10.5))
				|| ((card1 == 10 || card1 == 10.5) && (card2 == 1 || card2 == 1.5))) { // 장삥
			getCardLevel = 60;
		} else if (((card1 == 4 || card1 == 4.5) && (card2 == 10 || card2 == 10.5))
				|| ((card1 == 10 || card1 == 10.5) && (card2 == 4 || card2 == 4.5))) { // 장사
			getCardLevel = 50;
		} else if (((card1 == 4 || card1 == 4.5) && (card2 == 6 || card2 == 6.5))
				|| ((card1 == 6 || card1 == 6.5) && (card2 == 4 || card2 == 4.5))) { // 세륙
			getCardLevel = 40;
		}

		getCardLevel = (int) ((Math.abs(card1) + Math.abs(card2)) % 10); // 끗

		return getCardLevel;
	}
}
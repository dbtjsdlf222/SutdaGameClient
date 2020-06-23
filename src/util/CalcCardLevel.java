package util;

import java.util.ArrayList;
import java.util.Map;

import server.Room;
import vo.PlayerVO;

public class CalcCardLevel {

	private static ArrayList<CalcCardLevel> playerCardList = new ArrayList<>();

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
	private boolean remach = false;
	
	
	private CalcCardLevel bigScore = new CalcCardLevel();

	public CalcCardLevel() {
	}

	public int getCardLevel() {
		return cardLevel;
	}

	public void setCardLevel(int cardLevel) {
		this.cardLevel = cardLevel;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public CalcCardLevel(float card1, float card2) {
		this.card1 = card1;
		this.card2 = card2;
//		CardLevel(idx, card1, card2);
	}

	public void getWinner(Map<Integer, PlayerVO> playerMap) {
		PlayerVO height = null;
		int draw = 0;
		CardLevel(playerMap);
		
		if(mungGusa!=null) {
			System.out.println("멍구사 재경기");
		} else if (gusa!=null) {
			System.out.println("구사 재경기");
		}
		
		for (int i = 0; i < 5; i++) {
			try {
				if(playerMap.get(i).getCardLevel() >= height.getCardLevel()) {
					
				}
			} catch (NullPointerException e) {
				height
			}
		} //for
	} // getWinner();

	public void CardLevel(Map<Integer, PlayerVO> playerMap) {
		for (int i = 0; i < 5; i++) {
			
			if (playerMap.get(i) == null)
				continue;
			
			CalcCardLevel cal = new CalcCardLevel(playerMap.get(i).getCard1(), playerMap.get(i).getCard2());

			if ((card1 == 3 && card2 == 8) || (card1 == 8 && card2 == 3)) { // 38광땡
				cal.cardLevel = 3800;
				cal.setCardName("38광땡");
				gusa = null;
				mungGusa = null;
				continue;
			} else if ((card1 == 1 && card2 == 8) || (card1 == 8 && card2 == 1)) { // 18광땡
				cal.cardLevel = 1800;
				cal.setCardName("18광땡");
				gusa = null;
				mungGusa = null;
				
				if(amhaeng!=null) 
					playerCardList.get(amhaeng).setCardLevel(2000);
				 else 
					kwang138=i;
				
				continue;
			} else if ((card1 == 1 && card2 == 3) || (card1 == 3 && card2 == 1)) { // 13광땡
				cal.cardLevel = 1300;
				cal.setCardName("13광땡");
				gusa = null;
				mungGusa = null;
				if(amhaeng!=null)
					playerCardList.get(amhaeng).setCardLevel(2000);
				else
					kwang138=i;
				continue;
			}

			if (Math.abs(card1) == Math.abs(card2)) {
				cal.cardLevel = (int) (Math.abs(card1) * 100); // 땡
				gusa = null;
				if (cal.cardLevel == 10) {
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
				continue;
			} // 떙

			if (((Math.abs(card1) == 1) && (Math.abs(card2) == 2))
					|| ((Math.abs(card1) == 2) && (Math.abs(card2) == 1))) { // 알리
				cal.cardLevel = 90;
				cal.setCardName("알리");
				continue;
			} else if (((Math.abs(card1) == 1) && (Math.abs(card2) == 4))
					|| ((Math.abs(card1) == 4) && (Math.abs(card2) == 1))) { // 독사
				cal.cardLevel = 80;
				cal.setCardName("독사");
				continue;
			} else if (((Math.abs(card1) == 1) && (Math.abs(card2) == 9))
					|| ((Math.abs(card1) == 9) && (Math.abs(card2) == 1))) { // 구삥
				cal.cardLevel = 70;
				cal.setCardName("구삥");
				continue;
			} else if (((Math.abs(card1) == 1) && (Math.abs(card2) == 10))
					|| ((Math.abs(card1) == 10) && (Math.abs(card2) == 1))) { // 장삥
				cal.cardLevel = 60;
				cal.setCardName("장삥");
				continue;
			} else if (((Math.abs(card1) == 4) && (Math.abs(card2) == 10))
					|| ((Math.abs(card1) == 10) && (Math.abs(card2) == 4))) { // 장사
				cal.cardLevel = 50;
				cal.setCardName("장사");
				continue;
			} else if (((Math.abs(card1) == 4) && (Math.abs(card2) == 6))
					|| ((Math.abs(card1) == 6) && (Math.abs(card2) == 4))) { // 세륙
				cal.cardLevel = 40;
				cal.setCardName("세륙");
				continue;
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
				continue;
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
				continue;
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
				continue;
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

	// 땡잡이
	/*
	 * public void ddaeng() { if (((Math.abs(card1) == 3) && (Math.abs(card2) == 7))
	 * || ((Math.abs(card1) == 7) && (Math.abs(card2) == 3))) { cal.cardLevel = 0;
	 * CardName = "망통"; ddaeng = true; for (int i = 0; i < playerCardList.size();
	 * i++) { // if (Math.abs(playerCardList.get(i).card1) ==
	 * Math.abs(playerCardList.get(i).card2)) { // cardLevel = 950; //장땡은 못잡으니 1000
	 * 보다 아래인 950으로 세팅 // CardName = "땡잡이"; // } } } } // ddaeng();
	 * 
	 * //암행어사 public boolean amhaeng() { if ((card1 == 4 && card2 == 7) || (card1 ==
	 * 7 && card2 == 4)) { CardName = "암행어사"; for (int i = 0; i <
	 * playerCardList.size(); i++) { if (playerCardList.get(i).cal.cardLevel ==
	 * 1800) { cal.cardLevel = 2000; break; } else if
	 * (playerCardList.get(i).cal.cardLevel == 1300) { cal.cardLevel = 1500; break;
	 * } } //for } //if return true; } // amhaeng();
	 * 
	 * public void gusa() { if ((card1 == 4 && card2 == 9.5) || ((card1 == 4.5) &&
	 * (Math.abs(card2) == 9)) || (card1 == 9.5 && card2 == 4) || ((Math.abs(card1)
	 * == 9) && (card2 == 4.5))) { if (cal.cardLevel <= 90) { CardName = "구사"; //
	 * return rematch(); } else if (cal.cardLevel >= 100) { cal.cardLevel = 3;
	 * CardName = "3끗"; } } } // 구사 재경기
	 * 
	 * public void mungsa() { if ((card1 == 4 && card2 == 9) || (card1 == 9 && card2
	 * == 4)) { if (cal.cardLevel <= 900) { CardName = "멍구사"; // return rematch(); }
	 * else if (cal.cardLevel >= 1000) { cal.cardLevel = 3; CardName = "3끗"; } } }//
	 * 멍텅구리 구사 재경기
	 */ public float getCard1() {
		return card1;
	}

	public void setCard1(float card1) {
		this.card1 = card1;
	}

	public float getCard2() {
		return card2;
	}

	public void setCard2(float card2) {
		this.card2 = card2;
	}

}

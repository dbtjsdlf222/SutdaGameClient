import java.util.ArrayList;

import vo.PlayerVO;

public class CardLevel {
	int card[] = new int[20];
	ArrayList<PlayerVO> playerVO = new ArrayList<>();
	public String cardSetNo;

	public int getCardLevel(String no1, String no2) {
		int getCardLevel = 0;
		if (no1.equals("3") && no2.equals("8")) { 				// 38광땡
			getCardLevel = 3800;
		} else if (no1.equals("1") && no2.equals("8")) { 		// 18광땡
			getCardLevel = 1800;
		} else if (no1.equals("1") && no2.equals("3")) { 		// 13광땡
			getCardLevel = 1300;
		} else if (no1.equals("10") && no2.equals("10.5")) {	// 장땡
			getCardLevel = 1000;
		} else if (no1.equals("9") && no2.equals("9.5")) { 		// 9땡
			getCardLevel = 900;
		} else if (no1.equals("8") && no2.equals("8.5")) { 		// 8땡
			getCardLevel = 800;
		} else if (no1.equals("7") && no2.equals("7.5")) { 		// 7땡
			getCardLevel = 700;
		} else if (no1.equals("6") && no2.equals("6.5")) { 		// 6땡
			getCardLevel = 600;
		} else if (no1.equals("5") && no2.equals("5.5")) {		// 5땡
			getCardLevel = 500;
		} else if (no1.equals("4") && no2.equals("4.5")) { 		// 4땡
			getCardLevel = 400;
		} else if (no1.equals("3") && no2.equals("3.5")) { 		// 3땡
			getCardLevel = 300;
		} else if (no1.equals("2") && no2.equals("2.5")) { 		// 2땡
			getCardLevel = 200;
		} else if (no1.equals("1") && no2.equals("1.5")) { 		// 1땡
			getCardLevel = 100;
		} else if ((no1.equals("1") || no1.equals("1.5")) && (no1.equals("4") || no2.equals("4.5"))) { 		// 알리
			getCardLevel = 90;
		} else if ((no1.equals("1") || no1.equals("1.5")) && (no1.equals("4") || no2.equals("4.5"))) { 		// 독사
			getCardLevel = 80;
		} else if ((no1.equals("1") || no1.equals("1.5")) && (no1.equals("9") || no2.equals("9.5"))) { 		// 구삥
			getCardLevel = 70;
		} else if ((no1.equals("1") || no1.equals("1.5")) && (no1.equals("10") || no2.equals("10.5"))) { 	// 장삥
			getCardLevel = 60;
		} else if ((no1.equals("4") || no1.equals("4.5")) && (no1.equals("10") || no2.equals("10.5"))) { 	// 장사
			getCardLevel = 50;
		} else if ((no1.equals("4") || no1.equals("4.5")) && (no1.equals("6") || no2.equals("6.5"))) { 		// 세륙
			getCardLevel = 40;
		} else if ((no1.equals("1") || no1.equals("1.5")) && (no1.equals("8") || no2.equals("8.5"))) { 		// 갑오
			getCardLevel = 9;
		} else if ((no1.equals("2") || no1.equals("2.5")) && (no1.equals("7") || no2.equals("7.5"))) {
			getCardLevel = 9;
		} else if ((no1.equals("3") || no1.equals("3.5")) && (no1.equals("6") || no2.equals("6.5"))) {
			getCardLevel = 9;
		} else if ((no1.equals("4") || no1.equals("4.5")) && (no1.equals("5") || no2.equals("5.5"))) {
			getCardLevel = 9;
		} else if ((no1.equals("9") || no1.equals("9.5")) && (no1.equals("10") || no2.equals("10.5"))) {
			getCardLevel = 9;
		} else if ((no1.equals("1") || no1.equals("1.5")) && (no1.equals("7") || no2.equals("7.5"))) { 		// 8끗
			getCardLevel = 8;
		} else if ((no1.equals("2") || no1.equals("2.5")) && (no1.equals("6") || no2.equals("6.5"))) {
			getCardLevel = 8;
		} else if ((no1.equals("3") || no1.equals("3.5")) && (no1.equals("5") || no2.equals("5.5"))) {
			getCardLevel = 8;
		} else if ((no1.equals("8") || no1.equals("8.5")) && (no1.equals("10") || no2.equals("10.5"))) {
			getCardLevel = 8;
		} else if ((no1.equals("1") || no1.equals("1.5")) && (no1.equals("6") || no2.equals("6.5"))) { 		// 7끗
			getCardLevel = 7;
		} else if ((no1.equals("2") || no1.equals("2.5")) && (no1.equals("5") || no2.equals("5.5"))) {
			getCardLevel = 7;
		} else if ((no1.equals("3") || no1.equals("3.5")) && (no1.equals("4") || no2.equals("4.5"))) {
			getCardLevel = 7;
		} else if ((no1.equals("7") || no1.equals("7.5")) && (no1.equals("10") || no2.equals("10.5"))) {
			getCardLevel = 7;
		} else if ((no1.equals("8") || no1.equals("8.5")) && (no1.equals("9") || no2.equals("9.5"))) {
			getCardLevel = 7;
		}else if((no1.equals("1") || no1.equals("1.5")) && (no1.equals("5") || no2.equals("5.5"))) {		// 6끗
			getCardLevel = 6;
		}else if((no1.equals("2") || no1.equals("2.5")) && (no1.equals("4") || no2.equals("4.5"))) {
			getCardLevel = 6;
		}else if((no1.equals("6") || no1.equals("6.5")) && (no1.equals("10") || no2.equals("10.5"))) {
			getCardLevel = 6;
		}else if((no1.equals("7") || no1.equals("7.5")) && (no1.equals("9") || no2.equals("9.5"))) {
			getCardLevel = 6;
		}else if((no1.equals("2") || no1.equals("2.5")) && (no1.equals("3") || no2.equals("3.5"))) {		// 5끗
			getCardLevel = 5;
		}else if((no1.equals("5") || no1.equals("5.5")) && (no1.equals("10") || no2.equals("10.5"))) {
			getCardLevel = 5;
		}else if((no1.equals("6") || no1.equals("6.5")) && (no1.equals("9") || no2.equals("9.5"))) {
			getCardLevel = 5;
		}else if((no1.equals("7") || no1.equals("7.5")) && (no1.equals("8") || no2.equals("8.5"))) {
			getCardLevel = 5;
		}else if((no1.equals("1") || no1.equals("1.5")) && (no1.equals("3") || no2.equals("3.5"))) {		// 4끗
			getCardLevel = 4;
		}else if((no1.equals("5") || no1.equals("5.5")) && (no1.equals("9") || no2.equals("9.5"))) {
			getCardLevel = 4;
		}else if((no1.equals("6") || no1.equals("6.5")) && (no1.equals("8") || no2.equals("8.5"))) {
			getCardLevel = 4;
		}else if((no1.equals("3") || no1.equals("3.5")) && (no1.equals("10") || no2.equals("10.5"))) {		// 3끗
			getCardLevel = 3;
		}else if((no1.equals("5") || no1.equals("5.5")) && (no1.equals("8") || no2.equals("8.5"))) {
			getCardLevel = 3;
		}else if((no1.equals("6") || no1.equals("6.5")) && (no1.equals("7") || no2.equals("7.5"))) {
			getCardLevel = 3;
		}else if((no1.equals("2") || no1.equals("2.5")) && (no1.equals("10") || no2.equals("10.5"))) {		// 2끗
			getCardLevel = 2;
		}else if((no1.equals("3") || no1.equals("3.5")) && (no1.equals("9") || no2.equals("9.5"))) {
			getCardLevel = 2;
		}else if((no1.equals("4") || no1.equals("4.5")) && (no1.equals("8") || no2.equals("8.5"))) {
			getCardLevel = 2;
		}else if((no1.equals("5") || no1.equals("5.5")) && (no1.equals("7") || no2.equals("7.5"))) {
			getCardLevel = 2;
		}else if((no1.equals("2") || no1.equals("2.5")) && (no1.equals("9") || no2.equals("9.5"))) {		// 1끗
			getCardLevel = 1;
		}else if((no1.equals("3") || no1.equals("3.5")) && (no1.equals("8") || no2.equals("8.5"))) {
			getCardLevel = 1;
		}else if((no1.equals("4") || no1.equals("4.5")) && (no1.equals("7") || no2.equals("7.5"))) {
			getCardLevel = 1;
			if() {}	//암행어사 조건문
		}else if((no1.equals("5") || no1.equals("5.5")) && (no1.equals("6") || no2.equals("6.5"))) {
			getCardLevel = 1;
		}else if((no1.equals("2") || no1.equals("2.5")) && (no1.equals("8") || no2.equals("8.5"))) {		// 망통
			getCardLevel = 0;
		}else if((no1.equals("3") || no1.equals("3.5")) && (no1.equals("7") || no2.equals("7.5"))) {		// 땡잡이
			getCardLevel = 0;
			if() {}	//땡잡이 조건문
		}else if((no1.equals("4") || no1.equals("4.5")) && (no1.equals("9") || no2.equals("9.5"))) {
			again;	//94재경기 리턴값 다르게
			if() {}	//멍텅구리 구사 조건문
		}
		return getCardLevel;
	}
}
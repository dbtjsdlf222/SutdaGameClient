package util;

import java.util.HashMap;
import java.util.Map;

import vo.PlayerVO;

public class CardTest {
	public static void main(String[] args) {
		
		Map<Integer,PlayerVO> map = new HashMap<>();
		
		PlayerVO player =  new PlayerVO();
		player.setCard1(10);
		player.setCard2(10.5F);
		map.put(1, player);
		
		player =  new PlayerVO();
		player.setCard1(1);
		player.setCard2(2);
		map.put(2, player);
		
		player =  new PlayerVO();
		player.setCard1(4);
		player.setCard2(7);
		map.put(3, player);
		
		player =  new PlayerVO();
		player.setCard1(3F);
		player.setCard2(7F);
		map.put(4, player);
		
		new CalcCardLevel().getWinner(1,map);
		
	}
}

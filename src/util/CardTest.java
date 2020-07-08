package util;

import java.util.HashMap;
import java.util.Map;

import vo.PlayerVO;

public class CardTest {
	public static void main(String[] args) {
		
		Map<Integer,PlayerVO> map = new HashMap<>();
		
		PlayerVO player =  new PlayerVO();
		player.setCard1(1F);
		player.setCard2(1.5F);
		map.put(1, player);
		
		player =  new PlayerVO();
		player.setCard1(2F);
		player.setCard2(2.5F);
		map.put(2, player);
		
		player =  new PlayerVO();
		player.setCard1(3F);
		player.setCard2(3.5F);
		map.put(3, player);
		
		player =  new PlayerVO();
		player.setCard1(3F);
		player.setCard2(5.5F);
		map.put(4, player);
		
		new CalcCardLevel().CardLevel(map);
		
	}
}

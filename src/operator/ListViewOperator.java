package operator;

import com.fasterxml.jackson.databind.ObjectMapper;

import vo.PlayerVO;

public class ListViewOperator {

	private static ListViewOperator instance;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	private ListViewOperator() {
	}

	

	public static ListViewOperator getInstance() {
		if (instance == null) {
			instance = new ListViewOperator();
		}
		return instance;
	}
	
	public void list(PlayerVO vo) {
//		Packet pac = new Packet(Protocol.JOINPLAYER,vo);
	}
		
	
	
	
}

package operator;
 
import java.io.PrintWriter;

import javax.swing.JTextArea;

import com.fasterxml.jackson.databind.ObjectMapper;

import vo.Packet;
import vo.PlayerVO;
import vo.Protocol;

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

package operator;

import javax.swing.JTextArea;
import util.Packing;
import vo.Protocol;
import vo.Packet;
import vo.PlayerVO;

public class ChattingOperator {
	private static ChattingOperator instance;
	
	private ChattingOperator() {}

	public static JTextArea chatArea = new JTextArea(); // 지금 사용하는 static area

	public static ChattingOperator getInstance() {
		if (instance == null)
			instance = new ChattingOperator();
		return instance;
	} //getInstance();

	public void chatting(String msg) {
		if(msg.substring(0, 1).equals("/")) {
			oneChatting(msg);
		}
		Packet pac = new Packet(Protocol.MESSAGE, msg, PlayerVO.myVO);
		Packing.sender(PlayerVO.myVO.getPwSocket(), pac);
	} //chatting();
	
	
	public void oneChatting(String msg) {
		String[] msgCheck = msg.split(" ");

		
		switch (msgCheck[0]) {
		case "/w":
			String nic = msgCheck[1];
			PlayerVO vo = new PlayerVO();
			vo.setNic(nic);
			
			String[] oneMsg = msg.split(msgCheck[0] + " " + nic);
			
			if(!PlayerVO.myVO.getNic().equals(nic)) {
				Packet pac = new Packet(Protocol.MESSAGE, oneMsg[1], vo);
				Packing.sender(PlayerVO.myVO.getPwSocket(), pac);
			}
			
			break;

		default:
			break;
		}
	}
	
} //class
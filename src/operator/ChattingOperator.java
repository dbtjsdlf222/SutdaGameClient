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
		Packet pac = new Packet(Protocol.MESSAGE, msg, PlayerVO.myVO);
		Packing.sender(PlayerVO.myVO.getPwSocket(), pac);
	} //chatting();
} //class
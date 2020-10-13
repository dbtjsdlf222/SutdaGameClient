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
		} else {
			Packet pac = new Packet(Protocol.MESSAGE, msg, PlayerVO.myVO);
			Packing.sender(PlayerVO.myVO.getPwSocket(), pac);
		}
	} //chatting();
	
	
	public void oneChatting(String msg) {
		String[] msgCheck = msg.split(" ");
		String nic = null;
		if(msgCheck.length<=2) {
			nic = msgCheck[1];
		}

		switch (msgCheck[0]) {
		case "/ㅈ":
		case "/w":
			if(msgCheck.length >=3) {
				PlayerVO vo = new PlayerVO();
				vo.setNic(nic);
			
				String[] oneMsg = msg.split(msgCheck[0] + " " + nic);
			
				if(!PlayerVO.myVO.getNic().equals(nic)) {
					Packet pac = new Packet(Protocol.WHISPER, oneMsg[1], vo);
					Packing.sender(PlayerVO.myVO.getPwSocket(), pac);
					ChattingOperator.chatArea.append("<보낸메시지>" + nic +" : " + oneMsg[1] + "\n");
				}else {
					ChattingOperator.chatArea.append("<SYSTEM> 자신에게 귓속말을 보낼 수 없습니다.\n");
				}
			} else {
				ChattingOperator.chatArea.append("<SYSTEM> /w [상대방 닉네임] [할말] 형식으로 입력해주세요.\n");
			}	
			break;
		
		case "/초대":
		case "/c":
			Packet packet = new Packet(Protocol.DO_INVITE, nic);
			Packing.sender(PlayerVO.myVO.getPwSocket(), packet);
			break;
			
		case "/b":
		case "/추방":
			Packet kickPacket = new Packet(Protocol.KICK, nic);
			Packing.sender(PlayerVO.myVO.getPwSocket(), kickPacket);
			break;
		case "/help":
			
			ChattingOperator.chatArea.append("<SYSTEM>\n귓속말 : /w 또는 /ㅈ [상대방 닉네임] [할말] \n초대 : /c 또는 /초대 [상대방 닉네임]\n추방 : /b 또는 /추방 [상대방 닉네임]\n");
			
			break;

		default:
			ChattingOperator.chatArea.append("<SYSTEM> 잘못된 명령어입니다. /help 를 참조하세요.");
			break;
		}
	}
	
} //class
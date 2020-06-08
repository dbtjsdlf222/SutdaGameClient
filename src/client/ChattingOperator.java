package client;

import java.io.PrintWriter;

import javax.swing.JTextArea;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vo.Protocol;
import vo.Packet;
import vo.PlayerVO;

public class ChattingOperator {
	private static ChattingOperator instance;
	
	private ChattingOperator() {}
	
	public static JTextArea chatArea = new JTextArea(); //지금 사용하는 static area 
	
	public static ChattingOperator getInstance() {
		if(instance == null) instance = new ChattingOperator();
		return instance;
	}
	
	public void chatting(String msg,PlayerVO vo) {
		System.out.println(msg+" " + vo.getPwSocket());
		PrintWriter pw = vo.getPwSocket();
		try {
			ObjectMapper mapper = new ObjectMapper();
			Packet pac = new Packet(Protocol.MESSAGE, msg, vo);	
			String m = mapper.writeValueAsString(pac);
			pw.println(m);
			
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
	}
}
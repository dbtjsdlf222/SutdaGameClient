package util;

import java.io.PrintWriter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vo.Packet;
import vo.PlayerVO;
import vo.Protocol;

public class Packing {
	
	public static void sender(PrintWriter pw, String pro, PlayerVO vo) {
		System.out.println("[Send(" + Protocol.getName(pro) +"(" + vo.getNo() +", " + vo.getNic()+"))] " + vo);
		ObjectMapper mapper = new ObjectMapper();
		try {
			pw.println(mapper.writeValueAsString(new Packet(pro,vo)));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	public static void sender(PrintWriter pw, Packet packet) {
		try { System.out.println("[Send(" + Protocol.getName(packet.getAction()) +"(" + packet.getPlayerVO().getNo() +", " + packet.getPlayerVO().getNic()+"))] " + packet); }
		catch(NullPointerException e) { System.out.println("[Send(" + Protocol.getName(packet.getAction()) + ")] " + packet); }
		ObjectMapper mapper = new ObjectMapper();
		try {
			pw.println(mapper.writeValueAsString(packet));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	public static void sender(PrintWriter pw, Packet packet,String motion) {
		System.out.println("[Send(" + motion +"(" + packet.getPlayerVO().getNo() +", " + packet.getPlayerVO().getNic()+"))] " + packet);
		ObjectMapper mapper = new ObjectMapper();
		try {
			packet.setMotion(motion);
			pw.println(mapper.writeValueAsString(packet));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public static void sender(PrintWriter pw, String pro) {
		System.out.println("[Send(" + pro +")]");
		ObjectMapper mapper = new ObjectMapper();
		Packet packet = new Packet();
		packet.setAction(pro);
		try {
			pw.println(mapper.writeValueAsString(packet));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
package util;

import java.io.PrintWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vo.Packet;
import vo.PlayerVO;
import vo.Protocol;

public class Packing {
	
	private static final Logger logger = LogManager.getLogger();
	
	public static void sender(PrintWriter pw, String pro, PlayerVO vo) {
		logger.info("[Send(" + Protocol.getName(pro) +"(" + vo.getNo() +", " + vo.getNic()+"))] " + vo);
		ObjectMapper mapper = new ObjectMapper();
		try {
			pw.println(mapper.writeValueAsString(new Packet(pro,vo)));
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public static void sender(PrintWriter pw, Packet packet) {
		try { logger.info("[Send(" + Protocol.getName(packet.getAction()) +"(" + packet.getPlayerVO().getNo() +", " + packet.getPlayerVO().getNic()+"))] " + packet); }
		catch(NullPointerException e) { logger.info("[Send(" + Protocol.getName(packet.getAction()) + ")] " + packet); }
		ObjectMapper mapper = new ObjectMapper();
		try {
			pw.println(mapper.writeValueAsString(packet));
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public static void sender(PrintWriter pw, Packet packet,String motion) {
		logger.info("[Send(" + motion +"(" + packet.getPlayerVO().getNo() +", " + packet.getPlayerVO().getNic()+"))] " + packet);
		ObjectMapper mapper = new ObjectMapper();
		try {
			packet.setMotion(motion);
			pw.println(mapper.writeValueAsString(packet));
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void sender(PrintWriter pw, String pro) {
		logger.info("[Send(" + pro +")]");
		ObjectMapper mapper = new ObjectMapper();
		Packet packet = new Packet();
		packet.setAction(pro);
		try {
			pw.println(mapper.writeValueAsString(packet));
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
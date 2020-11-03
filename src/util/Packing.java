package util;

import java.io.PrintWriter;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import server.Room;
import vo.Packet;
import vo.PlayerVO;
import vo.Protocol;

public class Packing {
	
	private static final Logger logger = LoggerFactory.getLogger(Packing.class);
	
	/**
	 * 
	 * @param pw
	 * @param pro
	 * @param vo	PlayerVO
	 */
	public static void sender(PrintWriter pw, String pro, PlayerVO vo) {
		logger.info("[Send(" + Protocol.getName(pro) +"(" + vo.getNo() +", " + vo.getNic()+"))] " + vo);
		ObjectMapper mapper = new ObjectMapper();
		try {
			pw.println(mapper.writeValueAsString(new Packet(pro,vo)));
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * @param pw
	 * @param pro
	 * @param room
	 */
	public static void sender(PrintWriter pw, String pro, Room room) {
		logger.info("[Send(" + Protocol.getName(pro) +"(" + room.getTitle()+", " + room.getPassword()+"))] " + room);
		ObjectMapper mapper = new ObjectMapper();
		try {
			pw.println(mapper.writeValueAsString(new Packet(pro,room)));
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * @param pw
	 * @param packet
	 */
	public static void sender(PrintWriter pw, Packet packet) {
		try { logger.info("[Send(" + Protocol.getName(packet.getProtocol()) +"(" + packet.getPlayerVO().getNo() +", " + packet.getPlayerVO().getNic()+"))] " + packet); }
		catch(NullPointerException e) { logger.info("[Send(" + Protocol.getName(packet.getProtocol()) + ")] " + packet); }
		ObjectMapper mapper = new ObjectMapper();
		try {
			pw.println(mapper.writeValueAsString(packet));
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * @param pw
	 * @param packet
	 * @param motion
	 */
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

	/**
	 * 
	 * @param pw
	 * @param pro
	 */
	public static void sender(PrintWriter pw, String pro) {
		logger.info("[Send(" + pro +")]");
		ObjectMapper mapper = new ObjectMapper();
		Packet packet = new Packet();
		packet.setProtocol(pro);
		try {
			pw.println(mapper.writeValueAsString(packet));
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage(), e);
		}
	}
	/**
	 * 
	 * @param pw
	 * @param pro
	 * @param motion
	 */
	public static void sender(PrintWriter pw, String pro, String motion) {
		logger.info("[Send(" + pro +")]");
		ObjectMapper mapper = new ObjectMapper();
		Packet packet = new Packet();
		packet.setProtocol(pro);
		packet.setMotion(motion);
		try {
			pw.println(mapper.writeValueAsString(packet));
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage(), e);
		}
	} //sender
}
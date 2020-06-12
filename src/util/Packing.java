package util;

import java.io.PrintWriter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vo.Packet;
import vo.PlayerVO;

public class Packing {
	public static void sender(PrintWriter pw, String pro, PlayerVO vo) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			pw.println(mapper.writeValueAsString(new Packet(pro,vo)));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
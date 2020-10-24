package dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import client.service.ClientPacketController;
import client.service.ClientPacketSender;
import client.ui.Lobby;
import connection.DBCon;
import vo.Packet;
import vo.PlayerVO;

public class PlayerDAO {
	
	private static final Logger logger = LogManager.getLogger();
	
	private Connection conn;
	private PreparedStatement pstmt;

	public PlayerDAO() {
		try {
			conn = new DBCon().getMysqlConn();
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
	}

//	public String getServerIP() {
//		return "192.168.0.79";
//	}

	public void setServerIP() {
		String sql = "UPDATE player SET ip = ? WHERE id = 'SERVER'";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, InetAddress.getLocalHost().getHostAddress());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} catch (UnknownHostException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public int playerJoin(PlayerVO vo) throws ClassNotFoundException {
		ClientPacketSender.instance.join(vo);
		Packet result = getResponse();
		System.out.println(result.getMotion());
		if(result.getMotion().equals("1"))
			return 1;
		else 
			return 0;
	}//playerJoin()
	
	public PlayerVO login(String id, String pw) {
		ClientPacketSender.instance.login(id, pw);
		Packet result = getResponse();
		if(result.getPlayerVO() != null)
			return result.getPlayerVO();
		else
			return null;
	}//login()
	
	public boolean selectID(String id) {
		ClientPacketSender.instance.selectId(id);
		Packet result = getResponse();
		if(result.getMotion().equals("true")) 
			return true;
		else
			return false;
	}//selectID()
	
	public boolean selectNick(String nick) {
		ClientPacketSender.instance.selectNick(nick);
		Packet result = getResponse();
		if(result.getMotion().equals("true"))
			return true;
		else 
			return false;
	}//selectNick()
	
	public void playerSave(PlayerVO vo) {
		ClientPacketSender.instance.playerSave(vo);
	}//playerSave()
	
	public PlayerVO selectOnePlayerWithNo(int playerNo) {
		ClientPacketSender.instance.selectOnePlayerWithNo(playerNo);
		Packet result = getResponse();
		if(result.getPlayerVO() != null)
			return result.getPlayerVO();
		else
			return null;
	}//selectOnePlayerWithNo()
	
	public Packet getResponse() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			BufferedReader br = PlayerVO.myVO.getBrSocket();
			String packetStr = "";
			packetStr = br.readLine();
			logger.debug(packetStr);
			Packet packet = mapper.readValue(packetStr, Packet.class);
			
			return packet;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	} //getResponse
	
}
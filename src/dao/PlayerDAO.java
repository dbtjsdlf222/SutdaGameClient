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

	public String getServerIP() {
		String sql = "SELECT ip FROM player WHERE id = 'SERVER'";
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String ip = rs.getString(1);

			return ip;
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

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
	
	public PlayerVO selectOnePlayerWithNo(int playerNo) {
		
		String sql = "SELECT * FROM player WHERE no=?";
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, playerNo);
			rs = pstmt.executeQuery();
			rs.next();

			try {
				int no = rs.getInt(1);
				String rsID = rs.getString(2);
				String rsPW = rs.getString(3);
				String nickname = rs.getString(4);
				long money = rs.getLong(5);
				int win = rs.getInt(6);
				int lose = rs.getInt(7);
				boolean online = rs.getBoolean(8);
				int character = rs.getInt(10);
				String ip = null;
				try {
					ip = InetAddress.getLocalHost().getHostAddress();
				} catch (UnknownHostException e) {
					logger.error(e.getMessage(), e);
				}

				return new PlayerVO(no, rsID, rsPW, nickname, money, online, win, lose, online, character, ip);
			} catch (SQLException e) {
				return null;
			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		
		return null;
	}

	public int playerJoin(PlayerVO vo) throws ClassNotFoundException {
		ClientPacketSender.instance.join(vo);
		Packet result = getResponse();
		if(result.getMotion().equals("true")) 
			return 1;
		else 
			return 0;
	}
	
	public PlayerVO login(String id, String pw) {
		ClientPacketSender.instance.login(id, pw);
		Packet result = getResponse();
		if(result.getPlayerVO() != null)
			return result.getPlayerVO();
		else
			return null;
	}
	
	public boolean selectID(String id) {
		ClientPacketSender.instance.selectId(id);
		Packet result = getResponse();
		if(result.getMotion().equals("true")) 
			return true;
		else
			return false;
	}
	
	public boolean selectNick(String nick) {
		ClientPacketSender.instance.selectNick(nick);
		Packet result = getResponse();
		if(result.getMotion().equals("true"))
			return true;
		else 
			return false;
	}
	
	public void playerSave(PlayerVO vo) {
		ClientPacketSender.instance.playerSave(vo);
	}
	
	public 
	
	

//	public ArrayList<PlayerVO> listAll() {
//		ResultSet rs;
//		ArrayList<PlayerVO> list = new ArrayList<>();
//		String query = "SELECT * FROM player";
//		try {
//			pstmt = conn.prepareStatement(query);
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				String id = rs.getString("id");
//				String pw = rs.getString("password");
//				String nick = rs.getString("nickname");
//
//				list.add(new PlayerVO(id, pw, nick));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return list;
//	}

//	public boolean selectID(String id) {
//		ResultSet rs;
//		String query = "SELECT EXISTS (select * from player where id= ? ) AS SUCCESS";
//
//		try {
//			pstmt = conn.prepareStatement(query);
//			pstmt.setString(1, id);
//
//			rs = pstmt.executeQuery();
//			rs.next();
//			if (rs.getInt(1) == 1) {
//				return true;
//			} else
//				return false;
//		} catch (SQLException e) {
//			logger.error(e.getMessage(), e);
//		}
//
//		return true;
//	}
//
//	public boolean selectNick(String nick) {
//		ResultSet rs;
//		String query = "select EXISTS (select * from player where nickname= ? ) as success";
//
//		try {
//			pstmt = conn.prepareStatement(query);
//			pstmt.setString(1, nick);
//
//			rs = pstmt.executeQuery();
//			rs.next();
//			if (rs.getInt(1) == 1) {
//				return true;
//			} else
//				return false;
//		} catch (SQLException e) {
//			logger.error(e.getMessage(), e);
//		}
//
//		return true;
//	}
//
//	public PlayerVO login(String id, String pw) {
//		String sql = "SELECT * FROM player WHERE id=? AND password=?";
//		ResultSet rs = null;
//
//		try {
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, id);
//			pstmt.setString(2, pw);
//			rs = pstmt.executeQuery();
//			rs.next();
//
//			try {
//				int no = rs.getInt(1);
//				String rsID = rs.getString(2);
//				String rsPW = rs.getString(3);
//				String nickname = rs.getString(4);
//				long money = rs.getLong(5);
//				int win = rs.getInt(6);
//				int lose = rs.getInt(7);
//				boolean online = rs.getBoolean(8);
//				int character = rs.getInt(10);
//				String ip = null;
//				try {
//					ip = InetAddress.getLocalHost().getHostAddress();
//				} catch (UnknownHostException e) {
//					logger.error(e.getMessage(), e);
//				}
//
//				return new PlayerVO(no, rsID, rsPW, nickname, money, online, win, lose, online, character, ip);
//			} catch (SQLException e) {
//				return null;
//			}
//
//		} catch (
//
//		SQLException e) {
//			logger.error(e.getMessage(), e);
//		}
//		return null;
//	}
//
//	public void playerSave(PlayerVO vo) {
//		String query = "UPDATE player SET money = ?, win = ?, lose = ? WHERE no = ?";
//		try {
//			pstmt = conn.prepareStatement(query);
//			pstmt.setLong(1, vo.getMoney());
//			pstmt.setInt(2, vo.getWin());
//			pstmt.setInt(3, vo.getLose());
//			pstmt.setInt(4, vo.getNo());
//
//			pstmt.executeUpdate();
//		} catch (SQLException e) {
//			logger.error(e.getMessage(), e);
//		}
//	}
	
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
package dao;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connection.DBCon;

public class PlayerDAO {

	private Connection conn;

	public PlayerDAO() {
		try {
			conn = new DBCon().getMysqlConn();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public int playerJoin(PlayerVO vo) throws ClassNotFoundException {
		InetAddress local = null;
		try {
			local = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}

		String id = null;
		String pw = null;
		String nick = null;
		String ip = local.getHostAddress();
		int result = 0;


		id = vo.getID();
		pw = vo.getPW();
		nick = vo.getNICKNAME();
		
		String sql = "INSERT INTO player(id,password,nickname, ip) VALUES (?,?,?,?)";
		Connection conn = new DBCon().getMysqlConn();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, nick);
			pstmt.setString(4, ip);
			result = pstmt.executeUpdate();
		
		}
		catch (Exception e) {
			e.getMessage();
		}
		return result;
	}

	public ArrayList<PlayerVO> listAll() {
		ResultSet rs;
		ArrayList<PlayerVO> list = new ArrayList<>();
		String query = "SELECT * FROM player";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				String id = rs.getString("id");
				String pw = rs.getString("password");
				String nick = rs.getString("nickname");
				
				list.add(new PlayerVO(id, pw, nick));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean selectID(String id) {
		ResultSet rs;
		String query = "select EXISTS (select * from player where id= ? ) as success";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();
			rs.next();
			if(rs.getInt(1) == 1) {
				return true;
			}else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	public boolean selectNick(String nick) {
		ResultSet rs;
		String query = "select EXISTS (select * from player where nickname= ? ) as success";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, nick);

			rs = pstmt.executeQuery();
			rs.next();
			if(rs.getInt(1) == 1) {
				return true;
			}else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
}
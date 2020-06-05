package dao;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

import connection.DBCon;
import vo.PlayerVOsunil;

public class PlayerDAO {

	private Connection conn;
	private PreparedStatement pstmt; 

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
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, nick);
			pstmt.setString(4, ip);
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.getMessage();
		}
		return result;
	}

	public ArrayList<PlayerVO> listAll() {
		ResultSet rs;
		ArrayList<PlayerVO> list = new ArrayList<>();
		String query = "SELECT * FROM player";
		try {
			pstmt = conn.prepareStatement(query);
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
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();
			rs.next();
			if (rs.getInt(1) == 1) {
				return true;
			} else
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
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, nick);

			rs = pstmt.executeQuery();
			rs.next();
			if (rs.getInt(1) == 1) {
				return true;
			} else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return true;
	}

	public PlayerVOsunil login(String id, String pw) {
		String sql = "SELECT * FROM player WHERE id=? AND pw=?";
		ResultSet rs;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			
			int no = rs.getInt(1);
			String rsID = rs.getString(2);
			String rsPW = rs.getString(3);
			String nickname = rs.getString(4);
			int money = rs.getInt(5);
			int win = rs.getInt(6);
			int lose = rs.getInt(7);
			boolean online = rs.getBoolean(8);
			int character = rs.getInt(9);
			
			String update = "UPDATE player SET `online` = 'true' WHERE id=?";
			pstmt = conn.prepareStatement(update);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			
			return new PlayerVOsunil(no, rsID, rsPW, nickname, money, online, win, lose, online, character);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean selectPW(String pw) {
		ResultSet rs;
		String query = "select EXISTS (select * from player where password= ? ) as success";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, pw);

			rs = pstmt.executeQuery();
			rs.next();
			if (rs.getInt(1) == 1) {
				return true;
			} else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return true;
	}

}
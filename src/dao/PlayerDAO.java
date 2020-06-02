package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import connection.DBCon;

public class PlayerDAO {

	private Connection conn;

	public PlayerDAO() {
		conn = new DBCon().getOracleConn();
	}
	
	public String playerJoin(PlayerVO vo) throws ClassNotFoundException {
		String id = null;
		String pw = null;
		String nick = null;

		id = vo.getID();
		pw = vo.getPW();
		nick = vo.getNICKNAME();
		
		String sql = "INSERT INTO player(id,password,nickname) VALUES (?,?,?)";
		Connection conn = new DBCon().getMysqlConn();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, nick);
			pstmt.executeUpdate();
		}
		catch (Exception e) {
			e.getMessage();
		}
		return sql;
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
}
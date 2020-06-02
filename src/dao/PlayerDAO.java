package dao;

import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connection.DBCon;

public class PlayerDAO {

	private Connection conn;
	private Statement stmt;
	private ResultSet rs;

	public String playerJoin(PlayerVO vo) throws ClassNotFoundException {
		String id = null;
		String pw = null;
		String nic = null;

		id = vo.getID();
		pw = vo.getPW();
		nic = vo.getNICKNAME();
		
		String sql = "INSERT INTO Player(id,password,nickname) VALUES (?,?,?)";
		Connection conn = new DBCon().getMysqlConn();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, nic);
			pstmt.executeUpdate();
		}
		catch (Exception e) {
			e.getMessage();
		}
		return sql;
	}

	public ArrayList<PlayerVO> listAll() {
		ArrayList<PlayerVO> list = new ArrayList<>();
		connDB();
		String query = "SELECT * FROM Member";
		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				String id = rs.getString("id");
				String pw = rs.getString("pw");
				String nic = rs.getString("nic");

				for (List list : playerlist) {
					System.out.println(list);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
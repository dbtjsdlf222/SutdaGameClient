package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PlayerDAO {
	private Connection con;
	private Statement stmt;
	private ResultSet rs;

	public void conDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.con = DriverManager.getConnection(
					"jdbc:mysql://sunx.cafe24.com:3306/sunx?characterEncoding=UTF-8&serverTimezone=UTC", "sunx",
					"sun123@@");
			this.stmt = this.con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.stmt;
	}

	public void addPlayer(PlayerVO playerVO) {
		String ID = null;
		String PWD = null;
		String NICKNAME = null;

		ID = playerVO.getID();
		PWD = playerVO.getPW();
		NICKNAME = playerVO.getNICKNAME();

		conDB();
		String query = "INSERT INTO PLAYER VALUES('" + ID + "', '" + PWD + "', '" + NICKNAME + "')";
		try {
			stmt.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<PlayerVO> list() {
		ArrayList<PlayerVO> list = new ArrayList<>();
		conDB();
		String query = "SELCT * FROM PLAYER";
		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				String id = rs.getString("ID");
				String pwd = rs.getString("PWD");
				String nick = rs.getString("NICKNAME");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
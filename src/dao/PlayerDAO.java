package dao;

import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PlayerDAO {

	private Connection conn;
	private Statement stmt;
	private ResultSet rs;

	public void connDB() {
		String dburl = "jdbc:oracle:thin:@localhost:1521:xe";
		String dbid = "lee";
		String dbpw = "lee";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(dburl, dbid, dbpw);
			stmt = conn.createStatement();

		} catch (ClassNotFoundException e) {
			System.err.println("Class.forName에서 오타 발견 또는 빌드패스 문제");
		} catch (SQLException e) {
			System.err.println("url,id,pw 확인 요망");
		}
	}

	public String playerJoin(PlayerVO vo) {
		String id = null;
		String pw = null;
		String nic = null;

		id = vo.getID();
		pw = vo.getPW();
		nic = vo.getNICKNAME();

		connDB();
		String query = "INSERT INTO Player VALUES ('" + id + "', '" + pw + "', '" + nic + "')";
		try {
			stmt.executeUpdate(query);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
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
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PlayerDAO {
	private static final String driver = "oracle.jdbc.driver.OracleDriver";
	public final static String dburl = "jdbc:oracle:thin:@localhost:1521:xe";
	public final static String dbid = "lee";
	public final static String dbpw = "lee";

	private Connection conn;
	private Statement stmt;
	private ResultSet rs;

	public void connDB() {
		try {
			Class.forName(driver);
			System.out.println("Oracle 드라이버 로딩 성공");

			conn = DriverManager.getConnection(dburl, dbid, dbpw);
			System.out.println("Connection 객체 생성 성공");

			stmt = conn.createStatement();
			System.out.println("Statement 객체 생성 성공");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void playerJoin(PlayerVO vo) {
		String id = null;
		String pw = null;
		String nic = null;

		id = vo.getId();
		pw = vo.getPw();
		nic = vo.getNic();

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


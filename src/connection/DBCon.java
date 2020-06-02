package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBCon {
	private Connection conn = null;
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	
	public Statement getOracleStmt() {
		String driver ="oracle.jdbc.driver.OracleDriver";
		String dburl = "jdbc:oracle:thin:@localhost:1521:xe";
		String dbid = "lee";
		String dbpw = "lee";

		return new DBCon().dbconn(driver, dburl, dbid, dbpw);
	}
	
	public Statement getMysqlStmt() throws ClassNotFoundException {
		String driver = "com.mysql.cj.jdbc.Driver";
		String dburl = "jdbc:mysql://sunx.cafe24.com:3306/sunx?characterEncoding=UTF-8&serverTimezone=UTC";
		String dbid = "sunx";
		String dbpw = "sun123@@";
		
		return new DBCon().dbconn(driver, dburl, dbid, dbpw);
	}
	
	public Statement dbconn(String driver,String dburl, String dbid, String dbpw) {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(dburl, dbid, dbpw);
			stmt = conn.createStatement();
		} catch (ClassNotFoundException e) {
			System.err.println("Class.forName에서 오타 발견 또는 빌드패스 문제");
		} catch (SQLException e) {
			System.err.println("url,id,pw 확인 요망");
		} //try~catch
		return stmt;
	} //dbconn
	
}
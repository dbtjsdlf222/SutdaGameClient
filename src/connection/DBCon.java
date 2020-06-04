package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBCon {
	private Connection conn = null;
	
//	public Connection getOracleConn() {
//		String driver ="oracle.jdbc.driver.OracleDriver";
//		String dburl = "jdbc:oracle:thin:@localhost:1521:xe";
//		String dbid = "lee";
//		String dbpw = "lee";
//
//		return new DBCon().dbconn(driver, dburl, dbid, dbpw);
//	}
	
	public Connection getMysqlConn() throws ClassNotFoundException {
		String driver = "com.mysql.cj.jdbc.Driver";
		String dburl = "jdbc:mysql://sunx.cafe24.com:3306/sunx?characterEncoding=UTF-8&serverTimezone=UTC";
		String dbid = "sunx";
		String dbpw = "sun123@@";
		
		return new DBCon().dbconn(driver, dburl, dbid, dbpw);
	}
	
	public Connection dbconn(String driver,String dburl, String dbid, String dbpw) {
		try {
			conn = DriverManager.getConnection(dburl, dbid, dbpw);
		} 
		catch (SQLException e) { System.err.println("url,id,pw 확인 요망"); } //try~catch
		
		return conn;
	} //dbconn
	
} //class
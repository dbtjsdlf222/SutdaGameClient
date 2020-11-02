package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;

import client.ui.RoomScreen;

import org.slf4j.Logger;

public class DBCon {

	private static final Logger logger = LoggerFactory.getLogger(DBCon.class);
	
	private Connection conn = null;
	
//	public Connection getOracleConn() {
//		String driver ="oracle.jdbc.driver.OracleDriver";
//		String dburl = "jdbc:oracle:thin:@localhost:1521:xe";
//		String dbid = "lee";
//		String dbpw = "lee";
//
//		return new DBCon().dbconn(driver, dburl, dbid, dbpw);
//	}
	public static void main(String[] args) {
		try {
			new DBCon().getMysqlConn();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public Connection getMysqlConn() throws ClassNotFoundException {
		String driver = "com.mysql.jdbc.Driver";
		String dburl = "jdbc:mysql://sunx.cafe24.com:3306/sunx?characterEncoding=UTF-8&serverTimezone=UTC";
		String dbid = "sunx";
		String dbpw = "sun123@@";
		
		return new DBCon().dbconn(driver,dburl, dbid, dbpw);
	}
	
	public Connection dbconn(String driver, String dburl, String dbid, String dbpw) {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(dburl, dbid, dbpw);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return conn;
	} //dbconn
	
} //class
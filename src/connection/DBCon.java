package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBCon {

	private static final Logger logger = LogManager.getLogger();
	
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
		String driver = "com.mysql.jdbc.Driver";
		String dburl = "jdbc:mysql://sunx.cafe24.com:3306/sunx?characterEncoding=UTF-8&serverTimezone=UTC";
		String dbid = "sunx";
		String dbpw = "sun123@@";
		
		return new DBCon().dbconn(driver, dburl, dbid, dbpw);
	}
	
	public Connection dbconn(String driver,String dburl, String dbid, String dbpw) {
		try {
			Class.forName ("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dburl, dbid, dbpw);
		} 
		catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return conn;
	} //dbconn
	
} //class
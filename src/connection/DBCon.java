package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBCon {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement pstmt = null;
	private ResultSet resultSet = null;

	public Statement DBConnection() throws ClassNotFoundException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.connect = DriverManager.getConnection(
					"jdbc:mysql://sunx.cafe24.com:3306/sunx?characterEncoding=UTF-8&serverTimezone=UTC", "sunx",
					"sun123@@");
			this.statement = this.connect.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.statement;
	}
}
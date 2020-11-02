package dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import client.service.ClientPacketController;
import connection.DBCon;
import vo.Packet;
import vo.PlayerVO;

public class ServerDAO {
	
	private static final Logger logger = LogManager.getLogger();
	
	private Connection conn;
	private PreparedStatement pstmt;

	public ServerDAO() {
		try {
			conn = new DBCon().getMysqlConn();
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
	} //constructer();
	
	public PlayerVO selectOnePlayerWithNo(int playerNo) {
		
		String sql = "SELECT no,id,password,nickname,money,win,lose,admin,`character` FROM player WHERE no=?";
		ResultSet rs = null;

	try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, playerNo);
			rs = pstmt.executeQuery();
			rs.next();

			try {
				int no = rs.getInt(1);
				String rsID = rs.getString(2);
				String rsPW = rs.getString(3);
				String nickname = rs.getString(4);
				long money = rs.getLong(5);
				int win = rs.getInt(6);
				int lose = rs.getInt(7);
				boolean admin = rs.getBoolean(8);
				int character = rs.getInt(9);
				/*try {
					ip = InetAddress.getLocalHost().getHostAddress();
				} catch (UnknownHostException e) {
					logger.error(e.getMessage(), e);
				}*/

				return new PlayerVO(no, rsID, rsPW, nickname, money, admin, win, lose, character);
			} catch (SQLException e) {
				return null;
			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		
		return null;
	}
	
	public int extraMoney(PlayerVO vo) {
		String money = "1000";
		String sql = "UPDATE player SET  money = ? WHERE nickname = ?";
		int result = 0; 
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, money);
			pstmt.setString(2, vo.getNic());
			
			result = pstmt.executeUpdate();
			
			vo.setMoney(Integer.parseInt(money));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return result;
	}

	public int playerJoin(PlayerVO vo) throws ClassNotFoundException {
		InetAddress local = null;
		try {
			local = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			logger.error(e1.getMessage(), e1);
		}

		String id = null;
		String pw = null;
		String nick = null;
		String ip = local.getHostAddress();
		int character = 0;
		int result = 0;

		id = vo.getId();
		pw = vo.getPassword();
		nick = vo.getNic();
		character = vo.getCha();

		String sql = "INSERT INTO player(`id`, `password`, `nickname`, `character`) VALUES (?,?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, nick);
			pstmt.setInt(5, character);
			result = pstmt.executeUpdate();
			System.out.println(result);
			System.out.println(pstmt.executeUpdate());
		} catch (Exception e) {
			e.getMessage();
		}
		return result;
	}
	
	public void initMoneyChage() {
	      String query = "update player set money_charge = 5";
	      try {
	         pstmt = conn.prepareStatement(query);
	         pstmt.executeUpdate();
	      } catch (SQLException e) {
	         logger.error(e.getMessage(), e);
	      }
	   }
	   
	   public void useMoneyCharge(int no) {
	      String query = "UPDATE player SET money_charge = (select charge from(SELECT money_charge-1 as charge FROM player WHERE no =?) as charge) WHERE no = ?";
	      try {
	         pstmt = conn.prepareStatement(query);
	         pstmt.setInt(1, no);
	         pstmt.setInt(2, no);

	         pstmt.executeUpdate();
	      } catch (SQLException e) {
	         logger.error(e.getMessage(), e);
	      }
	   }
	   
	   public int moneyChargeCheck(int no) {
	      ResultSet rs;
	      String query = "SELECT money_charge FROM player WHERE no = ?";
	      int charge = 0;
	      try {
	         pstmt = conn.prepareStatement(query);
	         pstmt.setInt(1, no);
	         rs = pstmt.executeQuery();
	         rs.next();
	         charge=rs.getInt(1);
	      } catch (SQLException e) {
	         logger.error(e.getMessage(), e);
	      }
	      return charge;
	   }
	
	

//	public ArrayList<PlayerVO> listAll() {
//		ResultSet rs;
//		ArrayList<PlayerVO> list = new ArrayList<>();
//		String query = "SELECT * FROM player";
//		try {
//			pstmt = conn.prepareStatement(query);
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				String id = rs.getString("id");
//				String pw = rs.getString("password");
//				String nick = rs.getString("nickname");
//
//				list.add(new PlayerVO(id, pw, nick));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return list;
//	}

	public boolean selectID(String id) {
		ResultSet rs;
		String query = "SELECT EXISTS (select * from player where id= ? ) AS SUCCESS";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();
			rs.next();
			if (rs.getInt(1) == 1) {
				return true;
			} else
				return false;
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}

		return true;
	}

	public boolean selectNick(String nick) {
		ResultSet rs;
		String query = "select EXISTS (select * from player where nickname= ? ) as success";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, nick);

			rs = pstmt.executeQuery();
			rs.next();
			if (rs.getInt(1) == 1) {
				return true;
			} else
				return false;
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}

		return true;
	}

	public PlayerVO login(String id, String pw) {
		String sql = "SELECT no,id,password,nickname,money,win,lose,admin,`character` FROM player WHERE id=? AND password=?";
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			rs.next();

			try {
				int no = rs.getInt(1);
				String rsID = rs.getString(2);
				String rsPW = rs.getString(3);
				String nickname = rs.getString(4);
				long money = rs.getLong(5);
				int win = rs.getInt(6);
				int lose = rs.getInt(7);
				boolean admin = rs.getBoolean(8);
				int character = rs.getInt(9);
				String ip = null;
				try {
					ip = InetAddress.getLocalHost().getHostAddress();
				} catch (UnknownHostException e) {
					logger.error(e.getMessage(), e);
				}

				return new PlayerVO(no, rsID, rsPW, nickname, money, admin, win, lose, character);
			} catch (SQLException e) {
				return null;
			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public boolean selectPW(String pw) {
		ResultSet rs;
		String query = "select EXISTS (select * from player where password= ? ) as success";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, pw);

			rs = pstmt.executeQuery();
			rs.next();
			if (rs.getInt(1) == 1) {
				return true;
			} else
				return false;
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		return true;
	}

	public void playerSave(PlayerVO vo) {
		String query = "UPDATE player SET money = ?, win = ?, lose = ? WHERE no = ?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setLong(1, vo.getMoney());
			pstmt.setInt(2, vo.getWin());
			pstmt.setInt(3, vo.getLose());
			pstmt.setInt(4, vo.getNo());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
package vo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class PlayerVO {

	private static final Logger logger = LogManager.getLogger();

	@JsonIgnore
	public static PlayerVO myVO = new PlayerVO();

	private int no;
	private String id = null;
	private String password = null;
	private String nic = null;
	private long money = 0;
	private boolean admin;
	private int win = 0;
	private int lose = 0;
	private boolean online = false;
	private int cha;
	private boolean live = false;
	private float card1;
	private float card2;
	private int cardLevel;
	private String cardName;
	private int index;
	private String ip;
	private int roomNo; // 0이면 로비
	private long betMoney;	//room에서 건 돈 저장 (올인시 자신이 건돈만큼만 따기 위함)
	private boolean allIn = false;	//올인여부

	@JsonIgnore
	private Socket socket;
	@JsonIgnore
	private BufferedReader brSocket;
	@JsonIgnore
	private PrintWriter pwSocket;
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	
	public PlayerVO() {
	}

	public PlayerVO(String id, String pw) {
		this.id = id;
		this.password = pw;
	}
	
	public PlayerVO(int no, String id, String password, String nic, long money, boolean admin,int win, int lose, int cha) {
		this.no = no;
		this.id = id;
		this.password = password;
		this.nic = nic;
		this.money = money;
		this.admin = admin;
		this.win = win;
		this.lose = lose;
		this.cha = cha;
	}
	
	public PlayerVO(Socket socket, int no, String id, String password, String nic, long money, boolean admin, int win,
			int lose, boolean online, int cha, boolean live, float card1, float card2, int cardLevel, String cardName,
			BufferedReader brSocket, PrintWriter pwSocket) {
		this.socket = socket;
		this.no = no;
		this.id = id;
		this.password = password;
		this.nic = nic;
		this.money = money;
		this.admin = admin;
		this.win = win;
		this.lose = lose;
		this.online = online;
		this.cha = cha;
		this.live = live;
		this.card1 = card1;
		this.card2 = card2;
		this.cardLevel = cardLevel;
		this.cardName = cardName;
		this.brSocket = brSocket;
		this.pwSocket = pwSocket;
	}
	
	/**
	 *  배팅한 돈을 betMoney에 더한다
	 * @param money	 배팅할 돈
	 */
	public void pay(long money) {
		if(this.money - money < 0) {
			betMoney += this.money;
			this.money = 0;
		} else {
			this.money -= money;
		}
	} //pay();
	
	public void balance(long money) {
		this.money += money;
	}

	public void joinPlayer(String id, String pw, String nic) {
		this.id = id;
		this.password = pw;
		this.nic = nic;
	}

	public PlayerVO(String string, int i, int j) {
		this.nic = string;
		this.cha = i;
		this.money = j;
	}
	
	public void saveExceptPw(PlayerVO vo) {
		win = vo.getWin();
		lose = vo.getLose();
		money = vo.getMoney();
		betMoney = 0;
	}
	
	public void setSocketWithBrPw(Socket socket) {
		this.socket = socket;
		try {
			brSocket = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			pwSocket = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"), true);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void gameWin() {
		win++;
		resetVO();
	}

	public void gameLose() {
		lose++;
		resetVO();
	}
	
	public void resetVO() {
		betMoney = 0;
		allIn = false;
		live = true;
	}
	
	@Override
	public String toString() {
		return "PlayerVO [no=" + no + ", id=" + id + ", pw=" + password + ", nic=" + nic + ", money=" + money + ", live=" + live + ", card1="
				+ card1 + ", card2=" + card2 + ", cardLevel=" + cardLevel + ", cardName=" + cardName + ", index="
				+ index + ", roomNo=" + roomNo + "]";
	}
	
	public int   getRoomNo(){ return roomNo;}
	public int   getIndex() { return index; }
	public float getCard1() { return card1; }
	public float getCard2() { return card2; }
	public int 	 getNo()    { return this.no;}
	public int 	 getCha()   { return cha; }
	public int 	 getWin()   { return this.win;   }
	public int 	 getLose()  { return this.lose;  }
	public long  getMoney() { return this.money; }
	public long getBetMoney()  { return betMoney; }
	public int 	getCardLevel() { return cardLevel;  } 
	public boolean 	isAdmin()  { return this.admin; } 
	public boolean 	isLive()   { return live;  }
	public boolean 	isAllIn()  { return allIn; } 
	public boolean	isOnline() { return this.online; }
	public String 	getPassword() { return password; }
	public String 	getCardName() { return cardName; }
	public String	getId()  { return this.id; }
	public String 	getNic() { return this.nic; }
	public String 	getIp() 	 { return ip; }
	public Socket 	getSocket() { return socket; }
	
	public BufferedReader getBrSocket() { return brSocket; }
	public PrintWriter 	  getPwSocket() { return pwSocket; }
	
	public void setBetMoney(long betMoney) { this.betMoney = betMoney; }
	public void setAllIn(boolean allIn) {this.allIn = allIn;} 
	public void	setPassword(String password)   { this.password = password; }
	public void	setCardLevel(int cardLevel)    { this.cardLevel = cardLevel; }
	public void	setCardName(String cardName)   { this.cardName = cardName; }
	public void	setBrSocket(BufferedReader brSocket) { this.brSocket = brSocket; }
	public void	setPwSocket(PrintWriter pwSocket) 	 { this.pwSocket = pwSocket; }
	public void	setSocket(Socket socket)  { this.socket = socket; }
	public void	setLive(boolean live) 	  { this.live = live; }
	public void	setOnline(boolean online) { this.online = online; }
	public void	setNo(int no) 	{ this.no = no; }
	public void	setCha(int cha) { this.cha = cha; }
	public void	setId(String id)   { this.id = id; }
	public void	setNic(String nic) { this.nic = nic; }
	public void	setMoney(long money) 	{this.money = money; } 
	public void	winMoney(long money) 	{ this.money += money; }
	public void	setAdmin(boolean admin) { this.admin = admin; }
	public void	setWin(int win)   { this.win = win; }
	public void	setLose(int lose) { this.lose = lose; } 
	public void setRoomNo(int roomNo) { this.roomNo = roomNo; }
	public void setCard1(float card1) { this.card1 = card1; }
	public void setCard2(float card2) { this.card2 = card2; }
	public void setIp(String ip) 	{ this.ip = ip; }
	public void setIndex(int index) { this.index = index; }
	
}
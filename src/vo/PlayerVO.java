package vo;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class PlayerVO {

	private int no;
	private String id = null;
	private String password = null;
	private String nic = null;
	private int money = 0;
	private boolean admin;
	private int win = 0;
	private int lose = 0;
	private boolean online = false;
	private int cha;
	private boolean live = false;
	private float card1, card2;
	private int cardLevel;
	
	@JsonIgnore
	private Socket socket;
	@JsonIgnore
	private BufferedReader brSocket;
	@JsonIgnore
	private PrintWriter pwSocket;
	
	public PlayerVO() {
	}
	
	public PlayerVO(int no, String id, String password, String nic, int money, boolean admin, int win, int lose,
			boolean online, int cha) {
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
	}
	
	public PlayerVO(Socket socket, int no, String id, String password, String nic, int money, boolean admin,
			int win, int lose, boolean online, int cha, boolean live, float card1, float card2, int cardLevel,
			BufferedReader brSocket, PrintWriter pwSocket) {
		super();
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
		this.brSocket = brSocket;
		this.pwSocket = pwSocket;
	}

	public void setSocketWithBrPw(Socket socket) {
		this.socket = socket;
		try {
			brSocket = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
			pwSocket = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		return "PlayerVOsunil [socket=" + socket + ", no=" + no + ", id=" + id + ", password=" + password + ", nic="
				+ nic + ", money=" + money + ", admin=" + admin + ", win=" + win + ", lose=" + lose + ", online="
				+ online + ", cha=" + cha + ", live=" + live + ", card1=" + card1 + ", card2=" + card2 + ", cardLevel="
				+ cardLevel + ", brSocket=" + brSocket + ", pwSocket=" + pwSocket + "]";
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getCardLevel() {
		return cardLevel;
	}

	public void setCardLevel(int cardLevel) {
		this.cardLevel = cardLevel;
	}

	public BufferedReader getBrSocket() {
		return brSocket;
	}

	public void setBrSocket(BufferedReader brSocket) {
		this.brSocket = brSocket;
	}

	public PrintWriter getPwSocket() {
		return pwSocket;
	}

	public void setPwSocket(PrintWriter pwSocket) {
		this.pwSocket = pwSocket;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public float getCard1() {
		return card1;
	}

	public void setCard1(float card1) {
		this.card1 = card1;
	}

	public float getCard2() {
		return card2;
	}

	public void setCard2(float card2) {
		this.card2 = card2;
	}


	public int getNo() {
		return this.no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getCha() {
		return cha;
	}

	public void setCha(int cha) {
		this.cha = cha;
	}

	public String getID() {
		return this.id;
	}

	public void setID(String id) {
		this.id = id;
	}

	public String getNic() {
		return this.nic;
	}

	public void setNic(String nic) {
		this.nic = nic;
	}

	public int getMoney() {
		return this.money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public boolean isAdmin() {
		return this.admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public int getWin() {
		return this.win;
	}

	public void setWin(int win) {
		this.win = win;
	}

	public int getLose() {
		return this.lose;
	}

	public void setLose(int lose) {
		this.lose = lose;
	}

	public boolean isOnline() {
		return this.online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}
}
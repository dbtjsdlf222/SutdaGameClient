package vo;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class PlayerVO {
	public static ArrayList<PlayerVO> playerList = new ArrayList<>();

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
	private String cardName;
	private String location;
	private String ip;

	@JsonIgnore
	private Socket socket;
	@JsonIgnore
	private BufferedReader brSocket;
	@JsonIgnore
	private PrintWriter pwSocket;

	public PlayerVO() {
		playerList.add(this);
	}

	public PlayerVO(String id, String pw, String nic) {
		this.id = id;
		this.password = pw;
		this.nic = nic;
		playerList.add(this);
	}
	
	public void joinPlayer(String id, String pw, String nic) {
		this.id = id;
		this.password = pw;
		this.nic = nic;
	}


	public PlayerVO(int no, String id, String password, String nic, int money, boolean admin, int win, int lose,
			boolean online, int cha, String ip) {
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
		this.ip = ip;
		playerList.add(this);
	}

	public PlayerVO(Socket socket, int no, String id, String password, String nic, int money, boolean admin, int win,
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
		playerList.add(this);
	}

	// 같은 위치에 있는 사람들의 목록 리턴
	public ArrayList<PlayerVO> getLoctionList(String location) {
		ArrayList<PlayerVO> locPlayerList = new ArrayList<>();
		for (int i = 0; i < playerList.size(); i++) {
			if (playerList.get(i).getLocation().equals(location))
				locPlayerList.add(playerList.get(i));
		}
		return locPlayerList;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setSocketWithBrPw(Socket socket) {
		this.socket = socket;
		try {
			brSocket = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			pwSocket = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "PlayerVOsunil [socket=" + socket + ", no=" + no + ", id=" + id + ", password=" + password + ", nic="
				+ nic + ", money=" + money + ", admin=" + admin + ", win=" + win + ", lose=" + lose + ", online="
				+ online + ", cha=" + cha + ", live=" + live + ", card1=" + card1 + ", card2=" + card2 + ", cardLevel="
				+ cardLevel + ", cardName=" + cardName + ", brSocket=" + brSocket + ", pwSocket=" + pwSocket + "]";
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

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
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
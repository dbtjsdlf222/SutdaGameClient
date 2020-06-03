package vo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class UserVO extends Throwable {
	
	private int no;
	private String id;
	private String password;
	private String name;
	private int money;
	private boolean admin;
	private int win;
	private int lose;
	private boolean online;
	private BufferedReader br;
	private PrintWriter pw;
	private Socket socket;
	
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.socket = socket;
	}

	public UserVO(int no, String id, String pw, String name, int money, boolean admin, int win, int lose,
			boolean online) {
		this.no = no;
		this.id = id;
		this.password = pw;
		this.name = name;
		this.money = money;
		this.admin = admin;
		this.win = win;
		this.lose = lose;
		this.online = online;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public BufferedReader getBr() {
		return br;
	}

	public void setBr(BufferedReader br) {
		this.br = br;
	}

	public void setPw(PrintWriter pw) {
		this.pw = pw;
	}

	public int getNo() {
		return this.no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return this.password;
	}

	public void setPw(String pw) {
		this.password = pw;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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

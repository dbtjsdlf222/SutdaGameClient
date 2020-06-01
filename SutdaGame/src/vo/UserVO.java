package vo;

public class UserVO extends Throwable {
	private int no;
	private String id;
	private String pw;
	private String name;
	private int money;
	private boolean admin;
	private int win;
	private int lose;
	private boolean online;

	public UserVO(int no, String id, String pw, String name, int money, boolean admin, int win, int lose,
			boolean online) {
		this.no = no;
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.money = money;
		this.admin = admin;
		this.win = win;
		this.lose = lose;
		this.online = online;
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
		return this.pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
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

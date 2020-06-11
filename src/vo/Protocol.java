package vo;

public class Protocol {
	
	//Server
	public static final String FIRSTENTER = "0";	//첫 로비 입장
	public static final String MESSAGE = "1";		//메세지
	public static final String JOINPLAYER = "2";	//타 플레이어 입실
	public static final String EXITPLAYER = "3";	//타 플레이어 퇴실
	public static final String ENTERLOBBY = "4";		//로비입장
	public static final String MAKEROOM = "5";		//방만들기
	public static final String ENTERROOM = "10";	//방 입실		입실시 vo에 roomNo 수정
	public static final String EXITROOM = "11";		//방 퇴실		vo roomNo = 널
	public static final String EXITLOBBY = "13";		//방 퇴실
	public static final String LOGIN = "14";
	
	public static final String ENTEROTHERROOM = "9";  	//다른 사람이 룸에 입실
	public static final String ENTEROTHERLOBBY = "15";	//다른 사람이 로비 입실
	public static final String EXITOTHERROOM = "9";  	//다른 사람이 룸에 퇴실
	public static final String EXITOTHERLOBBY = "15";	//다른 사람이 로비 퇴실
	
	//Location
	public static final String LOBBY = "lobby";
	public static final String ROOM = "room";
	
	
	//Game
	public static final String GAMESTART = "gameStart";	//game 시작 상태 만들기
	public static final String Half = "half";
	public static final String Quater = "quater";
	public static final String Check = "checkt";
	public static final String Allin = "allIn";
	public static final String Call = "call";
	public static final String Die = "die";
	public static final String Pping = "pping";
	
}
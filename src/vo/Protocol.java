package vo;

public class Protocol {
	
	//Server
//	public static final String FIRSTENTER = "0";	//첫 로비 입장
	public static final String MESSAGE = "1";		//메세지
	public static final String MAKEROOM = "2";		//방만들기
	
	public static final String ENTERROOM = "3";	//방 입실		입실시 vo에 roomNo 수정
	public static final String EXITROOM = "4";		//방 퇴실		vo roomNo = 널
	public static final String ENTERLOBBY = "5";		//로비입장
	public static final String EXITLOBBY = "6";		//방 퇴실
	
	public static final String LOGIN = "7";
	
	public static final String ENTEROTHERROOM = "8";  	//다른 사람이 룸에 입실
	public static final String ENTEROTHERLOBBY = "9";	//다른 사람이 로비 입실
	public static final String EXITOTHERROOM = "10";  	//다른 사람이 룸에 퇴실
	public static final String EXITOTHERLOBBY = "11";	//다른 사람이 로비 퇴실
	
	
	//Game
	public static final String GAMESTART = "12";	//game 시작 상태 만들기
	public static final String DRAW = "13";	//game 시작 상태 만들기
	public static final String CHANGEMASTER = "21";	//선 변경
	
	
	public static final String Half = "14";
	public static final String Quater = "15";
	public static final String Check = "16";
	public static final String Allin = "17";
	public static final String Call = "18";
	public static final String Die = "19";
	public static final String Pping = "20";
	
}
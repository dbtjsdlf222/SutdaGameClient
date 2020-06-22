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
	public static final String RELOADLOBBYLIST = "22";	//로비가 변했을때 새로고침
	
	//Game
	public static final String GAMESTART = "12";	//game 시작 상태 만들기
	public static final String DRAW = "13";			//game 시작 상태 만들기
	public static final String CHANGEMASTER = "21";	//선 변경
	
	public static final String Half = "Half";
	public static final String Quater = "Quater";
	public static final String Check= "Check";
	public static final String Allin = "Allin";
	public static final String Call = "Call";
	public static final String Die = "Die";
	public static final String Pping = "Pping";
	public static final String Ddadang = "Ddadang";
	
	public static final String CARD = "23";
	public static final String OPENCARD = "25";
	public static final String SETMONEY = "26";
	public static final String SETBUTTON = "27";
	public static final String STARTPAY = "28";
	public static final String TURN = "29";
	public static final String OTHERBET = "30";
	public static final String BET = "31";
	public static final String GAMEOVER = "32";
	
	public static String getName(String str) {
		
		switch(str) {
			case "1": str = "MESSAGE"; break;
			case "2": str = "MAKEROOM"; break;
			case "3": str = "ENTERROOM"; break;
			case "4": str = "EXITROOM"; break;
			case "5": str = "ENTERLOBBY"; break;
			case "6": str = "EXITLOBBY"; break;
			case "7": str = "LOGIN"; break;
			case "8": str = "ENTEROTHERROOM"; break;
			case "9": str = "ENTEROTHERLOBBY"; break;
			case "10": str = "EXITOTHERROOM"; break;
			case "11": str = "EXITOTHERLOBBY"; break;
			case "12": str = "GAMESTART"; break;
			case "13": str = "DRAW"; break;
			case "21": str = "CHANGEMASTER"; break;
			case "14": str = "Half"; break;
			case "15": str = "Quater"; break;
			case "16": str = "Check"; break;
			case "17": str = "Allin"; break;
			case "18": str = "Call"; break;
			case "19": str = "Die"; break;
			case "20": str = "Pping"; break;
			case "22": str = "RELOADLOBBYLIST"; break;
			case "23": str = "CARD"; break;
			case "24": str = "DdaDang"; break;
			case "25": str = "OPENCARD"; break;
			case "26": str = "SETMONEY"; break;
			case "27": str = "SETBUTTON"; break;
			case "28": str = "STARTPAY"; break;
			case "29": str = "TURN"; break;
			case "30": str = "OTHERBET"; break;
			case "31": str = "BET"; break;
			case "32": str = "GAMEOVER"; break;
			
		}
		
		return str;
		
	}
	
}
package vo;

public class Protocol {
	
	//Server
	public static final String CONNECTSUCCESS= "0"; 			//서버 연결 성공
	public static final String MESSAGE = "1";					//메세지
	public static final String MAKEROOM = "2";					//방만들기
	
	public static final String ENTERROOM = "3";					//방 입실		입실시 vo에 roomNo 수정
	public static final String EXITROOM = "4";					//방 퇴실		vo roomNo = 널
	public static final String ENTERLOBBY = "5";				//로비입장
	public static final String EXITLOBBY = "6";					//방 퇴실
	
	public static final String LOGIN = "7";						//로그인
	public static final String JOIN = "8";						//회원가입
	public static final String SELECTID = "9";					//아이디 중복확인
	public static final String SELECTNICK= "10";				//닉네임 중복확인
	public static final String PLAYERSAVE = "11";				//플레이어 정보저장
	public static final String SELECTONEPLAYERWITHNO = "12";	//플레이어 정보조회
	
	
	public static final String ENTEROTHERROOM = "13";  			//다른 사람이 룸에 입실
	public static final String ENTEROTHERLOBBY = "14";			//다른 사람이 로비 입실
	public static final String EXITOTHERROOM = "15";  			//다른 사람이 룸에 퇴실
	public static final String EXITOTHERLOBBY = "16";			//다른 사람이 로비 퇴실
	public static final String RELOADLOBBYLIST = "17";			//로비가 변했을때 새로고침
	
	//Game
	public static final String GAMESTART = "18";				//game 시작 상태 만들기
	public static final String CHANGEMASTER = "19";				//선 변경
	
	public static final String CARD = "20";						//카드전송
	public static final String OPENCARD = "21";					//패 공개
	public static final String SETMONEY = "22";					//게임머니 설정
	public static final String SETBUTTON = "23";				//배팅 버튼 활성화
	public static final String STARTPAY = "24";					//시작금액 설정
	public static final String TURN = "25";						//턴
	public static final String OTHERBET = "26";					//다른플레이어 배팅
	public static final String BET = "27";						//자신이 배팅
	public static final String GAMEOVER = "28";					//게임종료
	public static final String DRAW = "29";						//동점
	public static final String SETSTARTBUTTON = "30"; 			//게임시작버튼 보이기
	public static final String RUNOUTMONEY = "31"; 				//게임머니 부족
	public static final String RELOADMYVO = "32"; 				//서버의 저장된 자신의 정보와 동기화 
	public static final String SENDOFF = "33";					//나가기 예약
	
	public static final String ONLINE = "34";					//로그인
	public static final String OFFLINE = "35";					//로그아웃
	

	public static final String Half = "Half";
	public static final String Quater = "Quater";
	public static final String Check= "Check";
	public static final String Allin = "Allin";
	public static final String Call = "Call";
	public static final String Die = "Die";
	public static final String Pping = "Pping";
	public static final String Ddadang = "Ddadang";
	
	public static String getName(String str) {
		
		switch(str) {
			case "0": str = "CONNECTSUCCESS"; break;
			case "1": str = "MESSAGE"; break;
			case "2": str = "MAKEROOM"; break;
			case "3": str = "ENTERROOM"; break;
			case "4": str = "EXITROOM"; break;
			case "5": str = "ENTERLOBBY"; break;
			case "6": str = "EXITLOBBY"; break;
			case "7": str = "LOGIN"; break;
			case "8": str = "JOIN"; break;
			case "9": str = "SELECTID"; break;
			case "10": str = "SELECTNICK"; break;
			case "11": str = "PLAYERSAVE"; break;
			case "12": str = "SELECTONEPLAYERWITHNO"; break;
			case "13": str = "ENTEROTHERROOM"; break;
			case "14": str = "ENTEROTHERLOBBY"; break;
			case "15": str = "EXITOTHERROOM"; break;
			case "16": str = "EXITOTHERLOBBY"; break;
			case "17": str = "RELOADLOBBYLIST"; break;
			case "18": str = "GAMESTART"; break;
			case "19": str = "CHANGEMASTER"; break;
			case "20": str = "CARD"; break;
			case "21": str = "OPENCARD"; break;
			case "22": str = "SETMONEY"; break;
			case "23": str = "SETBUTTON"; break;
			case "24": str = "STARTPAY"; break;
			case "25": str = "TURN"; break;
			case "26": str = "OTHERBET"; break;
			case "27": str = "BET"; break;
			case "28": str = "GAMEOVER"; break;
			case "29": str = "DRAW"; break;
			case "30": str = "SETSTARTBUTTON"; break;
			case "31": str = "RUNOUTMONEY"; break;
			case "32": str = "RELOADMYVO"; break;
			case "33": str = "SENDOFF"; break;
			case "34": str = "ONLINE"; break;
			case "35": str = "OFFLINE"; break;
			case "Half": str = "Half"; break;
			case "Quater": str = "Quater"; break;
			case "Check": str = "Check"; break;
			case "Allin": str = "Allin"; break;
			case "Call": str = "Call"; break;
			case "Die": str = "Die"; break;
			case "Pping": str = "Pping"; break;
			case "Ddadang": str = "Ddadang"; break;
			
			default  : str = "UnknownProtocol"; break;
		}
		return str;
	}
}
package vo;

public class Protocol {
   
   //Server
   public static final String CONNECT_SUCCESS= "0";         		//서버 연결 성공
   public static final String MESSAGE = "1";               		//메세지
   public static final String MAKE_ROOM = "2";               	//방만들기
   
   public static final String ENTER_ROOM = "3";              	//방 입실      입실시 vo에 roomNo 수정
   public static final String EXIT_ROOM = "4";            	   	//방 퇴실      vo roomNo = 널
   public static final String ENTER_LOBBY = "5";           		//로비입장
   public static final String EXIT_LOBBY = "6";               	//방 퇴실
   
   public static final String LOGIN = "7";                  	//로그인
   public static final String JOIN = "8";                  		//회원가입
   public static final String SELECT_ID = "9";              		//아이디 중복확인
   public static final String SELECT_NICK= "10";            		//닉네임 중복확인
   public static final String PLAYER_SAVE = "11";            	//플레이어 정보저장
   public static final String SELECT_ONE_PLAYER_WITH_NO = "12";   	//플레이어 정보조회
   
   
   public static final String ENTER_OTHER_ROOM = "13";           	//다른 사람이 룸에 입실
   public static final String ENTER_OTHER_LOBBY = "14";         	//다른 사람이 로비 입실
   public static final String EXIT_OTHER_ROOM = "15";           	//다른 사람이 룸에 퇴실
   public static final String EXIT_OTHER_LOBBY = "16";         	//다른 사람이 로비 퇴실
   public static final String RELOAD_LOBBY_LIST = "17";         	//로비가 변했을때 새로고침
   
   //Game
   public static final String GAME_START = "18";            		//game 시작 상태 만들기
   public static final String CHANGE_MASTER = "19";            	//선 변경
   
   public static final String CARD = "20";                  	//카드전송
   public static final String OPEN_CARD = "21";               	//패 공개
   public static final String SET_MONEY = "22";              	//게임머니 설정
   public static final String SET_BUTTON = "23";            		//배팅 버튼 활성화
   public static final String START_PAY = "24";               	//시작금액 설정
   public static final String TURN = "25";                  	//턴
   public static final String OTHER_BET = "26";               	//다른플레이어 배팅
   public static final String BET = "27";                  		//자신이 배팅
   public static final String GAME_OVER = "28";               	//게임종료
   public static final String DRAW = "29";                  	//동점
   public static final String SET_START_BUTTON = "30";          	//게임시작버튼 보이기
   public static final String RUN_OUT_MONEY = "31";             	//게임머니 부족
   public static final String RELOAD_MY_VO = "32";             	//서버의 저장된 자신의 정보와 동기화 
   public static final String SEND_OFF = "33";               	//나가기 예약
   
   public static final String ONLINE = "34";               		//로그인
   public static final String OFFLINE = "35";               	//로그아웃
   public static final String WHISPER= "36";              	 	//귓속말
   public static final String DO_INVITE= "37";               	//초대하기
   public static final String KICK= "38";                  		//추방
   public static final String PASSWORD= "39";                  	//비밀번호 체크
   public static final String GET_INVITE= "40";               	//초대받기
   public static final String SERVER_MESSAGE= "41";             //경고문 출력
   public static final String KICKROOM= "42";                	//추방당하면 로비로 이동
   public static final String TIME_OUT= "43";                	//시간초과시 다이
   public static final String SHOWNEEDMONEY= "44";              //게임중 돈이 얼마 드는지 출력
   public static final String EXTRAMONEY= "45";                	//돈이 부족하면 충전
   public static final String RELOAD_INFO_MONEY= "46";          //비상금서비스 받을시 다시 저장후 출력
   public static final String FIND = "47";						//유저 찾기

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
         case "36": str = "WHISPER"; break;
         case "37": str = "INVITE"; break;
         case "38": str = "KICK"; break;
         case "40": str = "GET_INVITE"; break;
         case "41": str = "SERVER_MESSAGE"; break;
         case "42": str = "KICKROOM"; break;
         case "43": str = "TIME_OUT"; break;
         case "44": str = "SHOWNEEDMONEY"; break;
         case "45": str = "EXTRAMONEY"; break;
         case "46": str = "RELOAD_INFO_MONEY"; break;
         case "47": str = "FIND"; break;
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
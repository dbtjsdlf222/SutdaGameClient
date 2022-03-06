# 2020 섯다게임

<strong>섯다</strong>는 화투로 할 수 있는 대표적인 카드 게임입니다. <br>
참가자들은 본인이 가진 단 2장의 카드 조합을 확인하고 금액을 베팅한 후, 이후 서로의 카드를 모두 공개하여 <br>
그 결과 가장 높은 2장의 카드 조합을 가진 참가자가 승자가 되어 베팅된 판돈을 모두 획득합니다. (포커의 화투 버전입니다)

<blockquote>게임 서버는 현재 AWS에서 Docker로 배포 중입니다.<br> 
  섯다 게임 클라이언트을 다운로드하기 위해선 <a href="http://sunx.cafe24.com/main">홈페이지</a>에 접속하신 후 다운로드 해주시길 바랍니다.
</blockquote>

<p>클라이언트 Test계정은 [id,pw] > [1,1]~[5,5] 입니다.</p>

<details open>
  <summary id="top">소개 순서</summary>
<ol>
  <li><a href="#developers">개발 구성</a></li>
  <li><a href="#login">로그인 및 회원가입</a></li>
  <li><a href="#lobby">게임 로비 화면</a></li>
  <li><a href="#makeRoom">방 만들기</a></li>
  <li><a href="#diagram">심플 다이어그램</a></li>
</ol>
 </details open>
<hr>

<h2 id="developers">개발 구성</h2>

<table>
  <tr>
    <td>개발언어</td>
    <td>Java 1.8</td>
  </tr>
  <tr>
    <td>빌드도구</td>
    <td>Maven</td>
  </tr>
  <tr>
    <td>UI</td>
    <td>Java Swing</td>
  </tr>
  <tr>
    <td>DB</td>
    <td>MySQL</td>
  </tr>
  <tr>
    <td>기여도</td>
    <td>
      웹 서버(100%)<br>
      웹 프론트 (40%)<br>
      게임 서버 (80%)<br>
      게임 클라이언트 (30%)
    </td>
  </tr>
</table>
  
<h2 id="login">로그인 화면</h2>
<img src="https://user-images.githubusercontent.com/34783191/107025166-5e7b1700-67ec-11eb-8bd7-d62e784176d6.png" alt="로그인 화면"/>
<blockquote>접속 중인 아이디로 로그인시 "접속중인 아이디"라는 안내 메세지가 나옵니다.</blockquote>

<h2 id="회원가입">회원가입 화면</h2>
<blockquote>
  <ul>
    <li>실시간으로 서버에 요청을 하여 유효성을 체크합니다.</li>
    <li>비밀번호는 bcrypt로 암호화하여 저장합니다.</li>
  <ul>
  </blockquote>
<img src="https://user-images.githubusercontent.com/34783191/107060793-bd578500-681a-11eb-9896-de5896195c38.png" alt="회원가입 화면"/>
<blockquote>캐릭터 선택 후 회원가입 완료</blockquote>
<img src="https://user-images.githubusercontent.com/34783191/107060240-1377f880-681a-11eb-9a6a-6fb24814f846.png" alt="캐릭터 선택창"/>
<br><br>
<h2 id="lobby">게임 로비 화면</h2>
<img src="https://user-images.githubusercontent.com/34783191/107061822-f47a6600-681b-11eb-9c38-f8e43a0818c1.png" alt="게임 로비 화면"/>
<blockquote>로비에 변화가 있을시 실시간으로 반영합니다.</blockquote>
    
<img src="https://user-images.githubusercontent.com/34783191/107120024-72974500-68ce-11eb-9825-70ba01c0e8e1.png" alt="초대 화면"/>
<blockquote>
  초대를 받을시 초대장이 10초 동안 노출 됩니다. <br>
  초대시 상대가 시작 금액보다 더 많이 보유하고 있어야 가능합니다.
</blockquote>

<br><br>
<h2 id="makeRoom">방만들기</h2>
<img src="https://user-images.githubusercontent.com/34783191/107118521-ee8c8f80-68c4-11eb-8041-e03d04d022cb.png" alt="방만들기 화면">
<blockquote>자신이 가진 한도 내에서 시작 금액 설정 가능합니다.</blockquote>
<br><br>
<h2 id="lobby">게임 화면</h2>
<img src="https://user-images.githubusercontent.com/34783191/107121278-e3416000-68d4-11eb-9824-fb180c1d465f.png" alt="게임 화면"/>
<blockquote>
  <p>게임 진행 순서</P>
  <ol>
    <li>방장이 게임을 시작 합니다(2명 이상일 때만 가능)</li>
    <li>1번 카드를 받습니다</li>
    <li>배팅을 합니다(프로필 옆에 어떤 배팅을 했는지 보입니다)</li>
    <li>2번 카드를 받습니다</li>
    <li>배팅을 합니다</li>
    <li>패를 공개하며 승자가 결정 됩니다</li>
  </ol>
  <p>승자는 방장 권한을 가지게 됩니다</p>
  <p>파산할 경우 1000만원이 지급 됩니다 (하루 제한 5회)</p>
  <p>게임중 나가기 버튼을 클릭시 나가기 예약이 되며 게임이 끝난 후 퇴장이 됩니다</p>
</blockquote>

<h3 id="diagram">다이어그램</h3>
<img src="https://user-images.githubusercontent.com/34783191/107120302-2ea53f80-68d0-11eb-8411-d028ea646a57.png" alt="다이어그램">



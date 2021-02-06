# 섯다게임

<strong>섯다</strong>는 화투로 할 수 있는 대표적인 카드 게임입니다. <br>
참가자들은 본인이 가진 단 2장의 카드 조합을 확인하고 금액을 베팅한 후, 이후 서로의 카드를 모두 공개하여 그 결과 가장 높은 2장의 카드 조합을 가진 참가자가 승자가 되어 베팅된 판돈을 모두 획득합니다. (포커의 화투 버전입니다)

<blockquote>게임 서버는 현재 아마존 웹 서비스 VPC에서 배포 중입니다.<br> 섯다 게임 클라이언트만 <a href="https://drive.google.com/file/d/1pkjSCJg5_KFpaW67MfsgLPa24rtpq7X4/view?usp=sharing">다운로드</a>하신 후 실행시 바로 서버에 접속 하실수 있습니다.</blockquote>

<h2 id="top">소개 순서</h2>
<ol>
  <li><a href="#login">로그인 및 회원가입</a></li>
  <li><a href="#lobby">게임 로비 화면</a></li>
  <li><a href="#makeRoom"></a>방 만들기</li>
  <li><a href="#diagram">다이어그램</a></li>
</ol>
<hr>

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
<blockquote></blockquote>
    
<img src="https://user-images.githubusercontent.com/34783191/107120024-72974500-68ce-11eb-9825-70ba01c0e8e1.png" alt="초대 화면"/>
<blockquote>
  초대를 받을시 초대장이 10초 동안 노출 됩니다. <br>
  초대시 상대가 시작 금액보다 더 많이 보유하고 있어야 가능합니다.
</blockquote>

<br><br>
<h2 id="makeroom">방만들기</h2>
<img src="https://user-images.githubusercontent.com/34783191/107118521-ee8c8f80-68c4-11eb-8041-e03d04d022cb.png" alt="방만들기 화면">
<blockquote>자신이 가진 한도 내에서 시작 금액 설정 가능합니다.</blockquote>
<br><br>
<h2 id="lobby">게임 화면</h2>
<img src="https://user-images.githubusercontent.com/34783191/107118974-cce0d780-68c7-11eb-8a12-7bd226dd5262.png" alt="게임 화면"/>
<blockquote>
  <p>게임 진행 순서</P>
  <ol>
    <li>1번 카드를 받습니다</li>
    <li>배팅을 합니다</li>
    <li>2번 카드를 받습니다</li>
    <li>배팅을 합니다</li>
    <li>패를 공개하며 승자가 결정 됩니다</li>
  </ol>
</blockquote>

<h3>채팅 기능</h3>
<blockquote>
  <ul>
    <li>욕설 필터링</li>
    <li>최대 입력 가능 글자 제한</li>
      <li>사용 가능 명령어</li>
      <ul>
        <li>도움말(/help)</li>
        <li>귓속말(/w|/귓말)</li>
        <li>[방]게임방 초대(상대는 로비에 있어야함)</li>
        <li>[방]강퇴(방장만 작동)</li>
      </ul>
  <ul>
</blockquote>

<h3 id="diagram">다이어그램</h3>
<img src="https://user-images.githubusercontent.com/34783191/107119085-9d7e9a80-68c8-11eb-8634-2eb15a78d14b.png" alt="다이어그램">


<h2 id="lobby">게임 화면</h2>


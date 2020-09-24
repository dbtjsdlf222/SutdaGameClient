package client.service.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;

import client.service.ClientPacketSender;
import client.service.ClientReceiver;
import client.service.login.LoginResultHandler.Focus;
import dao.PlayerDAO;
import util.Packing;
import vo.Packet;
import vo.PlayerVO;
import vo.Protocol;

public class LoginService {

	private LoginResultHandler resultHandler;

	private PlayerDAO playerDAO = new PlayerDAO();

	public LoginService(LoginResultHandler resultHandler) {
		this.resultHandler = resultHandler;
	} //Login();

	public PlayerDAO getPlayerDAO() { return playerDAO; }

	public void login(String id, String password) throws IOException {

		if(id == null || id.trim().isEmpty()) {
			resultHandler.loginFailure(Focus.ID, "아이디를 입력해 주세요.");
			return;
		}
		if(password == null || password.trim().isEmpty()) {
			resultHandler.loginFailure(Focus.PASSWORD, "비밀번호를 입력해 주세요.");
			return;
		}

		String packetStr = "";
		PlayerVO player = null;
		ObjectMapper mapper = new ObjectMapper();
		BufferedReader br = new BufferedReader(new InputStreamReader(PlayerVO.myVO.getSocket().getInputStream(), "UTF-8"));
		try  {
			ClientPacketSender.instance.login(id, password);	//서버에 로그인 정보 요청
			packetStr = br.readLine();	//결과 받음

			Packet packet = mapper.readValue(packetStr, Packet.class);
			player = packet.getPlayerVO();

			if(packet.getAction().equals(Protocol.ONLINE)) {
				resultHandler.loginFailure(Focus.PASSWORD, "접속중인 아이디 입니다.");
				return;
			}

			if(player == null) {
				resultHandler.loginFailure(Focus.PASSWORD, "없는 아이디거나 비밀번호가 틀립니다.");
				return;
			}


			
			player.setSocketWithBrPw(PlayerVO.myVO.getSocket());
			PlayerVO.myVO = player;

			new Thread(new ClientReceiver(player.getSocket())).start();
			resultHandler.loginSuccess(player);
		}catch (Exception e) {
			e.printStackTrace();
		}
		//try~catch
	} //login();
	
} //class Login();
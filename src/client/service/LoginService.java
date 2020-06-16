package client.service;

import java.io.IOException;
import java.net.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;

import client.listener.ClientReceiver;
import client.service.LoginResultHandler.Focus;
import client.ui.MainScreen;
import dao.PlayerDAO;
import util.Packing;
import vo.Packet;
import vo.PlayerVO;
import vo.Protocol;

public class LoginService {
	
	private PlayerDAO playerDAO = new PlayerDAO();
	
	private LoginResultHandler resultHandler;
	
	public LoginService(LoginResultHandler resultHandler) {
		this.resultHandler = resultHandler;
	} //Login();
	
	
	@SuppressWarnings("static-access")
	public void login(String id, String password) throws IOException {
		
		if(id == null || id.trim().isEmpty()) {
			resultHandler.loginFailure(Focus.ID, "아이디를 입력해 주세요.");
			return;
		}
		if(password == null || password.trim().isEmpty()) {
			resultHandler.loginFailure(Focus.PASSWORD, "비밀번호를 입력해 주세요.");
			return;
		}
		
		PlayerVO player = playerDAO.login(id, password);
		
		
		
		if(player == null) {
			resultHandler.loginFailure(Focus.PASSWORD, "없는 아이디거나 비밀번호가 틀립니다.");
			return;
		}
		if(player.isOnline()) {
			resultHandler.loginFailure(Focus.ID, "다른 PC에서 접속중인 아이디 입니다.");
			return;
		}
		
		player.setSocketWithBrPw(new Socket(playerDAO.getServerIP(), 4888));
		PlayerVO.myVO = player;
		new Thread(new ClientReceiver(player.getSocket())).start();
		ClientPacketSender.instance.setClientPacketSender(player);
		resultHandler.loginSuccess(player);
		
	} //login();
	
	public PlayerDAO getPlayerDAO() { return playerDAO; }
	
} //class Login();
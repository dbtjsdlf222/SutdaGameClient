package client;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.ui.LoginFrame;
import client.ui.ShowErrorPane;
import vo.PlayerVO;

public class RunClient {
	
	private static final Logger logger = LoggerFactory.getLogger(RunClient.class);
	
	public static final String SERVERS_IP = "3.80.54.122";
//	public static final String SERVERS_IP = "localhost";
	private static int port = 4886;
	
	public static void main(String[] args) {
		
		Socket socket = null;
		
		try {
			socket = new Socket(SERVERS_IP, port);
		} catch(ConnectException e) {
			new ShowErrorPane("서버가 점검중입니다.");
			return;
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		logger.info("서버\"" + socket.getRemoteSocketAddress() + "\"에 접속 성공");
		PlayerVO.myVO.setSocketWithBrPw(socket);
		
		try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } // OS 호환
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); }
			catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) { logger.error(e1.getMessage());}
		}
		
		new LoginFrame();
		
	} // main();
	
} // class RunClient;
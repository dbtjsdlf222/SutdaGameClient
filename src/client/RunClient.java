package client;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import client.ui.LoginFrame;
import vo.PlayerVO;

public class RunClient {
	
	private static final Logger logger = LogManager.getLogger();
	
	public static final String[] SERVERS_IP = { "localhost", "192.168.0.69", "192.168.56.1" };
	
	public static final String SERVERIP = "192.168.0.69";	//현호 IP
//	public static final String SERVERIP = "192.168.56.1";	//선일 IP
	
	public static void main(String[] args) {
		
//		try {
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // OS 호환
//			Socket socket = new Socket(SERVERIP,4888);
//			logger.info("서버 접속 성공");
//			PlayerVO.myVO.setSocketWithBrPw(socket);
//		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException
//				| UnsupportedLookAndFeelException e) {
//			try {
//				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
//			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
//					| UnsupportedLookAndFeelException e1) {
//				logger.error(e1.getMessage(), e1);
//			}
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		EventQueue.invokeLater(() -> new LoginFrame()); // 쓰레드 충돌 방지
		
		for(String serverIp : SERVERS_IP) {
			
			new Thread(() -> {
				
				logger.info("\"" + serverIp + "\"에 연결 시도");
				
				try {
					init(new Socket(serverIp, 4888));
				}
				catch (UnknownHostException e) { e.printStackTrace(); }
				catch (IOException e) { if(initialized) logger.debug("[" + serverIp + "] " + e.getMessage()); }
				
			}).start();
			
		}
		
	} // main();
	
	private static boolean initialized = false;
	private static synchronized void init(Socket socket) {
		
		if(initialized)
			return;
		initialized = true;
		
		logger.info("서버 접속 성공");
		PlayerVO.myVO.setSocketWithBrPw(socket);
		
		try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } // OS 호환
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); }
			catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) { logger.error(e1, e1); }
		}
		
		EventQueue.invokeLater(() -> new LoginFrame()); // 쓰레드 충돌 방지
		
	} //init();
	
} // class RunClient;

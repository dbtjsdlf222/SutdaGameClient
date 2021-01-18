package client;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.Socket;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.ui.LoginFrame;
import vo.PlayerVO;

public class RunClient {
	
	private static final Logger logger = LoggerFactory.getLogger(RunClient.class);
	
//	public static final String SERVERS_IP = "3.80.54.122";
	public static final String SERVERS_IP = "localhost";
	private static int port = 4886;
	
	public static void main(String[] args) {
		
		logger.info("\"" + SERVERS_IP + "\"에 연결 시도");
		
		Socket socket = null;
		
		try {
			socket = new Socket(SERVERS_IP, port);
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
		
		EventQueue.invokeLater(() -> new LoginFrame()); // 쓰레드 충돌 방지
		
//		for(String serverIp : SERVERS_IP) {
//			new Thread(() -> {
//				logger.info("\"" + serverIp + "\"에 연결 시도");
//				try {
//					init(new Socket(serverIp, port));
//				}
//				catch (UnknownHostException e) { e.printStackTrace(); }
//				catch (IOException e) { if(!initialized) logger.info("[" + serverIp + "] " + e.getMessage()); }
//			}).start();
//		}
		
	} // main();
	
//	private static boolean initialized = false;
//	private static synchronized void init(Socket socket) throws SocketException {
//		
//		if(initialized)
//			return;
//		initialized = true;
//		
//		logger.info("서버\"" + socket.getRemoteSocketAddress() + "\"에 접속 성공");
//		PlayerVO.myVO.setSocketWithBrPw(socket);
//		
//		try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } // OS 호환
//		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
//			try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); }
//			catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) { logger.error(e1.getMessage());}
//		}
//		
//		EventQueue.invokeLater(() -> new LoginFrame()); // 쓰레드 충돌 방지
//		
//	} //init();
} // class RunClient;
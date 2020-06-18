package server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.PlayerDAO;
public class RunServer {
	
	private static final Logger logger = LogManager.getLogger();
	
	private int port = 4888;
	
	public static void main(String[] args) {
		new RunServer().run();
	}
	
	public void run() {
		logger.info("서버 실행");
		ExecutorService pool = Executors.newFixedThreadPool(2); // 최대 스레드가 2개인 스레드풀 생성
		PlayerDAO dao = new PlayerDAO();
		
		try(ServerSocket serverSocket = new ServerSocket(port)) {
			
			dao.setServerIP();
			while (true) {
				Socket socket = serverSocket.accept(); // 접속한 소켓 받는다
				
//				new Thread (new ServerReceiver(socket)).start(); // 사용자에게서 패킷 받기 시작
				
				pool.submit(new ServerReceiver(socket));
				
			} //while
		} catch(BindException e) {
			logger.error(e.getMessage(), e);
		} catch (UnknownHostException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			pool.shutdown();
		}
		
	} // run();
	
} // Accept();

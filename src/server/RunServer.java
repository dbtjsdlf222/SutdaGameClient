package server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.ServerDAO;

public class RunServer {

	private static final Logger logger = LogManager.getLogger();
	public static final int MAXPLAYER = 10;
	public static final int MAXROOM = MAXPLAYER;
	
	private int port = 4886;

	public static void main(String[] args) {
		new RunServer().run();
	}

	public void run() {
		logger.info("서버 실행");

		// 쓰레드풀 생성		
		ExecutorService pool = Executors.newFixedThreadPool(MAXPLAYER);
		
		
//		Timer timer = new Timer();
//		TimerTask task = new TimerTask() {
//			
//			@Override
//			public void run() {
//				new ServerDAO().initMoneyChage();
//				System.out.println("비상금 서비스 초기화 실행");
//			}
//		};
//		timer.schedule(task, 0, 24*60*60*1000);
		
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			while (true) {
				Socket socket = serverSocket.accept(); // 접속한 소켓 받는다
				Runnable r = new ServerReceiver(socket);
				pool.execute(r);
			} // while

		} catch (BindException e) {
			logger.error("서버가 이미 실행중입니다");
			e.printStackTrace();
		} catch (UnknownHostException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			pool.shutdown();
		}

		
	} // run();

} // Accept();
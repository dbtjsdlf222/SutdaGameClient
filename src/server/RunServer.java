package server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dao.PlayerDAO;
public class RunServer {
	
	private int port = 4888;
	
	public static void main(String[] args) {
		new RunServer().run();
	}
	
	public void run() {
		System.out.println("서버 실행");
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
			System.out.println("서버가 이미 작동 중 입니다.");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pool.shutdown();
		}
		
	} // run();
	
} // Accept();

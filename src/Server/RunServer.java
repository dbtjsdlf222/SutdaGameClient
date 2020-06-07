package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import client.Login;
import client.ReceiveServerPacket;

public class RunServer {
	
	public static void main(String[] args) {
		new RunServer().run();
	}

	public void run() {
		ExecutorService executor = Executors.newFixedThreadPool(2); // 최대 스레드가 2개인 스레드풀 생성
		
		try {
			ServerSocket serverSocket = new ServerSocket(4888);

			while (true) {
				Socket socket = serverSocket.accept(); // 접속한 소켓 받는다
				
				new Thread (new ReceiveServerPacket(socket)).start(); // 사용자에게서 패킷 받기 시작
				
//				Runnable run = new Runnable() {
//					
//					@Override
//					public void run() {
//						System.out.println(Thread.currentThread().getName());
//					}
//				};
//				executor.submit(run);
			} //while 
		} catch (UnknownHostException e) {
			System.err.println("서버 접속 실패");
		} catch (IOException e) {
			System.err.println("서버가 이미 가동중입니다");
		} finally {
			executor.shutdown();
		}
	} // run()
} // Accept()
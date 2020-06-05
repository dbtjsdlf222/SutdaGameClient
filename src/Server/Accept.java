package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import client.ReceiveServerPacket;

public class Accept extends Thread {
	private RoomOperator roomOperator;

	public static void main(String[] args) {
		new Thread(new Accept()).start();
		
	}

	@Override
	public void run() {
		try {
			ServerSocket serverSocket = new ServerSocket(4888);

			while (true) {
				Socket socket = serverSocket.accept(); // 접속한 소켓 받는다
				new Thread(new ReceiveServerPacket(socket)).start(); // 사용자에게서 패킷 받기 시작
			}
		} catch (UnknownHostException e) {
			System.err.println("서버 접속 실패");
		} catch (IOException e) {
			e.printStackTrace();
		}
	} // run()
} // Accept()
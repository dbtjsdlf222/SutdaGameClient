package Server;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Accept extends Thread {
	private RoomOperator roomOperator;
	
	public void run() {
		
		String ip = "192.168.55.246";
		try {
			Socket socket = new Socket(ip, 4999);
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			Remote remote = new Remote();
			
			while (true) {
				String order = br.readLine();
				ObjectMapper mapper = new ObjectMapper();
			}
		} catch (UnknownHostException e) {
			System.err.println("서버 접속 실패");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
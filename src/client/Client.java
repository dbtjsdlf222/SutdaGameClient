package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;

import vo.Packet;

public class Client {
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("192.168.0.19", 4888);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			ObjectMapper mapper = new ObjectMapper();
			Scanner sc = new Scanner(System.in);

			while (true) {
				String msg = sc.nextLine();	
				Packet pac = new Packet("message", msg);	
				String m = mapper.writeValueAsString(pac);
				pw.println(m);
				pw.flush();
			}
			
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

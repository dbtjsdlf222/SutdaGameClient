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
	private Socket socket;
	
	public Client(Socket socket) {
		this.socket = socket;
	}
	
}
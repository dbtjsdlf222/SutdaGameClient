package client;

import java.awt.EventQueue;

import client.ui.LoginFrame;

public class RunClient {
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(() -> new LoginFrame());
		
	} //main();
	
} //class RunClient;

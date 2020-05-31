package main;

public class Main {
	
	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 720;
	
	public static void main(String[] args) {
//		Login lo = new Login();
//		lo.dispose();
		Thread th = new Thread(new Accept());
		th.start();
//		MainScreen main = new MainScreen();
	}
}

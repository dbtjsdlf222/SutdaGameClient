package client;

public class RunClient {
	public static String ip = "192.168.0.19";
	
	public static void main(String[] args) {
		try {
			new Login().login();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
	}
}
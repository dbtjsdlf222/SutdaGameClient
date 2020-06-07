package client;

public class RunClient {

	public static void main(String[] args) {
		try {
			new Login().login();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
		}
	}

}
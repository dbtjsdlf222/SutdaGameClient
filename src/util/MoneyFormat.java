package util;

public class MoneyFormat {
	
	public synchronized static String format(long money) {
		String returnMoney = "";
		String strMoney = Long.toString(money);
			
		for (int i = 0; i < 3; i++) {
			if(strMoney.length() > 4) {
				
			}
		} //for
		
		return returnMoney;
	} //format
	
	public static String format(String money) {
		
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(format(123456789101112L));
	}
	
}
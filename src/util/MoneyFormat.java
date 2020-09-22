package util;

import java.text.DecimalFormat;

public class MoneyFormat {
	
	public synchronized static String format(long money) {
		System.out.println("formar "+money);
        DecimalFormat d = new DecimalFormat("#,####");
       
        String[] unit = new String[]{"만","억","조","경"};
        String[] str = d.format(money).split(",");
        String result = "";
        int cnt = 0;
        for(int i=str.length;i>0;i--) {
            if(Integer.parseInt(str[i-1]) != 0) {
                result = String.valueOf(Integer.parseInt(str[i-1])) + unit[cnt] + result;
            }
            cnt++;
        }
        System.out.println("result "+result);
        return result; 
    }
	
	public synchronized static String format(String money) {
		return format(Long.parseLong(money));
	}
	
	public synchronized static Long reformat(String money) {
		System.out.println("reformat "+money);
		String[] unit = new String[]{"만","억","조","경"};
		String[] sp = money.split("");
		long maxUnit=0;
		long adad=0;
		long total=0;
		for (int i = 3; i >= 0; i--) {
			if(money.indexOf(unit[i]) != -1) {
				maxUnit=i;
				adad=maxUnit+1;
				break;
			}
		} //for
		String temp = "";
		for (int i = 0; i < money.length(); i++) {
			if(!(sp[i].equals(unit[3])||sp[i].equals(unit[2])||sp[i].equals(unit[1])||sp[i].equals(unit[0]))) {
				temp = temp + sp[i];
			} else {
					adad-=1;
					if(temp!="") {
						if(adad==3) {
							total+=(Long.parseLong(temp)*10000000000000000L);		
						} else if(adad==2) {
							total+=(Long.parseLong(temp)*1000000000000L);
						} else if(adad==1){
							total+=(Long.parseLong(temp)*100000000L);
						} else {
							total+=(Long.parseLong(temp)*10000L);
						}
					}
					
				temp="";
			} //else
		}
		System.out.println("total" +total);
		return total;
	} //reformat();
} //MoneyFormat
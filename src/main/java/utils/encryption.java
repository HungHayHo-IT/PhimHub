package utils;

import java.security.MessageDigest;
import java.util.Base64;

public class encryption {
	public static String toSHA1(String str) {
		String saltString = "asjrlkmcoewj@tjle;oxqskjhdjksjf1jurVn";
		
		String resultString = null;
		
		str = str + saltString;
		
		try {
			byte[] dataBytes = str.getBytes("UTF-8"); //chuyển hóa thành chuỗi byte
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
			resultString = Base64.getEncoder().encodeToString(messageDigest.digest(dataBytes)); 
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return resultString;
	}
}

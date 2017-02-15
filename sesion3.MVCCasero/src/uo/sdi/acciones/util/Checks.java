package uo.sdi.acciones.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class Checks {

	private static final String PASSWORD_PATTERN =
            "(^(?=.*[0-9])(?=.*[a-zA-zñÑ]).{8,})";
	public static boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}
	
	public static boolean isValidPassword(String password){
		 Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
		 Matcher matcher = pattern.matcher(password);
		 return matcher.matches();
	}
	public static boolean isSamePassword(String password, String passwordRepeat){
		 return password.compareTo(passwordRepeat)==0;
	}
	

}

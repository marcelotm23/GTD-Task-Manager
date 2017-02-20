package uo.sdi.acciones.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import uo.sdi.business.exception.BusinessException;

public class Checks {

	private static final String PASSWORD_PATTERN = 
			"(^(?=.*[0-9])(?=.*[a-zA-zñÑ]).{8,})";

	public static boolean isValidEmailAddress(String email)
			throws BusinessException {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
			throw new BusinessException(
					"ERROR: El email no tiene un formato válido.");
		}
		return result;
	}

	public static boolean isValidPassword(String password)
			throws BusinessException {
		Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
		Matcher matcher = pattern.matcher(password);
		boolean result = matcher.matches();
		if (!result) {
			throw new BusinessException(
					"ERROR: La constraseña debe tener al menos 8 carácteres, "
							+ "contener letras y números.");
		}
		return result;
	}

	public static boolean isSamePassword(String password, String passwordRepeat)
			throws BusinessException {
		boolean result = password.compareTo(passwordRepeat) == 0;
		if (!result) {
			throw new BusinessException("ERROR: Las constraseñas no coinciden.");
		}
		return result;
	}

}

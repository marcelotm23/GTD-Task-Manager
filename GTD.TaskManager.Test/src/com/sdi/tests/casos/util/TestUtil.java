package com.sdi.tests.casos.util;

import java.util.Random;


import net.sourceforge.jwebunit.junit.WebTester;

public class TestUtil {

	private static final String PASSWORD="nuevoUsuario123";
	
	public static String creaNombreUsuarioRandom(){
		char[] letras = "abcdefghijklmnñopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 4; i++) {
		    char c = letras[random.nextInt(letras.length)];
		    sb.append(c);
		}
		return sb.toString();
		}
	public static void crearCuentaYLogin(WebTester user) {
		user.beginAt("/registrarse");
		String nombreUsuario="nuevoUsuario"+creaNombreUsuarioRandom();
		user.setTextField("nombreUsuario", nombreUsuario);
		user.setTextField("email", "email@email.com");
		user.setTextField("contrasena", PASSWORD);
		user.setTextField("contrasenaAgain", PASSWORD);
		user.submit();
		doLogin(user, nombreUsuario);
	}
	private static void doLogin(WebTester user, String nombreUsuario){
    	user.setTextField("nombreUsuario", nombreUsuario);
    	user.setTextField("password", PASSWORD);
    	user.submit(); 
	}
}

package com.sdi.tests.casos.anonimo;

import java.util.Random;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.Before;
import org.junit.Test;

public class CrearNuevaCuenta {

	private WebTester nuevoUser;
	
	@Before
    public void prepare() {
    	nuevoUser=new WebTester();
    	nuevoUser.setBaseUrl("http://localhost:8280/UO237104");
    }
	
	@Test
	public void basicTest() {
		nuevoUser.beginAt("/");
        nuevoUser.assertTitleEquals("TaskManager - Inicie sesión");
        nuevoUser.assertTextPresent("Inicie sesión");
        nuevoUser.assertTextPresent("Su identificador de usuario");
        nuevoUser.assertTextPresent("Su contraseña");
        nuevoUser.assertTextPresent("¿No tienes cuenta?. Crea una");
        nuevoUser.assertTextPresent("Crear cuenta");
       
    }
	@Test
	public void testClickCrearCuenta() {
		nuevoUser.beginAt("/");
		nuevoUser.assertLinkPresent("crearCuenta_link_id");
		nuevoUser.clickLink("crearCuenta_link_id");
		nuevoUser.assertTitleEquals("TaskManager - Crear nueva cuenta de usuario");
		nuevoUser.assertFormPresent("crearCuenta_form");
		nuevoUser.assertTextPresent("Crear nueva cuenta de usuario");
        nuevoUser.assertTextPresent("Identificador de usuario:");
        nuevoUser.assertTextPresent("Email:");
        nuevoUser.assertTextPresent("Contraseña:");
        nuevoUser.assertTextPresent("Repita la contraseña:");
        nuevoUser.assertLinkPresent("atras_link_id");
	}
	@Test
	public void testCrearCuentaExito() {
		nuevoUser.beginAt("/registrarse");
		nuevoUser.setTextField("nombreUsuario", "nuevoUsuario"+creaNombreUsuarioRandom());
		nuevoUser.setTextField("email", "email@email.com");
		nuevoUser.setTextField("contrasena", "nuevoUsuario123");
		nuevoUser.setTextField("contrasenaAgain", "nuevoUsuario123");
		nuevoUser.submit();
		nuevoUser.assertTitleEquals("TaskManager - Inicie sesión");
		nuevoUser.assertTextPresent("Se ha registrado correctamente. Puede "
				+ "proceder a logearse.");
	}
	public String creaNombreUsuarioRandom(){
		char[] letras = "abcdefghijklmnñopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 4; i++) {
		    char c = letras[random.nextInt(letras.length)];
		    sb.append(c);
		}
		return sb.toString();
		}
	@Test
	public void testCrearCuentaFracaso() {
		nuevoUser.beginAt("/registrarse");
		
		//Email no valido
		nuevoUser.setTextField("nombreUsuario", "nuevoUsuario");
		nuevoUser.setTextField("email", "n");
		nuevoUser.submit();
		nuevoUser.assertTextPresent("ERROR: El email no tiene un formato válido.");
		//Constraseña con formato no válido
		nuevoUser.setTextField("email", "email@email.com");
		nuevoUser.setTextField("contrasena", "n");
		nuevoUser.setTextField("contrasenaAgain", "nuevoUsuario123");
		nuevoUser.submit();
		nuevoUser.assertTextPresent("ERROR: La constraseña debe tener al menos "
				+ "8 carácteres, contener letras y números.");
		//Constraseña con formato no válido
		nuevoUser.setTextField("email", "email@email.com");
		nuevoUser.setTextField("contrasena", "nuevUsuario123");
		nuevoUser.setTextField("contrasenaAgain", "nuevoUsuario123");
		nuevoUser.submit();
		nuevoUser.assertTextPresent("ERROR: Las constraseñas no coinciden.");
		//Nombre de usuario no válido
		nuevoUser.setTextField("nombreUsuario", "n");
		nuevoUser.setTextField("email", "email@email.com");
		nuevoUser.setTextField("contrasena", "nuevoUsuario123");
		nuevoUser.setTextField("contrasenaAgain", "nuevoUsuario123");
		nuevoUser.submit();
		nuevoUser.assertTextPresent("The login must be at least 3 chars long");
		//Nombre de usuario existente
		nuevoUser.setTextField("nombreUsuario", "marcelo");
		nuevoUser.setTextField("email", "marcelo@marcelo.com");
		nuevoUser.setTextField("contrasena", "marcelo123");
		nuevoUser.setTextField("contrasenaAgain", "marcelo123");
		nuevoUser.submit();
		nuevoUser.assertTextPresent("The login is already used");
	}

}

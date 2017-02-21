package com.sdi.tests.casos.usuario;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.Before;
import org.junit.Test;

import com.sdi.tests.casos.util.TestUtil;

public class IniciarSesionTest {

	 private WebTester user;
	 private String[] credenciales;

	@Before
    public void prepare() {
    	user=new WebTester();
    	user.setBaseUrl("http://localhost:8280/UO237104");
    	credenciales=TestUtil.crearCuenta(user);
    }
	@Test
    public void testIniciarSesionConExito() {
    	user.beginAt("/");
    	user.assertFormPresent("validarse_form_name");
    	user.setTextField("nombreUsuario", credenciales[0]);
    	user.setTextField("password", credenciales[1]);
    	user.submit();
    	user.assertTitleEquals("TaskManager - Página principal del usuario");
    	user.assertTextPresent("Página principal");
    	user.assertTextInElement("login", credenciales[0]);
    	user.assertTextInElement("status", "ENABLED");
    	user.assertTextPresent("Iniciaste sesión el");
    }

    
    @Test
    public void testIniciarSesionSinExito() {    
    	user.beginAt("/");
    	user.assertTitleEquals("TaskManager - Inicie sesión");
    	//Solo nombre de usuario
    	user.setTextField("nombreUsuario", "noExiste");
    	user.submit();
    	user.assertTextPresent("El usuario [noExiste] no está registrado o la "
    			+ "constraseña no es la correcta");
    	//Solo contraseña
    	user.setTextField("password", "noExiste123");
    	user.submit();
    	user.assertTextPresent("El usuario [] no está registrado o la "
    			+ "constraseña no es la correcta");
    	//Ambos mal
    	user.setTextField("nombreUsuario", "noExiste");
    	user.setTextField("password", "noExiste123");
    	user.submit();
    	user.assertTextPresent("El usuario [noExiste] no está registrado o la "
    			+ "constraseña no es la correcta");
    	//Usuario bien, pero contraseña mal
    	user.setTextField("nombreUsuario", credenciales[0]);
    	user.setTextField("password", "noExiste123");
    	user.submit();
    	user.assertTextPresent("El usuario ["+credenciales[0]+"] no está registrado o la "
    			+ "constraseña no es la correcta");
    }

}

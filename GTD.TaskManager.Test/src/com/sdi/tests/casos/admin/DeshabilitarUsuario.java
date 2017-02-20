package com.sdi.tests.casos.admin;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.Before;
import org.junit.Test;

public class DeshabilitarUsuario {

	private WebTester nuevoUser;
	private WebTester admin;
	
	@Before
    public void prepare() {
    	nuevoUser=new WebTester();
    	admin = new WebTester();
    	nuevoUser.setBaseUrl("http://localhost:8280/UO237104");
    	admin.setBaseUrl("http://localhost:8280/UO237104");
    }
	
	@Test
	public void testDeshabilitarUsuario() {
		
		nuevoUser.beginAt("/");
        nuevoUser.assertTitleEquals("TaskManager - Inicie sesión");
        nuevoUser.assertTextPresent("Inicie sesión");
        nuevoUser.assertTextPresent("Su identificador de usuario");
        nuevoUser.assertTextPresent("Su contraseña");
        nuevoUser.assertTextPresent("¿No tienes cuenta?. Crea una");
        nuevoUser.assertTextPresent("Crear cuenta");
        nuevoUser.clickLink("crearCuenta_link_id");
		nuevoUser.assertTitleEquals("TaskManager - Crear nueva cuenta de usuario");
		nuevoUser.setTextField("nombreUsuario", "pruebaDeshabilitar");
		nuevoUser.setTextField("email", "email@pruebaDeshabilitar.com");
		nuevoUser.setTextField("contrasena", "pruebaDeshabilitar123");
		nuevoUser.setTextField("contrasenaAgain", "pruebaDeshabilitar123");
		nuevoUser.submit();
		nuevoUser.assertTextPresent("Se ha registrado correctamente. Puede proceder a logearse.");
		
		admin.beginAt("/");
		admin.setTextField("nombreUsuario", "admin");
		admin.setTextField("password", "admin123");
		admin.submit();
		admin.assertTitleEquals("TaskManager - Página principal del usuario");
		admin.assertLinkPresent("listarUsuarios_link_id");
		admin.clickLink("listarUsuarios_link_id");
		admin.assertTitleEquals("TaskManager - Listado de usuarios");
		admin.assertTextPresent("Si selecciona el checkbox de eliminar, "
				+ "se borrará el usuario correspondiente así como todas "
				+ "sus categorias y tareas");
		admin.assertTextPresent("pruebaDeshabilitar");
		admin.assertTextPresent("email@pruebaDeshabilitar.com");
		admin.assertTextPresent("ENABLED");
		admin.assertCheckboxPresent("cb_pruebaDeshabilitar");
		admin.assertCheckboxSelected("cb_pruebaDeshabilitar");
		admin.uncheckCheckbox("cb_pruebaDeshabilitar");
		admin.setExpectedJavaScriptConfirm("¿Estás seguro de confirmar los "
				+ "cambios?. En caso de eliminar usuarios esta acción no se "
				+ "puede deshacer.", true);
		admin.submit();
		admin.submit("confirmEstado", "Volver");
		admin.assertTextPresent("pruebaDeshabilitar");
		admin.assertTextPresent("email@pruebaDeshabilitar.com");
		admin.assertTextPresent("DISABLED");
		admin.assertCheckboxNotSelected("cb_pruebaDeshabilitar");
	}

}

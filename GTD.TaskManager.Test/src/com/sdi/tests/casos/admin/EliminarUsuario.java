package com.sdi.tests.casos.admin;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.Before;
import org.junit.Test;

public class EliminarUsuario {

	private WebTester nuevoUser;
	private WebTester admin;

	@Before
	public void prepare() {
		nuevoUser = new WebTester();
		admin = new WebTester();
		nuevoUser.setBaseUrl("http://localhost:8280/UO237104");
		admin.setBaseUrl("http://localhost:8280/UO237104");
	}

	@Test
	public void testEliminarUsuario() {
		
		nuevoUser.beginAt("/");
        nuevoUser.assertTitleEquals("TaskManager - Inicie sesión");
        nuevoUser.assertTextPresent("Inicie sesión");
        nuevoUser.assertTextPresent("Su identificador de usuario");
        nuevoUser.assertTextPresent("Su contraseña");
        nuevoUser.assertTextPresent("¿No tienes cuenta?. Crea una");
        nuevoUser.assertTextPresent("Crear cuenta");
        nuevoUser.clickLink("crearCuenta_link_id");
		nuevoUser.assertTitleEquals("TaskManager - Crear nueva cuenta de usuario");
		nuevoUser.setTextField("nombreUsuario", "pruebaEliminar");
		nuevoUser.setTextField("email", "email@pruebaEliminar.com");
		nuevoUser.setTextField("contrasena", "pruebaEliminar123");
		nuevoUser.setTextField("contrasenaAgain", "pruebaEliminar123");
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
		// Eliminar usuario
		admin.assertTextPresent("pruebaEliminar");
		admin.assertTextPresent("email@pruebaEliminar.com");
		admin.assertTextPresent("ENABLED");
		admin.assertCheckboxPresent("eliminar_pruebaEliminar");
		admin.assertCheckboxNotSelected("eliminar_pruebaEliminar");
		admin.checkCheckbox("eliminar_pruebaEliminar");
		admin.setExpectedJavaScriptConfirm("¿Estás seguro de confirmar los "
				+ "cambios?. En caso de eliminar usuarios esta acción no se "
				+ "puede deshacer.", true);
		admin.submit();
		admin.submit("confirmEstado", "Volver");
		admin.assertTextNotPresent("pruebaEliminar");
		admin.assertTextNotPresent("email@pruebaEliminar.com");
		admin.assertCheckboxNotPresent("cb_pruebaEliminar");
		admin.assertCheckboxNotPresent("eliminar_pruebaEliminar");
	}

}

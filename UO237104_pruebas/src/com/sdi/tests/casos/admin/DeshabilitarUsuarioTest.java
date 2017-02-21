package com.sdi.tests.casos.admin;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.Before;
import org.junit.Test;

import com.sdi.tests.casos.util.TestUtil;

public class DeshabilitarUsuarioTest {

	private WebTester nuevoUser;
	private WebTester admin;
	private String[] credencialesUser;
	
	@Before
    public void prepare() {
    	nuevoUser=new WebTester();
    	admin = new WebTester();
    	nuevoUser.setBaseUrl("http://localhost:8280/UO237104");
    	admin.setBaseUrl("http://localhost:8280/UO237104");
    	credencialesUser=TestUtil.crearCuenta(nuevoUser);
    }
	
	@Test
	public void testDeshabilitarUsuario() {
		
		admin.beginAt("/");
		admin.setTextField("nombreUsuario", "administrador1");
		admin.setTextField("password", "administrador1");
		admin.submit();
		admin.assertTitleEquals("TaskManager - Página principal del usuario");
		admin.assertLinkPresent("listarUsuarios_link_id");
		admin.clickLink("listarUsuarios_link_id");
		admin.assertTitleEquals("TaskManager - Listado de usuarios");
		admin.assertTextPresent("Si selecciona el checkbox de eliminar, "
				+ "se borrará el usuario correspondiente así como todas "
				+ "sus categorias y tareas");
		admin.assertTextPresent(credencialesUser[0]);
		admin.assertTextPresent("ENABLED");
		admin.assertCheckboxPresent("cb_"+credencialesUser[0]);
		admin.assertCheckboxSelected("cb_"+credencialesUser[0]);
		admin.uncheckCheckbox("cb_"+credencialesUser[0]);
		admin.setExpectedJavaScriptConfirm("¿Estás seguro de confirmar los "
				+ "cambios?. En caso de eliminar usuarios esta acción no se "
				+ "puede deshacer.", true);
		admin.submit();
		admin.submit("confirmEstado", "Volver");
		admin.assertTextPresent(credencialesUser[0]);
		admin.assertTextPresent("DISABLED");
		admin.assertCheckboxNotSelected("cb_"+credencialesUser[0]);
	}

}

package com.sdi.tests.casos.usuario;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.Before;
import org.junit.Test;

import com.sdi.tests.casos.util.TestUtil;

public class EditarTareasTest {

	
	private WebTester user;
	@Before
    public void prepare() {
    	user=new WebTester();
    	user.setBaseUrl("http://localhost:8280/UO237104");
    	//Crear nueva cuenta
    	TestUtil.crearCuentaYLogin(user);
    	//Clickar mostrar tareas
    	TestUtil.clickMostrarTareas(user);
    }
	@Test
	public void generalTest() {
		user.assertTitleEquals("TaskManager - Pseudolistas");
		user.assertTextPresent("GTD Manager");
		user.assertLinkPresent("inbox_link");
		user.assertLinkPresent("today_link");
		//Añadir la tarea
		user.setTextField("nombreTarea", "Esto es una tarea para inbox");
		user.submit();
		user.assertTextPresent("La tarea ha sido creada con éxito.");
		user.submit();
		user.assertTitleEquals("TaskManager - Pseudolistas");
		//Click editar
		user.assertLinkPresent("editarTarea_link");
		user.clickLink("editarTarea_link");
		user.assertTextPresent("Editar tarea");
		user.assertTextPresent("Título:");
		user.assertTextPresent("Comentarios:");
		user.assertTextPresent("Fecha planeada:");
		user.assertTextPresent("Categoría:");
		//Cambiar datos exito
		user.setTextField("title", "Cambio la tarea");
		user.setTextField("comments", "Comentario");
		user.setTextField("planned", "20/11/2017");
		user.submit();
		user.assertTitleEquals("TaskManager - Pseudolistas");
		user.assertTextPresent("Cambio la tarea");
		//Cambiar fracaso
		user.assertLinkPresent("editarTarea_link");
		user.clickLink("editarTarea_link");
		user.setTextField("planned", "dffsd");
		user.submit();
		user.assertTextPresent("ERROR: La tarea no se ha modificado "
				+ "correctamente, por el motivo [El formato de la fecha debe "
				+ "ser dd/MM/yyyy]");
	}
	

}

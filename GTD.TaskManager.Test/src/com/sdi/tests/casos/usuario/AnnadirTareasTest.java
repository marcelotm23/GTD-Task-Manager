package com.sdi.tests.casos.usuario;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.Before;
import org.junit.Test;

import com.sdi.tests.casos.util.TestUtil;

public class AnnadirTareasTest {

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
		user.assertLinkPresent("week_link");
		//Añadir tarea a inbox
		user.assertTextPresent("Añadir tarea");
		user.assertFormPresent("añadirTarea_form");
		user.setTextField("nombreTarea", "Esto es una tarea para inbox");
		user.submit();
		user.assertTextPresent("La tarea ha sido creada con éxito.");
		user.submit();
		//Añadir tarea a today
		user.clickLink("today_link");
		user.assertTextPresent("Añadir tarea");
		user.assertFormPresent("añadirTarea_form");
		user.setTextField("nombreTarea", "Esto es una tarea para today");
		user.submit();
		user.assertTextPresent("La tarea ha sido creada con éxito.");
		user.submit();
		//Añadir tarea vacía inbox
		user.assertTextPresent("Añadir tarea");
		user.assertFormPresent("añadirTarea_form");
		user.setTextField("nombreTarea", "");
		user.submit();
		user.assertTextPresent("Ha ocurrido un error en la creación de la tarea,"
				+ " inténtelo de nuevo. No es válido un nombre vacío para la tarea.");
		user.submit();
		//Añadir tarea vacía a today
		user.clickLink("today_link");
		user.assertTextPresent("Añadir tarea");
		user.assertFormPresent("añadirTarea_form");
		user.setTextField("nombreTarea", "");
		user.submit();
		user.assertTextPresent("Ha ocurrido un error en la creación de la tarea,"
				+ " inténtelo de nuevo. No es válido un nombre vacío para la tarea.");

		user.submit();
		
	}

}

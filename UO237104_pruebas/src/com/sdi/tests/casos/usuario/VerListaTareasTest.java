package com.sdi.tests.casos.usuario;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.Before;
import org.junit.Test;

import com.sdi.tests.casos.util.TestUtil;

public class VerListaTareasTest {

	private WebTester user;
	@Before
    public void prepare() {
    	user=new WebTester();
    	user.setBaseUrl("http://localhost:8280/UO237104");
    	//Crear nueva cuenta
    	TestUtil.crearCuentaYLogin(user);
    }
	@Test
	public void generalTest() {
		user.assertTitleEquals("TaskManager - Página principal del usuario");
		user.assertLinkPresent("filtrarTareas_link_id");
		user.clickLink("filtrarTareas_link_id");
		
		user.assertTitleEquals("TaskManager - Pseudolistas");
		user.assertTextPresent("GTD Manager");
		user.assertLinkPresent("inbox_link");
		user.assertLinkPresent("today_link");
		user.assertLinkPresent("week_link");
		inboxTest();
		todayTest();
		weekTest();
	}
	
	private void inboxTest() {
		user.assertLinkPresent("inbox_link");
		user.clickLink("inbox_link");
		user.assertTextPresent("No se han encontrado tareas");
		user.assertTextPresent("Filtros");
		user.assertFormPresent("filtros_form");
		user.assertCheckboxPresent("cb_mostrarFinalizadas");
		user.assertTextPresent("Añadir tarea");
		user.assertFormPresent("añadirTarea_form");
	}
	
	private void todayTest() {
		user.assertLinkPresent("today_link");
		user.clickLink("today_link");
		user.assertTextPresent("No se han encontrado tareas");
		user.assertFormNotPresent("filtros_form");
		user.assertCheckboxNotPresent("cb_mostrarFinalizadas");
		user.assertTextPresent("Añadir tarea");
		user.assertFormPresent("añadirTarea_form");
	}
	
	private void weekTest() {
		user.assertLinkPresent("week_link");
		user.clickLink("week_link");
		user.assertTextPresent("No se han encontrado tareas");
		user.assertTextNotPresent("Filtros");
		user.assertFormNotPresent("filtros_form");
		user.assertCheckboxNotPresent("cb_mostrarFinalizadas");
		user.assertTextPresent("Añadir tarea");
		user.assertFormPresent("añadirTarea_form");
	}

}

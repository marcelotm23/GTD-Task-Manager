package com.sdi.tests.casos;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.sdi.tests.casos.admin.DeshabilitarUsuarioTest;
import com.sdi.tests.casos.admin.EliminarUsuarioTest;
import com.sdi.tests.casos.anonimo.CrearNuevaCuentaTest;
import com.sdi.tests.casos.usuario.AnnadirTareasTest;
import com.sdi.tests.casos.usuario.EditarTareasTest;
import com.sdi.tests.casos.usuario.IniciarSesionTest;
import com.sdi.tests.casos.usuario.VerListaTareasTest;

@RunWith(Suite.class)
@SuiteClasses({ DeshabilitarUsuarioTest.class, EliminarUsuarioTest.class,
	CrearNuevaCuentaTest.class, AnnadirTareasTest.class, EditarTareasTest.class,
	IniciarSesionTest.class, VerListaTareasTest.class})
public class AllTests {

}

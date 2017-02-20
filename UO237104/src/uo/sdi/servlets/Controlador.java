package uo.sdi.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import alb.util.log.Log;
import uo.sdi.acciones.*;
import uo.sdi.dto.User;
import uo.sdi.persistence.PersistenceException;

public class Controlador extends javax.servlet.http.HttpServlet {

	private static final long serialVersionUID = 1L;
	private Map<String, Map<String, Accion>> mapaDeAcciones;
	// <rol, <opcion, objeto Accion>>
	private Map<String, Map<String, Map<String, String>>> mapaDeNavegacion;

	// <rol, <opcion, <resultado, JSP>>>

	public void init() throws ServletException {
		crearMapaAcciones();
		crearMapaDeNavegacion();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String accionNavegadorUsuario, resultado, jspSiguiente;
		Accion objetoAccion;
		String rolAntes, rolDespues;

		try {
			accionNavegadorUsuario = request.getServletPath().replace("/", "");

			// Obtener el string que hay a la derecha de la última /

			rolAntes = obtenerRolDeSesion(request);

			objetoAccion = buscarObjetoAccionParaAccionNavegador(rolAntes,
					accionNavegadorUsuario);

			request.removeAttribute("mensajeParaElUsuario");

			resultado = objetoAccion.execute(request, response);

			rolDespues = obtenerRolDeSesion(request);

			jspSiguiente = buscarJSPEnMapaNavegacionSegun(rolDespues,
					accionNavegadorUsuario, resultado);

			request.setAttribute("jspSiguiente", jspSiguiente);

		} catch (PersistenceException e) {

			request.getSession().invalidate();

			Log.error("Se ha producido alguna excepción relacionada con la "
					+ "persistencia [%s]", e.getMessage());
			request.setAttribute("mensajeParaElUsuario",
					"Error irrecuperable: contacte con el responsable de "
							+ "la aplicación");
			jspSiguiente = "/login.jsp";

		} catch (Exception e) {

			request.getSession().invalidate();

			Log.error("Se ha producido alguna excepción no manejada [%s]",
					e.getMessage());
			request.setAttribute("mensajeParaElUsuario",
					"Error irrecuperable: contacte con el responsable de "
							+ "la aplicación");
			jspSiguiente = "/login.jsp";
		}

		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(jspSiguiente);

		dispatcher.forward(request, response);
	}

	private String obtenerRolDeSesion(HttpServletRequest req) {
		HttpSession sesion = req.getSession();
		if (sesion.getAttribute("user") == null)
			return "ANONIMO";
		else if (((User) sesion.getAttribute("user")).getIsAdmin())
			return "ADMIN";
		else
			return "USUARIO";
	}

	// Obtiene un objeto accion en funci�n de la opci�n
	// enviada desde el navegador
	private Accion buscarObjetoAccionParaAccionNavegador(String rol,
			String opcion) {

		Accion accion = mapaDeAcciones.get(rol).get(opcion);
		Log.debug("Elegida acción [%s] para opción [%s] y rol [%s]", accion,
				opcion, rol);
		return accion;
	}

	// Obtiene la p�gina JSP a la que habr� que entregar el
	// control el funci�n de la opci�n enviada desde el navegador
	// y el resultado de la ejecuci�n de la acci�n asociada
	private String buscarJSPEnMapaNavegacionSegun(String rol, String opcion,
			String resultado) {

		String jspSiguiente = mapaDeNavegacion.get(rol).get(opcion)
				.get(resultado);
		Log.debug("Elegida página siguiente [%s] para el resultado [%s] tras "
				+ "realizar [%s] con rol [%s]", jspSiguiente, resultado,
				opcion, rol);
		return jspSiguiente;
	}

	private void crearMapaAcciones() {

		mapaDeAcciones = new HashMap<String, Map<String, Accion>>();
		// Anonimo
		Map<String, Accion> mapaPublico = new HashMap<String, Accion>();
		mapaPublico.put("validarse", new ValidarseAction());
		mapaPublico.put("registrarse", new RegistrarseAction());
		mapaPublico.put("crearCuenta", new CrearCuentaAction());
		mapaDeAcciones.put("ANONIMO", mapaPublico);
		// Usuario
		Map<String, Accion> mapaRegistrado = new HashMap<String, Accion>();
		mapaRegistrado.put("modificarDatos", new ModificarDatosAction());
		mapaRegistrado.put("cerrarSesion", new CerrarSesionAction());
		mapaRegistrado.put("filtrarTareas", new FiltrarTareasAction());
		mapaRegistrado.put("leerTarea", new LeerTareaAction());
		mapaRegistrado.put("añadirTarea", new AnnadirTareaAction());
		mapaRegistrado.put("editarTarea", new EditarTareaAction());
		mapaRegistrado.put("crearCategoria", new CrearCategoriaAction());
		mapaRegistrado.put("eliminarCategoria", new EliminarCategoriaAction());
		mapaRegistrado.put("cambiarNombreCategoria",
				new CambiarNombreCategoriaAction());
		mapaRegistrado.put("finalizarTarea", new FinalizarTareaAction());
		mapaRegistrado.put("auxiliarListaTareas", new AuxiliarAction());
		mapaDeAcciones.put("USUARIO", mapaRegistrado);
		// Admin
		Map<String, Accion> mapaAdmin = new HashMap<String, Accion>();
		mapaAdmin.put("modificarDatos", new ModificarDatosAction());
		mapaAdmin.put("cerrarSesion", new CerrarSesionAction());
		mapaAdmin.put("listarUsuarios", new ListarUsuariosAction());
		mapaAdmin.put("modificarUsuarios", new ModificarUsuariosAction());
		mapaAdmin.put("auxiliarListarUsuarios", new AuxiliarAction());
		mapaDeAcciones.put("ADMIN", mapaAdmin);
	}

	private void crearMapaDeNavegacion() {

		mapaDeNavegacion = new HashMap<String, Map<String, Map<String, String>>>();

		// Crear mapas auxiliares vacíos
		Map<String, Map<String, String>> opcionResultadoYJSP = new HashMap<String, Map<String, String>>();
		Map<String, String> resultadoYJSP = new HashMap<String, String>();

		// Mapa de navegación de anónimo
		resultadoYJSP.put("FRACASO", "/login.jsp");
		opcionResultadoYJSP.put("validarse", resultadoYJSP);
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/login.jsp");
		resultadoYJSP.put("FRACASO", "/crearCuenta.jsp");
		opcionResultadoYJSP.put("crearCuenta", resultadoYJSP);
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/crearCuenta.jsp");
		resultadoYJSP.put("FRACASO", "/login.jsp");
		opcionResultadoYJSP.put("registrarse", resultadoYJSP);
		// Cerrar sesión
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/login.jsp");
		resultadoYJSP.put("FRACASO", "/login.jsp");
		opcionResultadoYJSP.put("cerrarSesion", resultadoYJSP);
		mapaDeNavegacion.put("ANONIMO", opcionResultadoYJSP);

		// Crear mapas auxiliares vacíos
		opcionResultadoYJSP = new HashMap<String, Map<String, String>>();
		resultadoYJSP = new HashMap<String, String>();

		// Mapa de navegación de usuarios normales
		resultadoYJSP.put("EXITO", "/principalUsuario.jsp");
		opcionResultadoYJSP.put("validarse", resultadoYJSP);
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/principalUsuario.jsp");
		resultadoYJSP.put("FRACASO", "/principalUsuario.jsp");
		opcionResultadoYJSP.put("modificarDatos", resultadoYJSP);
		// Filtrar tareas
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/mostrarPseudolistas.jsp");
		resultadoYJSP.put("FRACASO", "/mostrarPseudolistas.jsp");
		opcionResultadoYJSP.put("filtrarTareas", resultadoYJSP);
		// Añadir tareas
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/confirmacionFinalizarTarea.jsp");
		resultadoYJSP.put("FRACASO", "/confirmacionFinalizarTarea.jsp");
		opcionResultadoYJSP.put("añadirTarea", resultadoYJSP);
		// Leer tarea
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/editarTarea.jsp");
		resultadoYJSP.put("FRACASO", "/mostrarPseudolistas.jsp");
		opcionResultadoYJSP.put("leerTarea", resultadoYJSP);
		// Editar tarea
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/filtrarTareas");
		resultadoYJSP.put("FRACASO", "/editarTarea.jsp");
		opcionResultadoYJSP.put("editarTarea", resultadoYJSP);
		// Crear categoría
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/mostrarPseudolistas.jsp");
		resultadoYJSP.put("FRACASO", "/mostrarPseudolistas.jsp");
		opcionResultadoYJSP.put("crearCategoria", resultadoYJSP);
		// Eliminar categoría
		opcionResultadoYJSP.put("eliminarCategoria", resultadoYJSP);
		// Cambiar nombre a la categoría
		opcionResultadoYJSP.put("cambiarNombreCategoria", resultadoYJSP);
		// Finalizar tarea
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/confirmacionFinalizarTarea.jsp");
		resultadoYJSP.put("FRACASO", "/mostrarPseudolistas.jsp");
		opcionResultadoYJSP.put("finalizarTarea", resultadoYJSP);
		// Para ir desde la confirmación hasta la lista de tareas de nuevo
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/filtrarTareas");
		opcionResultadoYJSP.put("auxiliarListaTareas", resultadoYJSP);

		mapaDeNavegacion.put("USUARIO", opcionResultadoYJSP);

		// Crear mapas auxiliares vacíos
		opcionResultadoYJSP = new HashMap<String, Map<String, String>>();
		resultadoYJSP = new HashMap<String, String>();

		// Mapa de navegación de administrador
		resultadoYJSP.put("EXITO", "/principalUsuario.jsp");
		opcionResultadoYJSP.put("validarse", resultadoYJSP);
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/principalUsuario.jsp");
		resultadoYJSP.put("FRACASO", "/principalUsuario.jsp");
		opcionResultadoYJSP.put("modificarDatos", resultadoYJSP);

		// Listar usuarios
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listarUsuarios.jsp");
		resultadoYJSP.put("FRACASO", "/principalUsuario.jsp");
		opcionResultadoYJSP.put("listarUsuarios", resultadoYJSP);

		// Modificar usuarios
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/confirmacionCambioEstadoUsuario.jsp");
		opcionResultadoYJSP.put("modificarUsuarios", resultadoYJSP);

		// Para ir desde la confirmación hasta la lista de usuarios de nuevo
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listarUsuarios");
		opcionResultadoYJSP.put("auxiliarListarUsuarios", resultadoYJSP);

		mapaDeNavegacion.put("ADMIN", opcionResultadoYJSP);

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {

		doGet(req, res);
	}

}
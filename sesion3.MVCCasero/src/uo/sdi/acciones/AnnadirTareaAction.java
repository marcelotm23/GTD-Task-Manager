package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import alb.util.date.DateUtil;
import alb.util.log.Log;
import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Task;
import uo.sdi.dto.User;

public class AnnadirTareaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "EXITO";

		Task tarea;

		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			
			String nombreTarea = request.getParameter("nombreTarea");
			Long idUser = user.getId();

			// Creación de la tarea con sus atributos fijos
			tarea = new Task();
			tarea.setTitle(nombreTarea);
			tarea.setUserId(idUser);
			tarea.setCreated(DateUtil.now());

			// Atributos variables
			String idOpcion = request.getParameter("idOpcion");
			if (idOpcion.equals("today")) {
				tarea.setPlanned(DateUtil.today());
			} else if (!idOpcion.equals("inbox") && !idOpcion.equals("week")) {
				Long idCategoria = Long.parseLong(idOpcion);
				tarea.setCategoryId(idCategoria);
			}

			TaskService ts = Services.getTaskService();
			ts.createTask(tarea);

			request.setAttribute("mensajeParaElUsuario",
					"La tarea ha sido creada con éxito.");

		} catch (BusinessException b) {
			Log.debug("Algo ha ocurrido en la creación de la tarea: %s",
					b.getMessage());
			request.setAttribute("mensajeParaElUsuario",
					"Ha ocurrido un error en la creación de la tarea, inténtelo de nuevo.");
			resultado = "FRACASO";
		}
		FiltrarTareasAction f = new FiltrarTareasAction();
		f.execute(request, response);

		return resultado;
	}

}

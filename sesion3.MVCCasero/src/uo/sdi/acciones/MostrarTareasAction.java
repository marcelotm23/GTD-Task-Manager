package uo.sdi.acciones;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Category;
import uo.sdi.dto.Task;
import uo.sdi.dto.User;
import alb.util.log.Log;

public class MostrarTareasAction implements Accion{

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String resultado="EXITO";
		
		List<Task> listaTareas;
		List<Category> listaCategorias;
		
		try {
			HttpSession session=request.getSession();
			User user=(User) session.getAttribute("user");
			//Tareas
			TaskService taskService = Services.getTaskService();
			listaTareas=taskService.findFinishedInboxTasksByUserId(user.getId());
			//Categorias
			listaCategorias=taskService.findCategoriesByUserId(user.getId());
			request.setAttribute("listaTareas", listaTareas);
			request.setAttribute("listaCategorias", listaCategorias);
			Log.debug("Obtenida lista de tareas conteniendo [%d] tareas", 
					listaTareas.size());
		}
		catch (BusinessException b) {
			Log.debug("Algo ha ocurrido obteniendo lista de tareas: %s",
					b.getMessage());
			resultado="FRACASO";
		}
		return resultado;
	}

}

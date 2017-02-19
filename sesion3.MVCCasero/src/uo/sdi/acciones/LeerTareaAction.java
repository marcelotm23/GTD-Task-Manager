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

public class LeerTareaAction implements Accion{

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String resultado="EXITO";
		
		Long idTarea=Long.parseLong(request.getParameter("idTarea"));
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("user");
		
		TaskService taskService = Services.getTaskService();
		Task selecTask=null;
		try {
			selecTask = taskService.findTaskById(idTarea);
			List<Category> listaCategorias = taskService.findCategoriesByUserId(user.getId());
			request.setAttribute("listaCategorias", listaCategorias);
			if (selecTask!=null) {
				session.setAttribute("task", selecTask);
				Log.info("Se ha leeido la tarea con id [%s]",idTarea);
			}
			else {
				session.invalidate();
				Log.info("La tarea con id [%s] no se ha encontrado",idTarea);
				request.setAttribute("mensajeParaElUsuario", "La tarea con id ["+
						idTarea+"] no ha sido encontrada");
				resultado="FRACASO";
			}
		} catch (BusinessException b) {
			session.invalidate();
			Log.debug("Algo ha ocurrido intentando leer la tarea con id [%d]: %s", 
					idTarea,b.getMessage());
			request.setAttribute("mensajeParaElUsuario", b.getMessage());
			resultado="FRACASO";
		}
		
		return resultado;
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}

}

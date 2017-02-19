package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Task;
import uo.sdi.dto.util.Cloner;
import alb.util.date.DateUtil;
import alb.util.log.Log;

public class EditarTareaAction implements Accion{

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String resultado = "EXITO";

		String titulo = request.getParameter("title");
		String comentarios = request.getParameter("comments");
		String fechaPlaneada = request.getParameter("planned");
		String categoria = request.getParameter("category");
		HttpSession session = request.getSession();
		Task task = ((Task) session.getAttribute("task"));
		Task taskClone = Cloner.clone(task);

		try {
			taskClone.setTitle(titulo);
			taskClone.setComments(comentarios);
			taskClone.setPlanned(DateUtil.fromString(fechaPlaneada));
			//taskClone.setCategoryId(category_id);
			
			TaskService taskService=Services.getTaskService();
			taskService.updateTask(taskClone);
		} catch (BusinessException b) {
			Log.debug(
					"Algo ha ocurrido actualizando la tarea de título [%s]: ",
					titulo, b.getMessage());
		}
		return resultado;
	}

}

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
		String optionId=request.getParameterValues("category")[0];
		Long categoriaId = null;
		if(optionId.compareTo("inbox")!=0){
			categoriaId = Long.parseLong(optionId);
		}
		
		HttpSession session = request.getSession();
		Task task = ((Task) session.getAttribute("task"));
		Task taskClone = Cloner.clone(task);

		
		try {
			taskClone.setTitle(titulo);
			taskClone.setComments(comentarios);
			taskClone.setPlanned(DateUtil.fromString(fechaPlaneada));

			if(categoriaId!=task.getCategoryId()){
				taskClone.setCategoryId(categoriaId);
			}
			
			TaskService taskService=Services.getTaskService();
			taskService.updateTask(taskClone);
		} catch (BusinessException b) {
			resultado = "FRACASO";
			Log.debug(
					"Algo ha ocurrido actualizando la tarea de título [%s]: %s",
					titulo, b.getMessage());
		}
		return resultado;
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}

}

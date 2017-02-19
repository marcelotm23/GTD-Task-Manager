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

public class FiltrarTareasAction implements Accion{

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
			
			String idOpcion=request.getParameter("idOpcion");
			if(idOpcion==null){idOpcion="inbox";}
			boolean mostrarFinalizadas=(request.getParameter("cb_mostrarFinalizadas")
					!= null) ;
			if(idOpcion.compareTo("inbox")==0){
				listaTareas=taskService.findInboxTasksByUserId(user.getId());
				if (mostrarFinalizadas) {
					listaTareas.addAll(taskService.findFinishedInboxTasksByUserId(user.getId()));
				}else{
					listaTareas.removeAll(taskService.findFinishedInboxTasksByUserId(user.getId()));
				}
			}else if(idOpcion.compareTo("today")==0){
				listaTareas=taskService.findTodayTasksByUserId(user.getId());
			}else if(idOpcion.compareTo("week")==0){
				listaTareas=taskService.findWeekTasksByUserId(user.getId());
			}else{
				Long idCategoria= Long.parseLong(idOpcion);
				listaTareas=taskService.findTasksByCategoryId(idCategoria);
				if(mostrarFinalizadas){
					listaTareas.addAll(taskService.findFinishedTasksByCategoryId(idCategoria));
				}else{
					listaTareas.removeAll(taskService.findFinishedTasksByCategoryId(idCategoria));
				}
			}
			
			session.setAttribute("idOpcion", idOpcion);
			session.setAttribute("listaTareas", listaTareas);
			
			//Categorias
			listaCategorias=taskService.findCategoriesByUserId(user.getId());
			session.setAttribute("listaCategorias", listaCategorias);
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

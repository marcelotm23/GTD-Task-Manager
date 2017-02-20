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

public class EliminarCategoriaAction implements Accion{

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String resultado="EXITO";
		String idCategoria=request.getParameter("idCategoria");
		
		try {
			HttpSession session=request.getSession();
			
			TaskService taskService = Services.getTaskService();
			List<Category> listaCategorias=null;
			List<Task> listaTareas=null;
			if(idCategoria==null){
				throw new BusinessException("El nombre de la nueva categoría no "
						+ "puede ser cadena vacía.");
			}else{
				Long id=Long.parseLong(idCategoria);
				taskService.deleteCategory(id);
				User user=(User) session.getAttribute("user");
				Log.debug("La categoría : [%s] ha sido eliminada", 
						idCategoria);
				listaTareas = taskService.findTasksByCategoryId(id);
				listaCategorias = taskService.findCategoriesByUserId(user.getId());
				session.setAttribute("listaCategorias", listaCategorias);
				session.setAttribute("listaTareas", listaTareas);
				session.setAttribute("idOpcion", "inbox");
			}
			
		} catch (BusinessException b) {
			resultado="FRACASO";
			Log.debug("La categoría : [%s] no se añadido correctamente por :[%s]", 
					idCategoria, b.getMessage());
			request.setAttribute("mensajeParaElUsuario", "La categoría : ["+
					idCategoria+"] no se eliminó correctamente.");
		}
		return resultado;
		
	}
	@Override
	public String toString() {
		return getClass().getName();
	}
}

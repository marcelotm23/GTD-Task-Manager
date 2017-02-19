package uo.sdi.acciones;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import alb.util.log.Log;
import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Category;
import uo.sdi.dto.User;

public class CrearCategoriaAction implements Accion{

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String resultado="EXITO";
		String nombreCategoria="";
		
		try {
			HttpSession session=request.getSession();
			User user=(User) session.getAttribute("user");
			nombreCategoria=request.getParameter("nombreCategoria");
			TaskService taskService = Services.getTaskService();
			List<Category> listaCategorias=null;
			if(nombreCategoria.compareTo("")==0){
				throw new BusinessException("El nombre de la nueva categoría no "
						+ "puede ser cadena vacía.");
			}else{
				Category newCategory=new Category();
				newCategory.setName(nombreCategoria);
				newCategory.setUserId(user.getId());
				taskService.createCategory(newCategory);
				Log.debug("La categoría : [%s]", 
						nombreCategoria);
				listaCategorias = taskService.findCategoriesByUserId(user.getId());
				session.setAttribute("listaCategorias", listaCategorias);
			}
			
			
			
		} catch (BusinessException b) {
			resultado="FRACASO";
			Log.debug("La categoría : [%s] no se añadido correctamente por :[%s]", 
					nombreCategoria, b.getMessage());
			request.setAttribute("mensajeParaElUsuario", "La categoría : ["+
					nombreCategoria+"] no se añadido correctamente. Esta no puede ser "
							+ "cadena vacía");
		}
		return resultado;
		
	}
	@Override
	public String toString() {
		return getClass().getName();
	}

}

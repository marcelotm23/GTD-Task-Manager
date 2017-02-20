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

public class CambiarNombreCategoriaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String resultado = "EXITO";
		TaskService taskService = Services.getTaskService();
		String idCategoria = request.getParameter("idCategoria");
		String nombreCategoria = request.getParameter("nombreCategoria");
		Long id = Long.parseLong(idCategoria);
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			Category category = taskService.findCategoryById(id);
			category.setName(nombreCategoria);
			taskService.updateCategory(category);
			List<Category> listaCategorias = taskService
					.findCategoriesByUserId(user.getId());
			session.setAttribute("listaCategorias", listaCategorias);
		} catch (BusinessException b) {
			resultado = "FRACASO";
			Log.debug("Algo ha ocurrido actualizando la categoría [%s]: %s",
					nombreCategoria, b.getMessage());
			request.setAttribute("mensajeParaElUsuario", "ERROR: La categoría "
					+ "[" + nombreCategoria
					+ "] no se ha eliminado correctamente, "
					+ "por el motivo [" + b.getMessage() + "]");
		}
		return resultado;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}
}

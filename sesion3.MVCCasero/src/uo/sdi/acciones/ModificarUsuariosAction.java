package uo.sdi.acciones;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import alb.util.log.Log;
import uo.sdi.business.AdminService;
import uo.sdi.business.Services;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.User;
import uo.sdi.dto.types.UserStatus;

public class ModificarUsuariosAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "EXITO";

		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		List<User> listaUsuarios = (List<User>) session
				.getAttribute("listaUsuarios");

		AdminService adminService = Services.getAdminService();
		boolean modificadoAlgunUsuario = false;
		boolean borradoAlgunUsuario = false;
		String mensajeParaElUsuario;

		try {
			for (int i = 0; i < listaUsuarios.size(); i++) {
				User user = listaUsuarios.get(i);
				if (request.getParameter("eliminar_" + i) != null) {
					adminService.deepDeleteUser(user.getId());
					borradoAlgunUsuario = true;
				} else {
					if (request.getParameter("cb_" + i) != null) {
						if (user.getStatus().equals(UserStatus.DISABLED)) {
							modificadoAlgunUsuario = true;
						}
						adminService.enableUser(user.getId());
					} else {
						if (user.getStatus().equals(UserStatus.ENABLED)) {
							modificadoAlgunUsuario = true;
						}
						adminService.disableUser(user.getId());
					}
					Log.debug("El user [%s] ha cambiado de status", user);
				}
			}
			if (modificadoAlgunUsuario) {
				mensajeParaElUsuario = "Se ha modificado el estado de los usuarios con éxito";
				if(borradoAlgunUsuario) {
					mensajeParaElUsuario+= " y se han borrado uno o más usuarios.";
				}
				request.setAttribute("mensajeParaElUsuario",
						mensajeParaElUsuario);
			} else {
				mensajeParaElUsuario = "No se ha modificado el estado ningún usuario";
				if(borradoAlgunUsuario) {
					mensajeParaElUsuario+= " y se han borrado uno o más usuarios.";
				}
				request.setAttribute("mensajeParaElUsuario",
						mensajeParaElUsuario);
			}
		} catch (BusinessException e) {
			Log.debug("Ha ocurrido un error [%s]", e.getMessage());
			request.setAttribute("mensajeParaElUsuario",
					"Ha ocurrido el siguiente error: " + e.getMessage());
		}

		return resultado;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}

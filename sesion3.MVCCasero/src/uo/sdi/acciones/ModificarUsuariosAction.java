package uo.sdi.acciones;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import alb.util.log.Log;
import uo.sdi.business.Services;
import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.User;
import uo.sdi.dto.types.UserStatus;
import uo.sdi.dto.util.Cloner;

public class ModificarUsuariosAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "EXITO";

		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		List<User> listaUsuarios = (List<User>) session
				.getAttribute("listaUsuarios");

		try {
			for (int i = 0; i < listaUsuarios.size(); i++) {
				User userClone = Cloner.clone(listaUsuarios.get(i));
				if ( request.getParameter("cb_" + i) != null ) {
					userClone.setStatus(UserStatus.ENABLED);
				} else {
					userClone.setStatus(UserStatus.DISABLED);
				}
				UserService userService = Services.getUserService();
				userService.updateUserDetails(userClone);
				Log.debug("El user [%s] ha pasado de [%s] a [%s]", 
						listaUsuarios.get(i), 
						listaUsuarios.get(i).getStatus(), 
						userClone.getStatus());
			}
			request.setAttribute("mensajeParaElUsuario",
					"Se ha modificado el estado los usuarios con éxito");
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

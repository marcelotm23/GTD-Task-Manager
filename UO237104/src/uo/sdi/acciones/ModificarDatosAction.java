package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import alb.util.log.Log;
import uo.sdi.business.Services;
import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.User;
import uo.sdi.dto.util.Cloner;

public class ModificarDatosAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "EXITO";

		String nuevoEmail = request.getParameter("email");
		String contrasenaAntigua = request.getParameter("contrasenaAntigua");
		String contrasenaNueva = request.getParameter("contrasenaNueva");
		String contrasenaNuevaAgain = request
				.getParameter("contrasenaNuevaAgain");
		HttpSession session = request.getSession();
		User user = ((User) session.getAttribute("user"));
		User userClone = Cloner.clone(user);

		try {
			if (contrasenaAntigua.equals(user.getPassword())) {
				userClone.setEmail(nuevoEmail);
				if (contrasenaNueva.length() != 0
						&& contrasenaNuevaAgain.length() != 0
						&& contrasenaNueva.equals(contrasenaNuevaAgain))
					userClone.setPassword(contrasenaNueva);
			}
			if (!user.equals(userClone)) {
				UserService userService = Services.getUserService();
				userService.updateUserDetails(userClone);
				Log.debug("Se ha modificado la información del usuario [%s]",
						userClone.getLogin());
				session.setAttribute("user", userClone);
				request.setAttribute("mensajeParaElUsuario",
						"Se han guardo los " + "cambios realizados.");
			} else {
				Log.debug("No se ha modificado ningún dato del usuario [%s]",
						userClone.getLogin());
				request.setAttribute("mensajeParaElUsuario", "No ha realizado "
						+ "ningún cambio.");
				resultado = "FRACASO";
			}
		} catch (BusinessException b) {
			Log.debug(
					"Algo ha ocurrido actualizando el email de [%s] a [%s]: %s",
					user.getLogin(), nuevoEmail, b.getMessage());
			request.setAttribute("mensajeParaElUsuario",
					"ERROR: " + b.getMessage());

		}
		return resultado;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}

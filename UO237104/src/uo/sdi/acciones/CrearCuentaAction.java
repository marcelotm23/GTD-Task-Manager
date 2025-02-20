package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.acciones.util.Checks;
import uo.sdi.business.Services;
import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.User;
import uo.sdi.dto.types.UserStatus;
import alb.util.log.Log;

public class CrearCuentaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "EXITO";

		String nombreUsuario = request.getParameter("nombreUsuario");
		String email = request.getParameter("email");
		String contrasena = request.getParameter("contrasena");
		String contrasenaAgain = request.getParameter("contrasenaAgain");
		try {
			if (checkData(email, contrasena, contrasenaAgain)) {
				UserService userService = Services.getUserService();
				User newUser = new User();
				newUser.setLogin(nombreUsuario);
				newUser.setStatus(UserStatus.ENABLED);
				newUser.setEmail(email);
				newUser.setPassword(contrasena);
				newUser.setIsAdmin(false);

				userService.registerUser(newUser);
				Log.debug("El usuario [%s] ha sido registrado", nombreUsuario);
				request.setAttribute("mensajeParaElUsuario",
						"Se ha registrado correctamente. Puede proceder a "
								+ "logearse.");

			}
		} catch (BusinessException e) {
			Log.debug(
					"El usuario [%s] no ha sido registrado, por el motivo: [%s]",
					nombreUsuario, e.getMessage());
			request.setAttribute("mensajeParaElUsuario", e.getMessage());
			resultado = "FRACASO";
		}
		return resultado;
	}

	private boolean checkData(String email, String contrasena,
			String contrasenaAgain) throws BusinessException {
		return Checks.isValidEmailAddress(email)
				&& Checks.isValidPassword(contrasena)
				&& Checks.isSamePassword(contrasena, contrasenaAgain);
	}

}

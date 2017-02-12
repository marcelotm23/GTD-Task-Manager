package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CerrarSesionAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		
		HttpSession session=request.getSession();
			session.invalidate();
		}else{
			resultado="FRACASO";
		}
		
		return resultado;
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}
	
}

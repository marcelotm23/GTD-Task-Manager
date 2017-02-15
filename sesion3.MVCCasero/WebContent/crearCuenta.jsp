<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="comprobarNavegacion.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Crear nueva cuenta de usuario</title>
</head>
<body>
	<h1>Crear nueva cuenta de usuario</h1>
	<br />
	
	<form action="crearCuenta" method="POST">
		<table>
			<tr> 
	    		<td align="right">Identificador de usuario:</td>
		    	<td><input type="text" name="nombreUsuario" align="left" size="15"></td>
      		</tr>
			<tr>
				<td>Email:</td>
				<td id="email"><input type="text" name="email" size="30"
					placeholder="Ej: ejemplo@ejemplo.com"></td>
			</tr>
			<tr>
				<td>Contraseña:</td>
				<td id="contrasena"><input type="password"
					name="contrasena" size="20" /></td>
			</tr>
			<tr>
				<td>Repita la contraseña:</td>
				<td id="contrasenaAgain"><input type="password"
					name="contrasenaAgain" size="20" /><input type="submit"
					value="Crear"></td>
			</tr>
			
		</table>
	</form>
	<br />
	<a id="cerrarSesion_link_id" href="cerrarSesion">Atrás</a>

	<%@ include file="pieDePagina.jsp"%>
</body>
</html>

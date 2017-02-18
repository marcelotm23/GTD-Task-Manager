<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="comprobarNavegacion.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Página principal del usuario</title>
</head>
<body>
	<i>Iniciaste sesión el <fmt:formatDate
			pattern="dd-MM-yyyy' a las 'HH:mm"
			value="${sessionScope.fechaInicioSesion}" /> (usuario número
		${contador})
	</i>
	<br />
	<br />
	<jsp:useBean id="user" class="uo.sdi.dto.User" scope="session" />
	<form action="modificarDatos" method="POST">
		<table>
			<tr>
				<td>Id:</td>
				<td id="id"><jsp:getProperty property="id" name="user" /></td>
			</tr>
			<tr>
				<td>Email:</td>
				<td id="email"><input type="text" name="email" size="15"
					value="<jsp:getProperty property="email" name="user"/>"></td>
			</tr>
			<tr>
				<td>Contraseña antigua:</td>
				<td id="contrasenaAntigua"><input type="password"
					name="contrasenaAntigua" size="20" /></td>
			</tr>
			<tr>
				<td>Contraseña nueva:</td>
				<td id="contrasenaNueva"><input type="password"
					name="contrasenaNueva" size="20" /></td>
			</tr>
			<tr>
				<td>Comprobación de contraseña:</td>
				<td id="contrasenaNuevaAgain"><input type="password"
					name="contrasenaNuevaAgain" size="20" /><input type="submit"
					value="Modificar"></td>
			</tr>
			<tr>
				<td>Es administrador:</td>
				<td id="isAdmin"><jsp:getProperty property="isAdmin"
						name="user" /></td>
			</tr>
			<tr>
				<td>Login:</td>
				<td id="login"><jsp:getProperty property="login" name="user" /></td>
			</tr>
			<tr>
				<td>Estado:</td>
				<td id="status"><jsp:getProperty property="status" name="user" /></td>
			</tr>
		</table>
	</form>
	<br />
	<c:if test="${ !user.getIsAdmin() }">
		<a id="filtrarTareas_link_id" href="filtrarTareas">Mostrar tareas</a>
	</c:if>
	<c:if test="${ user.getIsAdmin() }">
		<br/>
		<a id="listarUsuarios_link_id" href="listarUsuarios">Lista usuarios</a>
	</c:if>
	<br />
	<a id="cerrarSesion_link_id" href="cerrarSesion">Cerrar sesión</a>

	<%@ include file="pieDePagina.jsp"%>
</body>
</html>

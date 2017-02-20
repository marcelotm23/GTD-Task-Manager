<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Listado de usuarios</title>
</head>
<body>
	<form action="modificarUsuarios" method="POST">
		<table border="1" align="center">
			<tr>
				<th>Nombre</th>
				<th>Email</th>
				<th>Status</th>
				<th>Habilitar/Deshabilitar</th>
				<th>Eliminar</th>
			</tr>
			<c:forEach var="entry" items="${listaUsuarios}" varStatus="i">
				<tr id="item_${i.index}">
					<td>${entry.login}</td>
					<td>${entry.email}</td>
					<td>${entry.status}</td>
					<c:if test="${entry.status == \"ENABLED\"}">
						<td><input name="cb_${entry.login}" type="checkbox"
							checked="checked" /></td>
					</c:if>
					<c:if test="${entry.status == \"DISABLED\"}">
						<td><input name="cb_${entry.login}" type="checkbox" /></td>
					</c:if>
					<td><input name="eliminar_${entry.login}" type="checkbox" /></td>
				</tr>
			</c:forEach>
		</table>
		<p>Si selecciona el checkbox de eliminar, se borrará el usuario
			correspondiente así como todas sus categorias y tareas</p>
		<input type="submit" align="right" value="Modificar usuarios"
		onclick="return confirm('¿Estás seguro de confirmar los cambios?. En caso de eliminar usuarios esta acción no se puede deshacer.');">
	</form>
	<br>
	<%@ include file="pieDePagina.jsp"%>
</body>
</html>
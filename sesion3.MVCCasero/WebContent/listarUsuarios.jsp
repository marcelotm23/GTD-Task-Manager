<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Listado de tareas</title>
</head>
<body>
	<form action="modificarUsuarios" method="POST">
		<table border="1" align="center">
			<tr>
				<th>Nombre</th>
				<th>Email</th>
				<th>Status</th>
				<th>Habilitar/Deshabilitar</th>
			</tr>
			<c:forEach var="entry" items="${listaUsuarios}" varStatus="i">
				<tr id="item_${i.index}">
					<td>${entry.login}</td>
					<td>${entry.email}</td>
					<td>${entry.status}</td>
					<c:if test="${entry.status == \"ENABLED\"}">
						<td><input name="cb_${i.index}" type="checkbox"
							checked="checked" /></td>
					</c:if>
					<c:if test="${entry.status == \"DISABLED\"}">
						<td><input name="cb_${i.index}" type="checkbox" /></td>
					</c:if>
				</tr>
			</c:forEach>
			<tr>
				<td><input type="submit" align="right"
					value="Modificar usuarios"></td>
			</tr>
		</table>
	</form>
	<a id="atras_link_id" href="principalUsuario.jsp">Atrás</a>
	<%@ include file="pieDePagina.jsp"%>
</body>
</html>
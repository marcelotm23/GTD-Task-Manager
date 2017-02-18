<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<style>
div#menu {float:left;}
div#filtro {float: right;};
</style>
<title>TaskManager - Pseudolistas</title>
</head>
<body>
<div id="menu">
  <h1>GTD Manager</h1>
  <ul>
	 <%@ include file="pseudolistasUsuario.jsp" %>
  </ul>
</div>
<c:if test="${idOpcion!=\"week\" && idOpcion!=\"today\"}">
	<div id="filtro">
		<h2>Filtros</h2>
		<br>
		<form action="filtrarTareas?idOpcion=${idOpcion}" method="POST">
			<input name="cb_mostrarFinalizadas" type="checkbox">
			Mostrar tareas finalizadas<br>
			<input type="submit" align="right" value="Aplicar">
		</form>
	</div>
</c:if>
<c:choose>
	<c:when test="${!empty listaTareas}">
		<%@ include file="mostrarTareas.jsp" %>
	</c:when>
	<c:otherwise>
		<h4>No se han encontrado tareas</h4>
	</c:otherwise>
</c:choose>

	<%@ include file="pieDePagina.jsp" %>
</body>
</html>
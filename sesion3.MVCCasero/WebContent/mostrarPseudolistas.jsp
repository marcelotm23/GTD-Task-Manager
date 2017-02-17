<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Pseudolistas</title>
</head>
<body>
<div id="menu">
  <h1>GTD Manager</h1>
  <ul>
	 <%@ include file="pseudolistasUsuario.jsp" %>
  </ul>
</div>
<c:choose>
	<c:when test="${!empty listaCategorias}">
	<%@ include file="mostrarTareas.jsp" %>
	</c:when>
	<c:otherwise>
	<h4>No se han encontrado tareas</h4>
	</c:otherwise>
</c:choose>

	<%@ include file="pieDePagina.jsp" %>
</body>
</html>
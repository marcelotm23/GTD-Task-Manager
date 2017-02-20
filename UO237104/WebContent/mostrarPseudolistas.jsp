<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<style>
div#menu {
	float: left;
}

div#filtro {
	float: right;
}
;
</style>
<script>
function cambiarNombreCategoria(id) {
    var categoryName = prompt("Introduzca el nuevo nombre para esta categoría", "Categoría "+id);
    if (categoryName != null) {
        document.getElementById("cambiarNombreCategoria"+id).href =
        "cambiarNombreCategoria?idCategoria="+id+"&nombreCategoria=\""+categoryName+"\"";
        return true;
    }else{
    	return false;
    }
}
</script>

<title>TaskManager - Pseudolistas</title>
</head>
<body>
	<div id="menu">
		<h1>GTD Manager</h1>
		<ul>
			<li><a id="inbox_link" href="filtrarTareas?idOpcion=inbox">Inbox</a></li>
			<li><a id="today_link" href="filtrarTareas?idOpcion=today">Hoy</a></li>
			<li><a id="week_link" href="filtrarTareas?idOpcion=week">Semana</a></li>
		</ul>
		<dl>
		  <dt>Categorías</dt>
		  <c:if test="${!empty listaCategorias}">
		  <dd><c:forEach var="entry" items="${listaCategorias}" varStatus="i">
		   <li><a href="filtrarTareas?idOpcion=${entry.id}">${entry.name}</a>
		   <a 
		   href="eliminarCategoria?idCategoria=${entry.id}" title="Eliminar categoría"
		   onclick="return confirm('Esta acción eliminará la categoría con todas sus tareas. ¿Estás seguro?');">X</a>
		   <a id="cambiarNombreCategoria${entry.id}"
		   title="Cambiar nombre categoría"
		   onclick="return cambiarNombreCategoria(${entry.id});">&#9997;</a>
		   </li>
			</c:forEach></dd>
		  </c:if>
		</dl>
		
<form action="crearCategoria" method="POST">
	<input type="text" name="nombreCategoria" size="20">
	<br>
	<input type="submit" value="+ Crear categoría">
</form>
	</div>
	<div id="añadirTarea">
		<h2 align="center">Añadir tarea</h2>
		<form name="añadirTarea_form" action="añadirTarea?idOpcion=${idOpcion}" 
		method="POST">
			<table align="center">
				<tr>
					<td>Nombre de la tarea</td>
					<td><input type="text" name="nombreTarea" align="left"
						size="25"></td>
					<td><input type="submit" align="right" value="Añadir"></td>
				</tr>
			</table>
			<br> <br>
		</form>
	</div>
	<c:if test="${idOpcion!=\"week\" && idOpcion!=\"today\"}">
		<div id="filtro">
			<h2>Filtros</h2>
			<br>
			<form name="filtros_form" action="filtrarTareas?idOpcion=${idOpcion}" method="POST">
				<input name="cb_mostrarFinalizadas" type="checkbox"> Mostrar
				tareas finalizadas<br> <input type="submit" align="right"
					value="Aplicar">
			</form>
		</div>
	</c:if>
	<c:choose>
		<c:when test="${!empty listaTareas}">
			<c:if test="${idOpcion==\"today\"}">
				<%@ include file="mostrarTareasHoy.jsp"%>
			</c:if>
			<c:if test="${idOpcion!=\"today\"}">
				<%@ include file="mostrarTareas.jsp"%>
			</c:if>
		</c:when>
		<c:otherwise>
			<h4 align="center">No se han encontrado tareas</h4>
		</c:otherwise>
	</c:choose>

	<p align="center"><%@ include file="pieDePagina.jsp"%></p>
</body>
</html>
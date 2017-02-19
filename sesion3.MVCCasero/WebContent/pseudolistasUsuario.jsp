<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<li><a href="filtrarTareas?idOpcion=inbox">Inbox</a></li>
<li><a href="filtrarTareas?idOpcion=today">Hoy</a></li>
<li><a href="filtrarTareas?idOpcion=week">Semana</a></li>

<dl>
  <dt>Categorías</dt>
  <c:if test="${!empty listaCategorias}">
  <dd><c:forEach var="entry" items="${listaCategorias}" varStatus="i">
   <li><a href="filtrarTareas?idOpcion=${entry.id}">${entry.name}</a></li>
	</c:forEach></dd>
  </c:if>
</dl>

<form action="crearCategoria?idOpcion=inbox" method="POST">
	<input type="text" name="nombreCategoria" size="20">
	<br>
	<input type="submit" value="+ Crear categoría">
</form>
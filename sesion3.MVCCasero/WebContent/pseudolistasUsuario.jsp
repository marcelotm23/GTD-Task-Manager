<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<li><a href="filtrarTareas?idOpcion=inbox">Inbox</a></li>
<li><a href="filtrarTareas?idOpcion=today">Hoy</a></li>
<li><a href="filtrarTareas?idOpcion=week">Semana</a></li>
<c:if test="${!empty listaCategorias}">
<dl>
  <dt>Categorías</dt>
  <dd><c:forEach var="entry" items="${listaCategorias}" varStatus="i">
   <li><a href="filtrarTareas?idOpcion=${entry.id}">${entry.name}</a></li>
</c:forEach></dd>
</dl>
</c:if>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<li><a href="#inbox">Inbox</a></li>
<li><a href="#today">Hoy</a></li>
<c:if test="${!empty listaCategorias}">
<dl>
  <dt>Categorías</dt>
  <dd><c:forEach var="entry" items="${listaCategorias}" varStatus="i">
   <li><a href="${entry.id}">${entry.name}</a></li>
</c:forEach></dd>
</dl>
</c:if>

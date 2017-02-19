<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="comprobarNavegacion.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Editar tarea</title>
</head>
<body>
	<br />
	<jsp:useBean id="task" class="uo.sdi.dto.Task" scope="session" />
	<form action="editarTarea" method="POST">
		<h1>Editar tarea</h1>
		<table>
			<tr>
				<td>Título:</td>
				<td id="title">
				<input type="text"
					name="title" size="20" 
					value="<jsp:getProperty property="title" name="task" />">
					</td>
			</tr>
			<tr>
				<td>Comentarios:</td>
				<td id="comments"> 
					<textarea name="comments" rows="10" cols="30">
						<jsp:getProperty property="comments" name="task"/>
					</textarea>
				</td>
			</tr>
			<tr>
				<td>Fecha planeada:</td>
				<td id="planned">
				<input type="text"
					name="planned" size="20" 
					value="<fmt:formatDate pattern="dd/MM/yyyy" value="${task.planned}"/>"/>
				</td>
			</tr>
			<tr>
				<td>Categoría:</td>
				<td id="category">
				<input type="text"
					name="category" size="20" 
					value="<jsp:getProperty property="categoryId" name="task" />">
					</td>
			</tr>
		</table>
		<input type="submit" value="Modificar">
	</form>
	<br />
	<%@ include file="pieDePagina.jsp"%>
</body>
</html>

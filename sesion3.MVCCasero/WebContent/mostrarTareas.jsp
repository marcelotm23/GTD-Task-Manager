<%@ page import="java.util.Date, alb.util.date.DateUtil" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<table border="1" align="center">
			<tr>
				<th>ID</th>
				<th>Titulo</th>
				<th>Comentarios</th>
				<th>Fecha planeada</th>
				<th>Fecha finalizada</th>
				<th>Editar</th>
			</tr>
		<c:forEach var="entry" items="${listaTareas}" varStatus="i">
			<c:set var="plannedDate" value="${entry.planned}"/>
			<c:set var="finishedDate" value="${entry.finished}"/>
			<tr id="item_${i.index}">
				<td>${entry.id}</td>
				<td>${entry.title}</td>
				<td>${entry.comments}</td>
				<%
				java.text.DateFormat format = new java.text.SimpleDateFormat("dd/MM/yyyy");
					Date plannedDate=(Date)pageContext.getAttribute("plannedDate");
					Date finishedDate=(Date)pageContext.getAttribute("finishedDate");
				if(plannedDate!=null){	
					if(finishedDate==null && DateUtil.isBefore(plannedDate, DateUtil.today())){
							out.write("<td bgcolor=\"#FF0000\">"+DateUtil.toString(plannedDate)+"</td>");
						}else{
							out.write("<td>"+DateUtil.toString(plannedDate)+"</td>");
					}
				}else{out.write("<td>"+"</td>");}
				if(finishedDate!=null){
					out.write("<td>"+DateUtil.toString(finishedDate)+"</td>");
				}else{out.write("<td>"+"</td>");}
				%>
				<td><a id="editarTarea" href="leerTarea?idTarea=${entry.id}">Editar</a></td>
				
			</tr>
		</c:forEach>
	</table>
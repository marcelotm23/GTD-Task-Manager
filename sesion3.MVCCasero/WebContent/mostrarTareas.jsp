<%@ page import="java.util.Date, alb.util.date.DateUtil" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<table border="1" align="center">
			<tr>
				<th>ID</th>
				<th>Titulo</th>
				<th>Fecha planeada</th>
			</tr>
		<c:forEach var="entry" items="${listaTareas}" varStatus="i">
			<c:set var="plannedDate" value="${entry.planned}"/>
			<tr id="item_${i.index}">
				<td>${entry.id}</td>
				<td>${entry.title}</td>
				<%
					Date plannedDate=(Date)pageContext.getAttribute("plannedDate");
					if(DateUtil.isBefore(plannedDate, DateUtil.today())){
						out.write("<td bgcolor=\"#FF0000\">"+plannedDate+"</td>");
					}else{
						out.write("<td>"+plannedDate+"</td>");
				} %>
			</tr>
		</c:forEach>
	</table>
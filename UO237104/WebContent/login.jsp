<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html><head> <title>TaskManager - Inicie sesión</title>
<body>
  <form action="validarse" method="post" name="validarse_form_name">

 	<center><h1>Inicie sesión</h1></center>
 	<hr><br>
 	<table align="center">
    	<tr>
    		<td align="right">Su identificador de usuario</td>
	    	<td><input type="text" name="nombreUsuario" align="left" size="15"></td>
      	</tr>
      	<tr> 
    		<td align="right">Su contraseña</td>
	    	<td><input type="password" name="password" align="left" size="15"></td>
      	</tr>
      	<tr>
    	    <td><input type="submit" value="Enviar"/></td>
      	</tr>
      </table>
   </form>
   <p>¿No tienes cuenta?. Crea una<p>
   <a id="crearCuenta_link_id" href="registrarse">Crear cuenta</a>
   <%@ include file="pieDePagina.jsp" %>
</body>
</html>
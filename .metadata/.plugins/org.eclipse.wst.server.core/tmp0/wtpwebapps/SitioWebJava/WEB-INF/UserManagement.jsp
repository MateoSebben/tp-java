<%@page import="java.util.LinkedList"%>
<%@page import="entities.Usuario"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="http://getbootstrap.com/favicon.ico">
	<title>Sitio Web Java</title>
	
	<!-- Bootstrap core CSS -->
    <link href="style/bootstrap.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="style/start.css" rel="stylesheet">
    
    <%
    	Usuario u = (Usuario)session.getAttribute("usuario");
    	LinkedList<Usuario> lu = (LinkedList<Usuario>)request.getAttribute("listaUsuarios");
    
    %>
</head>
<body>

<div class="container">
		<div class="row">
        	<h4>Usuarios</h4>
            	<div class="col-12 col-sm-12 col-lg-12">
                	<div class="table-responsive">
                    	<table class="table">
                    		<thead>
                    			<tr>
                    				<th>id</th>
                    		    	<th>nombre</th>
                        			<th>apellido</th>
                        			<th>email</th>
                        			<th>rol</th>
                        			<th></th>
                        			<th></th>
                      			</tr>
                      		</thead>
                    		<tbody>
                    		<% for (Usuario usu : lu) { %>
                    			<tr>
                    				<td><%=usu.getId()%></td>
                    				<td><%=usu.getNombre()%></td>
                    				<td><%=usu.getApellido()%></td>
                    				<td><%=usu.getEmail()%></td>
                    				<td><%=usu.getRol()%></td>
                    				<td><button type="button" class="btn btn-outline-primary">Editar</button></td><!-- editar --> 
                    				<td></td><!-- borrar -->
                    			</tr>
                    		<% } %>
                    		</tbody>	
	</div> <!-- /container -->

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sitio Web Java </title>

    <!-- Custom styles for this template -->
    <link href="style/signup.css" rel="stylesheet">
    
</head>
<body>
  <div class="form-wrapper">
    <form class="form" method="post" action="registrarse">
        <h1 class="title">Registrarse </h1>
        <p class="message">Registrate ahora y obtené acceso completo a todos nuestros recursos!</p>

        <div class="flex">
            <label>
                <input name="nombre" required placeholder="" type="text" class="input">
                <span>Nombre</span>
            </label>

            <label>
                <input name="apellido" required placeholder="" type="text" class="input">
                <span>Apellido</span>
            </label>
        </div>  

        <label>
            <input name="email" required placeholder="" type="email" class="input">
            <span>Email</span>
        </label> 
        
        <label>
            <input name="password" required placeholder="" type="password" class="input">
            <span>Contraseña</span>
        </label>

        <label>
            <input name="confirmPassword" required placeholder="" type="password" class="input">
            <span>Confirmar contraseña</span>
        </label>

        <button class="submit" type="submit">Enviar</button>

        <p class="signin">¿Ya tenés una cuenta? <a href="index.html">Iniciar Sesión</a></p>

        <%-- Mostrar mensaje de error si lo hay --%>
        <%
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
            <p style="color:red;"><%= error %></p>
        <%
            }
        %>
    </form>
  </div>
</body>

</html>
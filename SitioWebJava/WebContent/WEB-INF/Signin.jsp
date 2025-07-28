<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="https://getbootstrap.com/favicon.ico">

    <title>Sitio Web Java</title>


    <!-- Custom styles for this template -->
    <link href="style/login.css" rel="stylesheet">
</head>

<body style="font-family: sans-serif;">
  <div class="form-wrapper">
    <form class="form" method="post" action="Signin">
        <h1 class="title">Iniciar Sesión </h1>
        <p class="message">Ingresá ahora y empeza a interactuar!</p>


        <label>
            <input name="email" required placeholder="" type="email" class="input">
            <span>Email</span>
        </label> 
        
        <label class="password-label"> <%-- Clase nueva para posicionamiento --%>
            <input id="password-input" name="password" required placeholder="" type="password" class="input">
            <span>Contraseña</span>
            <span class="toggle-password" id="togglePassword">👁️</span> <%-- Ícono para mostrar/ocultar --%>
        </label>

        <button class="submit" type="submit">Sign in</button>

        <p class="signin">¿No tenés una cuenta? <a href="signup">Registrarse</a></p>

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
  
  <script>
        // JavaScript para alternar la visibilidad de la contraseña
        const togglePassword = document.querySelector('#togglePassword');
        const passwordInput = document.querySelector('#password-input'); 

        togglePassword.addEventListener('click', function (e) {
            const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
            passwordInput.setAttribute('type', type);
            
            // Cambiar el ícono (opcional)
            this.textContent = (type === 'password') ? '👁️' : '🔒'; 
        });
    </script>
</body>
</html>
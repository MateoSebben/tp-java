<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bienvenido - Mi Aplicación</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        
        .welcome-container {
            background: white;
            border-radius: 20px;
            padding: 40px;
            box-shadow: 0 20px 40px rgba(0,0,0,0.1);
            text-align: center;
            max-width: 500px;
            width: 90%;
        }
        
        .welcome-header {
            color: #333;
            font-size: 2.5em;
            margin-bottom: 10px;
            font-weight: 300;
        }
        
        .user-name {
            color: #667eea;
            font-weight: 600;
        }
        
        .welcome-message {
            color: #666;
            font-size: 1.1em;
            margin-bottom: 30px;
            line-height: 1.6;
        }
        
        .info-card {
            background: #f8f9fa;
            border-radius: 10px;
            padding: 20px;
            margin: 20px 0;
            border-left: 4px solid #667eea;
        }
        
        .info-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin: 10px 0;
            padding: 5px 0;
        }
        
        .info-label {
            font-weight: 600;
            color: #333;
        }
        
        .info-value {
            color: #667eea;
            font-weight: 500;
        }
        
        .action-buttons {
            margin-top: 30px;
        }
        
        .btn {
            display: inline-block;
            padding: 12px 24px;
            margin: 5px;
            border: none;
            border-radius: 25px;
            text-decoration: none;
            font-weight: 600;
            transition: all 0.3s ease;
            cursor: pointer;
        }
        
        .btn-primary {
            background: linear-gradient(45deg, #667eea, #764ba2);
            color: white;
        }
        
        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 20px rgba(102, 126, 234, 0.3);
        }
        
        .btn-secondary {
            background: transparent;
            color: #667eea;
            border: 2px solid #667eea;
        }
        
        .btn-secondary:hover {
            background: #667eea;
            color: white;
        }
        
        .footer-info {
            margin-top: 30px;
            padding-top: 20px;
            border-top: 1px solid #eee;
            font-size: 0.9em;
            color: #999;
        }
    </style>
</head>
<body>
    <div class="welcome-container">
        <h1 class="welcome-header">
            ¡Bienvenido, <span class="user-name">${userName}</span>!
        </h1>
        
        <p class="welcome-message">
            Nos alegra tenerte aquí. Esta es tu página de bienvenida personalizada 
            donde puedes encontrar información sobre tu sesión actual.
        </p>
        
        <div class="info-card">
            <div class="info-item">
                <span class="info-label">Fecha y Hora:</span>
                <span class="info-value">${currentDateTime}</span>
            </div>
            
            <div class="info-item">
                <span class="info-label">Número de Visitas:</span>
                <span class="info-value">${visitCount}</span>
            </div>
            
            <div class="info-item">
                <span class="info-label">ID de Sesión:</span>
                <span class="info-value">${sessionId.substring(0, 8)}...</span>
            </div>
        </div>
        
        <div class="action-buttons">
            <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-primary">
                Ir al Dashboard
            </a>
            <a href="${pageContext.request.contextPath}/profile" class="btn btn-secondary">
                Ver Perfil
            </a>
        </div>
        
        <div class="action-buttons">
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-secondary">
                Cerrar Sesión
            </a>
        </div>
        
        <div class="footer-info">
            <p>Aplicación Web con JSP y Servlets</p>
            <p>Sesión iniciada correctamente</p>
        </div>
    </div>
</body>
</html>
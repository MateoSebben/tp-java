<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="entities.Usuario" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>NoteLift</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-LN+7fdVzj6u52u30Kp6M/trliBMCMKTyK833zpbD+pXdCLuTusPj697FH4R/5mcr" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="icon" href="https://cdn-icons-png.flaticon.com/512/3197/3197967.png">
    <link rel="stylesheet" href="style/styles.css">
</head>
<body>
    <div class="app-container">
    
        <!-- Sidebar -->
        <div class="sidebar" id="sidebar">
        
            <!-- Header del sidebar -->
            <div class="sidebar-header">
                <div class="logo">
                    <ion-icon name="school-outline" size="large"></ion-icon>
                    <span class="logo-text">NoteLift</span>
                </div>
                <button class="sidebar-toggle" id="sidebarToggle">
                    <ion-icon name="menu-outline" size="large"></ion-icon>
                </button>
            </div>

            <!-- Navegación principal -->
			<nav class="sidebar-nav">

    		<!-- Inicio -->
    		<a href="bienvenida.jsp" class="nav-item active" title="Inicio">
        		<ion-icon name="home-outline"></ion-icon>
        		<span class="nav-text">Inicio</span>
    		</a>

    		<!-- Subir material -->
    		<a href="upload" class="nav-item" title="Subir Material">
        		<ion-icon name="cloud-upload-outline"></ion-icon>
        		<span class="nav-text">Subir Material</span>
    		</a>

    		<!-- Mis recursos -->
    		<a href="ListarMisRecursos" class="nav-item" title="Mis Recursos">
        		<ion-icon name="folder-open-outline"></ion-icon>
        		<span class="nav-text">Mis Recursos</span>
    		</a>

    		<!-- Buscar material -->
    		<a href="ListaArchivos" class="nav-item" title="Buscar Material">
        		<ion-icon name="search-outline"></ion-icon>
        		<span class="nav-text">Buscar Material</span>
    		</a>

    		<%
    		Usuario usuarioMenu = (Usuario) session.getAttribute("usuario");
    		boolean esAdmin = usuarioMenu != null && "administrador".equalsIgnoreCase(usuarioMenu.getRol());
    		%>

    		<!-- OPCIONES DE ADMINISTRADOR - Solo visibles para admin -->
    		<% if (esAdmin) { %>
    
    		<!-- Alta Facultades -->
    		<a href="ListaFacultades" class="nav-item" title="Alta Facultades">
        		<ion-icon name="business-outline"></ion-icon>
        		<span class="nav-text">Alta Facultades</span>
    		</a>
    
    		<!-- Alta Carreras -->
    		<a href="ListaCarreras" class="nav-item" title="Alta Carreras">
        		<ion-icon name="duplicate-outline"></ion-icon>
        		<span class="nav-text">Alta Carreras</span>
    		</a>
    
    		<% } %>

    		<!-- Cerrar sesión -->
    		<a href="#" class="nav-item" data-bs-toggle="modal" data-bs-target="#modalCerrarSesion" title="Cerrar Sesión">
        		<ion-icon name="log-out-outline"></ion-icon>
        		<span class="nav-text">Cerrar Sesión</span>
    		</a>
			</nav>

        </div>

        <!-- Contenido principal -->
        <div class="main-content">
        
            
            <!-- Header -->
			<header class="main-header">
    		<h1>Sistema de Gestión de Recursos Académicos Compartidos</h1>
    		<div class="header-actions">
        	<%
            Usuario usuarioHeader = (Usuario) session.getAttribute("usuario");
            boolean esAdministrador = usuarioHeader != null && "administrador".equalsIgnoreCase(usuarioHeader.getRol());
            
            // Obtener solicitudes pendientes si es admin
            int totalPendientes = 0;
            if (esAdministrador) {
                try {
                    data.DataSolicitudMateria dataSolicitud = new data.DataSolicitudMateria();
                    java.util.LinkedList<entities.SolicitudMateria> solicitudesPendientes = dataSolicitud.getSolicitudesPendientes();
                    totalPendientes = solicitudesPendientes != null ? solicitudesPendientes.size() : 0;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        %>
        
        <!-- Solicitudes pendientes - Solo para administradores -->
        <% if (esAdministrador) { %>
        <a href="GestionarSolicitudes">
            <button class="btn btn-outline btn-solicitudes">
                <ion-icon name="notifications-outline"></ion-icon>
                <span>Solicitudes pendientes</span>
                <% if (totalPendientes > 0) { %>
                    <span class="badge-count"><%= totalPendientes %></span>
                <% } %>
            </button>
        </a>
        <% } %>
        
        <!-- Subir material -->
        <a href="upload">
            <button class="btn btn-primary">
                <ion-icon name="cloud-upload-outline"></ion-icon>
                <span>Subir Material</span>                  
            </button>
        </a>
    </div>
</header>

            <!-- Contenido de bienvenida -->
            <main class="content">
                <div class="welcome-container">
                
                    <!-- Bienvenida principal -->
                    <div class="welcome-hero">
                        <div class="welcome-badge">
                            <ion-icon name="school-outline" size="large"></ion-icon>
                            <span>Bienvenidos a NoteLift</span>
                        </div>
                        <h2>Comparte y Accede a Recursos Académicos</h2>
                        <p class="hero-description">
                            Una plataforma colaborativa diseñada para estudiantes universitarios que facilita el 
                            intercambio de materiales de estudio, promoviendo el acceso equitativo al conocimiento.
                        </p>
                    </div>

                    <!-- Características principales -->
                    <div class="features-grid">
                        <a href="upload" class="feature-card-link">
                            <div class="feature-card">
                                <div class="feature-header">
                                    <ion-icon name="cloud-upload-outline" size="large"></ion-icon>
                                    <h3>Sube y Organiza</h3>
                                </div>
                                <p>
                                    Carga apuntes, exámenes, ejercicios resueltos y otros materiales de estudio. 
                                    Organízalos por facultad, materia y año de cursada.
                                </p>
                            </div>
                        </a>

                        <a href="ListaArchivos" class="feature-card-link">
                            <div class="feature-card">
                                <div class="feature-header">
                                    <ion-icon name="search-outline" size="large"></ion-icon>
                                    <h3>Búsqueda Avanzada</h3>
                                </div>
                                <p>
                                    Encuentra rápidamente los recursos que necesitas con filtros por facultad, 
                                    materia, tipo de material y palabras clave específicas.
                                </p>
                            </div>
                        </a>

                        <a href="listarMisRecursos" class="feature-card-link">
                            <div class="feature-card">
                                <div class="feature-header">
                                    <ion-icon name="folder-open-outline" size="large"></ion-icon>
                                    <h3>Mis Aportes Académicos</h3>
                                </div>
                                <p>
                                    Revisa tu historial de subidas. Tienes control total para modificar 
                                    la información o dar de baja cualquier recurso.
                                </p>
                            </div>
                        </a>
                    </div>

                    <!-- Información del sistema -->
                    <div class="info-card">
                        <h3>¿Qué puedes hacer en NoteLift?</h3>
                        <div class="info-grid">
                            <div class="info-section">
                                <h4>Gestión de Recursos:</h4>
                                <ul>
                                    <li>Subir apuntes de clase y materiales de estudio</li>
                                    <li>Compartir exámenes parciales y finales</li>
                                    <li>Organizar ejercicios resueltos por materia</li>
                                    <li>Categorizar por facultad y año de cursada</li>
                                </ul>
                            </div>
                            <div class="info-section">
                                <h4>Búsqueda y Acceso:</h4>
                                <ul>
                                    <li>Filtrar por facultad y materia específica</li>
                                    <li>Buscar por tipo de material y palabras clave</li>
                                    <li>Acceder a recursos compartidos por la comunidad</li>
                                    <li>Recibir notificaciones sobre nuevos materiales</li>
                                </ul>
                            </div>
                        </div>
                    </div>

                    <!-- Call to action -->
                    <div class="cta-section">
                        <h3>¿Listo para comenzar?</h3>
                        <p>
                            Únete a la comunidad de estudiantes que ya están compartiendo y 
                            accediendo a recursos académicos.
                        </p>
                        <div class="cta-buttons">
                            <a href="upload">
                                <button class="btn btn-primary btn-large">
                                    <ion-icon name="cloud-upload-outline"></ion-icon>
                                    <span>Subir mi primer material</span>
                                </button>
                            </a>
                            <a href="ListaArchivos">
                            <button class="btn btn-outline btn-large">
                                <ion-icon name="search-outline"></ion-icon>
                                <span>Explorar recursos</span>
                            </button>
                             </a>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </div>

    <!-- Modal de confirmación de cierre de sesión -->
<div class="modal fade" id="modalCerrarSesion" tabindex="-1" aria-labelledby="modalCerrarSesionLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
                <div class="logout-icon">
                    <ion-icon class="logout" name="log-out-outline"></ion-icon>
                </div>
                <h5 class="modal-title" id="modalCerrarSesionLabel">
                    ¿Estás seguro de que deseas<br>cerrar sesión?
                </h5>
            </div>

            <div class="modal-body">
                <p class="logout-message">
                    Tu sesión actual se cerrará y tendrás que iniciar sesión nuevamente para acceder.
                </p>
                
                <div class="logout-warning">
                    <ion-icon class="info-lo" name="information-circle-outline"></ion-icon>
                    <span>Asegúrate de haber guardado todos tus cambios antes de continuar.</span>
                </div>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                    <ion-icon class="close-ol" name="close-outline"></ion-icon>
                    Cancelar
                </button>
                <a href="Logout" class="btn btn-danger">
                    <ion-icon class="signout" name="log-out-outline"></ion-icon>
                    Cerrar Sesión
                </a>
            </div>
        </div>
    </div>
</div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>    
    <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>	
    <script>
        // Event listener para el botón de toggle del sidebar
        document.addEventListener('DOMContentLoaded', function() {
            const sidebarToggle = document.getElementById('sidebarToggle');
            if (sidebarToggle) {
                sidebarToggle.addEventListener('click', function() {
                    const sidebar = document.getElementById('sidebar');
                    sidebar.classList.toggle('collapsed');
                });
            }
        });
    </script>
</body>
</html>
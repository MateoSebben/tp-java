<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SGRAC - Sistema de Gestión de Recursos Académicos Compartidos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-LN+7fdVzj6u52u30Kp6M/trliBMCMKTyK833zpbD+pXdCLuTusPj697FH4R/5mcr" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="style/styles.css">
    <link rel="stylesheet" href="style/welcome.css">
    <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
	<script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>	
</head>
<body>
    <div class="app-container">
        <!-- Sidebar estilo Notion -->
        <div class="sidebar" id="sidebar">
            <!-- Header del sidebar -->
            <div class="sidebar-header">
                <div class="logo">
                    <ion-icon name="school-outline" size="large"></ion-icon>
                    <span class="logo-text">SGRAC</span>
                </div>
                <button class="sidebar-toggle" id="sidebarToggle">
                    <ion-icon name="menu-outline" size="large"></ion-icon>
                </button>
            </div>

            <!-- Navegación -->
            <nav class="sidebar-nav">
            
                <!-- Inicio -->
                <a href="index.jsp" class="nav-item active">
                    <ion-icon name="home-outline"></ion-icon>
                    <span class="nav-text">Inicio</span>
                </a>

                <!-- Sección Recursos -->
                <div class="nav-section">
                    <button class="nav-section-header" onclick="toggleSection('recursos')">
                        <i class="fas fa-chevron-down section-arrow" id="recursos-arrow"></i>
                       <ion-icon name="book-outline"></ion-icon>
                        <span class="nav-text">Recursos</span>
                    </button>
                    <div class="nav-subsection" id="recursos-section">
                        <a href="upload.jsp" class="nav-subitem">
                            <ion-icon name="cloud-upload-outline"></ion-icon>
                            <span class="nav-text">Subir Material</span>
                        </a>
                        <a href="my-resources.jsp" class="nav-subitem">
                            <ion-icon name="document-text-outline"></ion-icon>
                            <span class="nav-text">Mis Recursos</span>
                        </a>
                        <a href="shared.jsp" class="nav-subitem">
                            <ion-icon name="share-social-outline"></ion-icon>
                            <span class="nav-text">Compartidos</span>
                        </a>
                    </div>
                </div>

                <!-- Sección Búsqueda -->
                <div class="nav-section">
                    <button class="nav-section-header" onclick="toggleSection('busqueda')">
                        <i class="fas fa-chevron-right section-arrow" id="busqueda-arrow"></i>
                        <ion-icon name="search-outline"></ion-icon>
                        <span class="nav-text">Búsqueda</span>
                    </button>
                    <div class="nav-subsection collapsed" id="busqueda-section">
                        <a href="search.jsp" class="nav-subitem">
                            <i class="fas fa-search"></i>
                            <span class="nav-text">Buscar Material</span>
                        </a>
                        <a href="filters.jsp" class="nav-subitem">
                            <i class="fas fa-filter"></i>
                            <span class="nav-text">Filtros Avanzados</span>
                        </a>
                    </div>
                </div>

                <!-- Sección Perfil -->
                <div class="nav-section">
                    <button class="nav-section-header" onclick="toggleSection('perfil')">
                        <i class="fas fa-chevron-right section-arrow" id="perfil-arrow"></i>
                        <ion-icon name="person-outline"></ion-icon>
                        <span class="nav-text">Perfil</span>
                    </button>
                    <div class="nav-subsection collapsed" id="perfil-section">
                        <a href="profile.jsp" class="nav-subitem">
                            <i class="fas fa-user"></i>
                            <span class="nav-text">Mi Perfil</span>
                        </a>
                        <a href="notifications.jsp" class="nav-subitem">
                            <i class="fas fa-bell"></i>
                            <span class="nav-text">Notificaciones</span>
                        </a>
                    </div>
                </div>

                <!-- Configuración -->
                <a href="settings.jsp" class="nav-item">
                    <ion-icon name="settings-outline"></ion-icon>
                    <span class="nav-text">Configuración</span>
                </a>
                
                <!-- Cerrar Sesion -->
                	<a href="#" class="nav-item" data-bs-toggle="modal" data-bs-target="#modalCerrarSesion">
    				<ion-icon name="log-out-outline"></ion-icon>
    				<span class="nav-text-so">Cerrar Sesión</span>
					</a>
            </nav>
        </div>

        <!-- Contenido principal -->
        <div class="main-content">
        
            <!-- Header -->
            <header class="main-header">
                <h1>Sistema de Gestión de Recursos Académicos Compartidos</h1>
                <div class="header-actions">
                    <button class="btn btn-outline">
                        <ion-icon name="notifications-outline"></ion-icon>
                        <span>Notificaciones</span>
                    </button>
                    <button class="btn btn-primary">
                        <ion-icon name="cloud-upload-outline"></ion-icon>
                        <span>Subir Material</span>
                    </button>
                </div>
            </header>

            <!-- Contenido de bienvenida -->
            <main class="content">
                <div class="welcome-container">
                
                    <!-- Bienvenida principal -->
                    <div class="welcome-hero">
                        <div class="welcome-badge">
                            <ion-icon name="school-outline" size="large"></ion-icon>
                            <span>Bienvenido al SGRAC</span>
                        </div>
                        <h2>Comparte y Accede a Recursos Académicos</h2>
                        <p class="hero-description">
                            Una plataforma colaborativa diseñada para estudiantes universitarios que facilita el 
                            intercambio de materiales de estudio, promoviendo el acceso equitativo al conocimiento.
                        </p>
                    </div>

                    <!-- Características principales -->
                    <div class="features-grid">
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

                        <div class="feature-card">
                            <div class="feature-header">
                                <ion-icon name="share-social-outline" size="large"></ion-icon>
                                <h3>Colaboración</h3>
                            </div>
                            <p>
                                Comparte tus materiales con la comunidad estudiantil y accede a recursos 
                                compartidos por otros compañeros de diferentes facultades.
                            </p>
                        </div>
                    </div>

                    <!-- Información del sistema -->
                    <div class="info-card">
                        <h3>¿Qué puedes hacer en SGRAC?</h3>
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
                            <button class="btn btn-primary btn-large">
                                <ion-icon name="cloud-upload-outline"></ion-icon>
                                <span>Subir mi primer material</span>
                            </button>
                            <button class="btn btn-outline btn-large">
                                <ion-icon name="search-outline"></ion-icon>
                                <span>Explorar recursos</span>
                            </button>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </div>

    <!-- ACA -->
    
    <!-- Modal de confirmación de cierre de sesión -->
<div class="modal fade" id="modalCerrarSesion" tabindex="-1" aria-labelledby="modalCerrarSesionLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content rounded-3 shadow">

      <div class="modal-header bg-primary text-white border-0">
        <h5 class="modal-title" id="modalCerrarSesionLabel">¿Estás seguro de que deseas cerrar sesión?</h5>
        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Cerrar"></button>
      </div>

      <div class="modal-body text-dark">
      </div>

      <div class="modal-footer border-0">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
        <a href="Logout" class="btn btn-danger">Cerrar Sesión</a>
      </div>

    </div>
  </div>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>    
</body>
</html>

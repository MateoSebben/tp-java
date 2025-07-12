<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SGRAC - Sistema de Gestión de Recursos Académicos Compartidos</title>
    <link rel="stylesheet" href="style/styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <div class="app-container">
        <!-- Sidebar estilo Notion -->
        <div class="sidebar" id="sidebar">
            <!-- Header del sidebar -->
            <div class="sidebar-header">
                <div class="logo">
                    <i class="fas fa-graduation-cap"></i>
                    <span class="logo-text">SGRAC</span>
                </div>
                <button class="sidebar-toggle" id="sidebarToggle">
                    <i class="fas fa-bars"></i>
                </button>
            </div>

            <!-- Navegación -->
            <nav class="sidebar-nav">
                <!-- Inicio -->
                <a href="index.jsp" class="nav-item active">
                    <i class="fas fa-home"></i>
                    <span class="nav-text">Inicio</span>
                </a>

                <!-- Sección Recursos -->
                <div class="nav-section">
                    <button class="nav-section-header" onclick="toggleSection('recursos')">
                        <i class="fas fa-chevron-down section-arrow" id="recursos-arrow"></i>
                        <i class="fas fa-book-open"></i>
                        <span class="nav-text">Recursos</span>
                    </button>
                    <div class="nav-subsection" id="recursos-section">
                        <a href="upload.jsp" class="nav-subitem">
                            <i class="fas fa-upload"></i>
                            <span class="nav-text">Subir Material</span>
                        </a>
                        <a href="my-resources.jsp" class="nav-subitem">
                            <i class="fas fa-file-text"></i>
                            <span class="nav-text">Mis Recursos</span>
                        </a>
                        <a href="shared.jsp" class="nav-subitem">
                            <i class="fas fa-share-alt"></i>
                            <span class="nav-text">Compartidos</span>
                        </a>
                    </div>
                </div>

                <!-- Sección Búsqueda -->
                <div class="nav-section">
                    <button class="nav-section-header" onclick="toggleSection('busqueda')">
                        <i class="fas fa-chevron-right section-arrow" id="busqueda-arrow"></i>
                        <i class="fas fa-search"></i>
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
                        <i class="fas fa-users"></i>
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
                    <i class="fas fa-cog"></i>
                    <span class="nav-text">Configuración</span>
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
                        <i class="fas fa-bell"></i>
                        <span>Notificaciones</span>
                    </button>
                    <button class="btn btn-primary">
                        <i class="fas fa-upload"></i>
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
                            <i class="fas fa-graduation-cap"></i>
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
                                <i class="fas fa-upload feature-icon upload"></i>
                                <h3>Sube y Organiza</h3>
                            </div>
                            <p>
                                Carga apuntes, exámenes, ejercicios resueltos y otros materiales de estudio. 
                                Organízalos por facultad, materia y año de cursada.
                            </p>
                        </div>

                        <div class="feature-card">
                            <div class="feature-header">
                                <i class="fas fa-search feature-icon search"></i>
                                <h3>Búsqueda Avanzada</h3>
                            </div>
                            <p>
                                Encuentra rápidamente los recursos que necesitas con filtros por facultad, 
                                materia, tipo de material y palabras clave específicas.
                            </p>
                        </div>

                        <div class="feature-card">
                            <div class="feature-header">
                                <i class="fas fa-share-alt feature-icon share"></i>
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
                                <i class="fas fa-upload"></i>
                                <span>Subir mi primer material</span>
                            </button>
                            <button class="btn btn-outline btn-large">
                                <i class="fas fa-search"></i>
                                <span>Explorar recursos</span>
                            </button>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </div>

    <script src="js/main.js"></script>
</body>
</html>

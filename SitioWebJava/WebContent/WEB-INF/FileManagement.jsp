<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.LinkedList" %>
<%@ page import="entities.Archivo" %>
<%@ page import="entities.Carrera" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Archivos Disponibles</title>

    <!-- Estilos -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="icon" href="https://cdn-icons-png.flaticon.com/512/3197/3197967.png">
    <link rel="stylesheet" href="style/filelist.css"> 
</head>
<body>
<main class="main-content">

    <!-- Breadcrumb -->
    <nav aria-label="breadcrumb" class="breadcrumb-nav container">
        <ol class="breadcrumb">
            <li class="breadcrumb-item">
                <a href="bienvenida.jsp">
                    <ion-icon name="home-outline"></ion-icon> Inicio
                </a>
            </li>
            <li class="breadcrumb-item active" aria-current="page">
                <ion-icon name="search-circle-outline"></ion-icon> Buscar Material
            </li>
        </ol>
    </nav>

    <div class="container">
        <!-- Header Section -->
        <div class="page-header">
            <div class="header-content">
                <div class="header-icon">
                    <ion-icon name="document-text-outline"></ion-icon>
                </div>
                <div class="header-text">
                    <h1>Archivos Disponibles</h1>
                    <p>Explora y descarga el material académico compartido por la comunidad</p>
                </div>
            </div>
            
            <%
                LinkedList<Archivo> archivos = (LinkedList<Archivo>) request.getAttribute("archivos");
                int totalArchivos = (archivos != null) ? archivos.size() : 0;
            %>
            
            <div class="results-badge">
                <ion-icon name="documents-outline"></ion-icon>
                <span><strong><%= totalArchivos %></strong> archivo<%= totalArchivos != 1 ? "s" : "" %></span>
            </div>
        </div>

        <!-- Vista Toggle (opcional para futuro) -->
        <div class="view-controls">
            <div class="search-box">
                <ion-icon name="search-outline"></ion-icon>
                <input type="text" id="searchFiles" placeholder="Buscar archivos...">
            </div>
            <div class="sort-options">
                <label>Ordenar por:</label>
                <select id="sortBy">
                    <option value="recent">Más recientes</option>
                    <option value="name">Nombre</option>
                    <option value="size">Tamaño</option>
                </select>
            </div>
        </div>

        <!-- Cards Grid -->
        <div class="files-grid">
            <%
            if (archivos != null && !archivos.isEmpty()) {
                for (Archivo archivo : archivos) {
                    String extension = archivo.getExtension() != null ? archivo.getExtension().toLowerCase() : "";
                    
                    String iconClass = "bi bi-file-earmark"; 
                    String iconStyle = "color: gray;";
                    String cardAccent = "#6c757d";

                    if ("pdf".equals(extension)) {
                        iconClass = "bi bi-file-earmark-pdf";
                        iconStyle = "color: #dc3545;";
                        cardAccent = "#dc3545";
                    } else if ("doc".equals(extension) || "docx".equals(extension)) {
                        iconClass = "bi bi-file-earmark-word";
                        iconStyle = "color: #0d6efd;";
                        cardAccent = "#0d6efd";
                    } else if ("xls".equals(extension) || "xlsx".equals(extension)) {
                        iconClass = "bi bi-file-earmark-excel";
                        iconStyle = "color: #198754;";
                        cardAccent = "#198754";
                    } else if ("ppt".equals(extension) || "pptx".equals(extension)) {
                        iconClass = "bi bi-file-earmark-ppt";
                        iconStyle = "color: #ffc107;";
                        cardAccent = "#ffc107";
                    } else if ("txt".equals(extension)) {
                        iconClass = "bi bi-file-earmark-text";
                        iconStyle = "color: #6c757d;";
                        cardAccent = "#6c757d";
                    } else if ("jpg".equals(extension) || "jpeg".equals(extension) || "png".equals(extension) || "gif".equals(extension)) {
                        iconClass = "bi bi-file-earmark-image";
                        iconStyle = "color: #0dcaf0;";
                        cardAccent = "#0dcaf0";
                    }
                    
                    String descripcion = archivo.getDescripcion() != null && !archivo.getDescripcion().trim().isEmpty() 
                        ? archivo.getDescripcion() 
                        : "Sin descripción disponible";
            %>
            
            <div class="file-card" data-accent="<%= cardAccent %>">
                <!-- Card Header con icono y acciones -->
                <div class="card-header">
                    <div class="file-icon-large">
                        <i class="<%= iconClass %>" style="<%= iconStyle %>"></i>
                    </div>
                    <div class="card-actions">
                        <a href="DescargarArchivo?id=<%= archivo.getIdArchivo() %>" 
                           class="btn-download" 
                           title="Descargar archivo">
                            <ion-icon name="download-outline"></ion-icon>
                        </a>
                    </div>
                </div>

                <!-- Nombre del archivo -->
                <div class="file-title">
                    <h3><%= archivo.getNombre() %></h3>
                    <span class="file-ext">.<%= archivo.getExtension() %></span>
                </div>

                <!-- Tipo y tamaño -->
                <div class="file-meta">
                    <span class="badge badge-<%= archivo.getTipoArchivo().toLowerCase().replace(" ", "-") %>">
                        <%= archivo.getTipoArchivo() %>
                    </span>
                    <span class="file-size">
                        <ion-icon name="cloud-outline"></ion-icon>
                        <%= String.format("%.2f", archivo.getPeso()) %> MB
                    </span>
                </div>

                <!-- Descripción -->
                <div class="file-description">
                    <p><%= descripcion %></p>
                </div>

                <!-- Información de la materia -->
                <div class="subject-section">
                    <div class="subject-header">
                        <ion-icon name="book-outline"></ion-icon>
                        <span class="subject-name"><%= archivo.getMateria().getNombreMateria() %></span>
                    </div>
                    <%
                        if (archivo.getMateria() != null && archivo.getMateria().getCarreras() != null
                                && !archivo.getMateria().getCarreras().isEmpty()) {
                    %>
                    <div class="carreras-tags">
                        <%
                            for (Carrera c : archivo.getMateria().getCarreras()) {
                                if (c != null && c.getNombreCarrera() != null) {
                        %>
                        <span class="carrera-tag"><%= c.getNombreCarrera() %></span>
                        <%
                                }
                            }
                        %>
                    </div>
                    <%
                        }
                    %>
                </div>

                <!-- Footer con usuario y fecha -->
                <div class="card-footer">
                    <div class="user-info">
                        <div class="user-avatar">
                            <ion-icon name="person-outline"></ion-icon>
                        </div>
                        <div class="user-details">
                            <span class="user-name"><%= archivo.getUsuario().getNombre() %> <%= archivo.getUsuario().getApellido() %></span>
                            <span class="upload-date">
                                <ion-icon name="calendar-outline"></ion-icon>
                                <%= archivo.getFechaSubida() != null ? sdf.format(archivo.getFechaSubida()) : "" %>
                            </span>
                        </div>
                    </div>
                </div>
            </div>
            
            <% 
                }
            } else { 
            %>
            
            <!-- Estado vacío -->
            <div class="empty-state">
                <div class="empty-icon">
                    <ion-icon name="folder-open-outline"></ion-icon>
                </div>
                <h3>No se encontraron archivos</h3>
                <p>Aún no hay material disponible en esta sección.</p>
            </div>
            
            <% } %>
        </div>
    </div>
</main>

<!-- Scripts -->
<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>

<script>
    // Búsqueda en tiempo real
    document.getElementById('searchFiles').addEventListener('input', function(e) {
        const searchTerm = e.target.value.toLowerCase();
        const cards = document.querySelectorAll('.file-card');
        
        cards.forEach(card => {
            const text = card.textContent.toLowerCase();
            if (text.includes(searchTerm)) {
                card.style.display = 'block';
            } else {
                card.style.display = 'none';
            }
        });
    });

    // Efecto de acento de color en hover
    document.querySelectorAll('.file-card').forEach(card => {
        const accentColor = card.getAttribute('data-accent');
        card.addEventListener('mouseenter', function() {
            this.style.borderTopColor = accentColor;
        });
        card.addEventListener('mouseleave', function() {
            this.style.borderTopColor = 'transparent';
        });
    });
</script>
</body>
</html>
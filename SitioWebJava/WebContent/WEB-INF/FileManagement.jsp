<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="entities.Archivo" %>
<%@ page import="entities.Carrera" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    // Obtener listas de filtros
    Set<String> tiposUnicos = (Set<String>) request.getAttribute("tiposUnicos");
    Set<String> materiasUnicas = (Set<String>) request.getAttribute("materiasUnicas");
    Set<String> carrerasUnicas = (Set<String>) request.getAttribute("carrerasUnicas");
    Set<String> extensionesUnicas = (Set<String>) request.getAttribute("extensionesUnicas");
    
    // Obtener filtros actuales
    String filtroTipo = (String) request.getAttribute("filtroTipo");
    String filtroMateria = (String) request.getAttribute("filtroMateria");
    String filtroCarrera = (String) request.getAttribute("filtroCarrera");
    String filtroExtension = (String) request.getAttribute("filtroExtension");
    String filtroFechaDesde = (String) request.getAttribute("filtroFechaDesde");
    String filtroFechaHasta = (String) request.getAttribute("filtroFechaHasta");
    String filtroBusqueda = (String) request.getAttribute("filtroBusqueda");
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
                List<Archivo> archivos = (List<Archivo>) request.getAttribute("archivos");
                int totalArchivos = (archivos != null) ? archivos.size() : 0;
            %>
            
            <div class="results-badge">
                <ion-icon name="documents-outline"></ion-icon>
                <span><strong><%= totalArchivos %></strong> archivo<%= totalArchivos != 1 ? "s" : "" %></span>
            </div>
        </div>

        <!-- Sistema de Filtros -->
        <div class="filters-container">
            <div class="filters-header">
                <div class="filters-title">
                    <ion-icon name="options-outline"></ion-icon>
                    Filtros de Búsqueda
                </div>
                <button class="filters-toggle" onclick="toggleFilters()">
                    <ion-icon name="funnel-outline"></ion-icon>
                    <span id="toggleText">Ocultar</span>
                </button>
            </div>
            
            <form id="filterForm" method="get" action="ListaArchivos">
                <div id="filtersContent">
                    <div class="filters-grid">
                        <!-- Filtro por Tipo de Archivo -->
                        <div class="filter-group">
                            <label class="filter-label">
                                <ion-icon name="document-outline"></ion-icon>
                                Tipo de Material
                            </label>
                            <select name="tipoArchivo" class="filter-select">
                                <option value="todos" <%= (filtroTipo == null || filtroTipo.equals("todos")) ? "selected" : "" %>>Todos los tipos</option>
                                <%
                                    if (tiposUnicos != null) {
                                        for (String tipo : tiposUnicos) {
                                %>
                                <option value="<%= tipo %>" <%= (filtroTipo != null && filtroTipo.equals(tipo)) ? "selected" : "" %>><%= tipo %></option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                        </div>
                        
                        <!-- Filtro por Materia -->
                        <div class="filter-group">
                            <label class="filter-label">
                                <ion-icon name="book-outline"></ion-icon>
                                Materia
                            </label>
                            <select name="materia" class="filter-select">
                                <option value="todas" <%= (filtroMateria == null || filtroMateria.equals("todas")) ? "selected" : "" %>>Todas las materias</option>
                                <%
                                    if (materiasUnicas != null) {
                                        for (String materia : materiasUnicas) {
                                %>
                                <option value="<%= materia %>" <%= (filtroMateria != null && filtroMateria.equals(materia)) ? "selected" : "" %>><%= materia %></option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                        </div>
                        
                        <!-- Filtro por Carrera -->
                        <div class="filter-group">
                            <label class="filter-label">
                                <ion-icon name="school-outline"></ion-icon>
                                Carrera
                            </label>
                            <select name="carrera" class="filter-select">
                                <option value="todas" <%= (filtroCarrera == null || filtroCarrera.equals("todas")) ? "selected" : "" %>>Todas las carreras</option>
                                <%
                                    if (carrerasUnicas != null) {
                                        for (String carrera : carrerasUnicas) {
                                %>
                                <option value="<%= carrera %>" <%= (filtroCarrera != null && filtroCarrera.equals(carrera)) ? "selected" : "" %>><%= carrera %></option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                        </div>
                        
                        <!-- Filtro por Extensión -->
                        <div class="filter-group">
                            <label class="filter-label">
                                <ion-icon name="code-outline"></ion-icon>
                                Formato
                            </label>
                            <select name="extension" class="filter-select">
                                <option value="todas" <%= (filtroExtension == null || filtroExtension.equals("todas")) ? "selected" : "" %>>Todos los formatos</option>
                                <%
                                    if (extensionesUnicas != null) {
                                        for (String ext : extensionesUnicas) {
                                %>
                                <option value="<%= ext %>" <%= (filtroExtension != null && filtroExtension.equalsIgnoreCase(ext)) ? "selected" : "" %>><%= ext %></option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                        </div>
                        
                        <!-- Filtro por Fecha Desde -->
                        <div class="filter-group">
                            <label class="filter-label">
                                <ion-icon name="calendar-outline"></ion-icon>
                                Desde
                            </label>
                            <input type="date" name="fechaDesde" class="filter-input" 
                                   value="<%= filtroFechaDesde != null ? filtroFechaDesde : "" %>">
                        </div>
                        
                        <!-- Filtro por Fecha Hasta -->
                        <div class="filter-group">
                            <label class="filter-label">
                                <ion-icon name="calendar-outline"></ion-icon>
                                Hasta
                            </label>
                            <input type="date" name="fechaHasta" class="filter-input"
                                   value="<%= filtroFechaHasta != null ? filtroFechaHasta : "" %>">
                        </div>
                    </div>
                    
                    <!-- Acciones de filtro -->
                    <div class="filter-actions">
                        <button type="button" class="btn-filter btn-clear" onclick="limpiarFiltros()">
                            <ion-icon name="close-circle-outline"></ion-icon>
                            Limpiar Filtros
                        </button>
                        <button type="submit" class="btn-filter btn-apply">
                            <ion-icon name="checkmark-circle-outline"></ion-icon>
                            Aplicar Filtros
                        </button>
                    </div>
                </div>
                
                <!-- Campo de búsqueda oculto para mantener el valor -->
                <input type="hidden" name="busqueda" id="hiddenBusqueda" value="<%= filtroBusqueda != null ? filtroBusqueda : "" %>">
            </form>
            
            <!-- Filtros activos -->
            <%
                boolean hayFiltrosActivos = false;
                if ((filtroTipo != null && !filtroTipo.equals("todos")) ||
                    (filtroMateria != null && !filtroMateria.equals("todas")) ||
                    (filtroCarrera != null && !filtroCarrera.equals("todas")) ||
                    (filtroExtension != null && !filtroExtension.equals("todas")) ||
                    (filtroFechaDesde != null && !filtroFechaDesde.isEmpty()) ||
                    (filtroFechaHasta != null && !filtroFechaHasta.isEmpty()) ||
                    (filtroBusqueda != null && !filtroBusqueda.isEmpty())) {
                    hayFiltrosActivos = true;
                }
                
                if (hayFiltrosActivos) {
            %>
            <div class="active-filters">
                <% if (filtroTipo != null && !filtroTipo.equals("todos")) { %>
                <span class="filter-chip">
                    Tipo: <%= filtroTipo %>
                    <ion-icon name="close-circle" onclick="removeFilter('tipoArchivo')"></ion-icon>
                </span>
                <% } %>
                
                <% if (filtroMateria != null && !filtroMateria.equals("todas")) { %>
                <span class="filter-chip">
                    Materia: <%= filtroMateria %>
                    <ion-icon name="close-circle" onclick="removeFilter('materia')"></ion-icon>
                </span>
                <% } %>
                
                <% if (filtroCarrera != null && !filtroCarrera.equals("todas")) { %>
                <span class="filter-chip">
                    Carrera: <%= filtroCarrera %>
                    <ion-icon name="close-circle" onclick="removeFilter('carrera')"></ion-icon>
                </span>
                <% } %>
                
                <% if (filtroExtension != null && !filtroExtension.equals("todas")) { %>
                <span class="filter-chip">
                    Formato: <%= filtroExtension %>
                    <ion-icon name="close-circle" onclick="removeFilter('extension')"></ion-icon>
                </span>
                <% } %>
                
                <% if (filtroFechaDesde != null && !filtroFechaDesde.isEmpty()) { %>
                <span class="filter-chip">
                    Desde: <%= filtroFechaDesde %>
                    <ion-icon name="close-circle" onclick="removeFilter('fechaDesde')"></ion-icon>
                </span>
                <% } %>
                
                <% if (filtroFechaHasta != null && !filtroFechaHasta.isEmpty()) { %>
                <span class="filter-chip">
                    Hasta: <%= filtroFechaHasta %>
                    <ion-icon name="close-circle" onclick="removeFilter('fechaHasta')"></ion-icon>
                </span>
                <% } %>
                
                <% if (filtroBusqueda != null && !filtroBusqueda.isEmpty()) { %>
                <span class="filter-chip">
                    Búsqueda: "<%= filtroBusqueda %>"
                    <ion-icon name="close-circle" onclick="removeFilter('busqueda')"></ion-icon>
                </span>
                <% } %>
            </div>
            <% } %>
        </div>

        <!-- Barra de búsqueda y ordenamiento -->
        <div class="view-controls">
            <div class="search-box">
                <ion-icon name="search-outline"></ion-icon>
                <input type="text" id="searchFiles" placeholder="Buscar archivos..." 
                       value="<%= filtroBusqueda != null ? filtroBusqueda : "" %>">
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

                <!-- Tipo, tamaño y año -->
				<div class="file-meta">
    				<span class="badge badge-<%= archivo.getTipoArchivo().toLowerCase().replace(" ", "-") %>">
        				<%= archivo.getTipoArchivo() %>
    				</span>
    				<span class="file-size">
        				<ion-icon name="cloud-outline"></ion-icon>
        				<%= String.format("%.2f", archivo.getPeso()) %> MB
    				</span>
    					<% if (archivo.getAnioCursada() != null) { %>
    				<span class="year-badge">
        				<ion-icon name="calendar-outline"></ion-icon>
        				Año de cursada <%= archivo.getAnioCursada() %>
    				</span>
    					<% } %>
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
                <p>Intenta ajustar los filtros de búsqueda para encontrar más resultados.</p>
            </div>
            
            <% } %>
        </div>
    </div>
</main>

<!-- Iconos -->
<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>

<script>
    // Toggle de filtros
    function toggleFilters() {
        const content = document.getElementById('filtersContent');
        const toggleText = document.getElementById('toggleText');
        
        if (content.classList.contains('filters-collapsed')) {
            content.classList.remove('filters-collapsed');
            toggleText.textContent = 'Ocultar';
        } else {
            content.classList.add('filters-collapsed');
            toggleText.textContent = 'Mostrar';
        }
    }
    
    // Limpiar todos los filtros
    function limpiarFiltros() {
        window.location.href = 'ListaArchivos';
    }
    
    // Remover un filtro específico
    function removeFilter(filterName) {
        const form = document.getElementById('filterForm');
        const input = form.querySelector(`[name="${filterName}"]`);
        
        if (input.tagName === 'SELECT') {
            if (filterName === 'tipoArchivo' || filterName === 'extension') {
                input.value = 'todos';
            } else {
                input.value = 'todas';
            }
        } else {
            input.value = '';
        }
        
        form.submit();
    }
    
    // Búsqueda en tiempo real con actualización del formulario
    document.getElementById('searchFiles').addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            e.preventDefault();
            const searchTerm = this.value;
            document.getElementById('hiddenBusqueda').value = searchTerm;
            document.getElementById('filterForm').submit();
        }
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
    
    // Ordenamiento local
    document.getElementById('sortBy').addEventListener('change', function() {
        const sortValue = this.value;
        const grid = document.querySelector('.files-grid');
        const cards = Array.from(document.querySelectorAll('.file-card'));
        
        cards.sort((a, b) => {
            if (sortValue === 'name') {
                const nameA = a.querySelector('.file-title h3').textContent.toLowerCase();
                const nameB = b.querySelector('.file-title h3').textContent.toLowerCase();
                return nameA.localeCompare(nameB);
            } else if (sortValue === 'size') {
                const sizeA = parseFloat(a.querySelector('.file-size').textContent);
                const sizeB = parseFloat(b.querySelector('.file-size').textContent);
                return sizeB - sizeA;
            } else { // recent
                const dateA = a.querySelector('.upload-date').textContent.trim();
                const dateB = b.querySelector('.upload-date').textContent.trim();
                return dateB.localeCompare(dateA);
            }
        });
        
        cards.forEach(card => grid.appendChild(card));
    });
</script>
</body>
</html>
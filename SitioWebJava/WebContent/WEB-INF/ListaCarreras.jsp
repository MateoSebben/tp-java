<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="java.util.Set" %>
<%
    @SuppressWarnings("unchecked")
    LinkedList<String[]> carreras = (LinkedList<String[]>) request.getAttribute("carreras");
    
    @SuppressWarnings("unchecked")
    Set<String> facultadesUnicas = (Set<String>) request.getAttribute("facultadesUnicas");
    
    String filtroFacultad = (String) request.getAttribute("filtroFacultad");
    String filtroBusqueda = (String) request.getAttribute("filtroBusqueda");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Carreras</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="icon" href="https://cdn-icons-png.flaticon.com/512/3197/3197967.png">
    <link rel="stylesheet" href="style/listaCarreras.css">
</head>
<body>
    <div class="main-container">
    
        <!-- Breadcrumb -->
        <nav aria-label="breadcrumb" class="breadcrumb-nav">
            <ol class="breadcrumb">
                <li class="breadcrumb-item">
                    <a href="bienvenida.jsp">
                        <ion-icon name="home-outline"></ion-icon> Inicio
                    </a>
                </li>
                <li class="breadcrumb-item active" aria-current="page">
                    <ion-icon name="duplicate-outline"></ion-icon> Alta Carreras
                </li>
            </ol>
        </nav>

        <!-- Header -->
        <div class="page-header">
            <div class="header-content">
                <div class="header-icon">
                    <ion-icon name="school-outline"></ion-icon>
                </div>
                <div class="header-text">
                    <h1>Gestión de Carreras</h1>
                    <p>Administra las carreras del sistema</p>
                </div>
            </div>
            <a href="agregarCarrera" class="btn-primary">
                <ion-icon name="add-circle-outline"></ion-icon>
                <span>Agregar Carrera</span>
            </a>
        </div>

        <!-- Sistema de Filtros -->
        <div class="filters-container">
            <div class="filters-header">
                <div class="filters-title">
                    <ion-icon name="options-outline"></ion-icon>
                    Filtros de Búsqueda
                </div>
            </div>
            
            <form id="filterForm" method="get" action="ListaCarreras">
                <div class="filters-content">
                    <div class="filters-row">
                        <!-- Buscador de texto -->
                        <div class="filter-group search-group">
                            <label class="filter-label">
                                <ion-icon name="search-outline"></ion-icon>
                                Buscar Carrera
                            </label>
                            <input type="text" 
                                   name="busqueda" 
                                   class="filter-input" 
                                   placeholder="Ej: Ingeniería, Sistemas, Civil..."
                                   value="<%= filtroBusqueda != null ? filtroBusqueda : "" %>">
                            <small class="filter-hint">Busca por nombre de carrera o facultad</small>
                        </div>
                        
                        <!-- Filtro por Facultad -->
                        <div class="filter-group">
                            <label class="filter-label">
                                <ion-icon name="business-outline"></ion-icon>
                                Facultad
                            </label>
                            <select name="facultad" class="filter-select">
                                <option value="todas" <%= (filtroFacultad == null || filtroFacultad.equals("todas")) ? "selected" : "" %>>
                                    Todas las facultades
                                </option>
                                <%
                                    if (facultadesUnicas != null) {
                                        for (String facultad : facultadesUnicas) {
                                %>
                                <option value="<%= facultad %>" <%= (filtroFacultad != null && filtroFacultad.equals(facultad)) ? "selected" : "" %>>
                                    <%= facultad %>
                                </option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                        </div>
                    </div>
                    
                    <!-- Botones de acción -->
                    <div class="filter-actions">
                        <button type="button" class="btn-filter btn-clear" onclick="limpiarFiltros()">
                            <ion-icon name="close-circle-outline"></ion-icon>
                            Limpiar
                        </button>
                        <button type="submit" class="btn-filter btn-apply">
                            <ion-icon name="search-outline"></ion-icon>
                            Buscar
                        </button>
                    </div>
                </div>
            </form>
            
            <!-- Filtros activos -->
            <%
                boolean hayFiltrosActivos = false;
                if ((filtroFacultad != null && !filtroFacultad.equals("todas")) ||
                    (filtroBusqueda != null && !filtroBusqueda.isEmpty())) {
                    hayFiltrosActivos = true;
                }
                
                if (hayFiltrosActivos) {
            %>
            <div class="active-filters">
                <% if (filtroFacultad != null && !filtroFacultad.equals("todas")) { %>
                <span class="filter-chip">
                    Facultad: <%= filtroFacultad %>
                    <ion-icon name="close-circle" onclick="removeFilter('facultad')"></ion-icon>
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

        <%
            if (carreras == null || carreras.isEmpty()) {
        %>
            <!-- Estado vacío -->
            <div class="empty-state">
                <div class="empty-icon">
                    <ion-icon name="search-outline"></ion-icon>
                </div>
                <% if (hayFiltrosActivos) { %>
                    <h3>No se encontraron carreras</h3>
                    <p>No hay carreras que coincidan con los filtros aplicados</p>
                    <button class="btn-primary" onclick="limpiarFiltros()">
                        <ion-icon name="close-circle-outline"></ion-icon>
                        <span>Limpiar Filtros</span>
                    </button>
                <% } else { %>
                    <h3>No hay carreras registradas</h3>
                    <p>Comienza agregando tu primera carrera al sistema</p>
                    <a href="agregarCarrera" class="btn-primary">
                        <ion-icon name="add-circle-outline"></ion-icon>
                        <span>Agregar Primera Carrera</span>
                    </a>
                <% } %>
            </div>
        <%
            } else {
        %>
            <!-- Stats -->
            <div class="stats-bar">
                <ion-icon name="school-outline"></ion-icon>
                <span><strong><%= carreras.size() %></strong> Carrera<%= carreras.size() != 1 ? "s" : "" %> registrada<%= carreras.size() != 1 ? "s" : "" %></span>
            </div>

            <!-- Cards Grid -->
            <div class="cards-grid">
                <%
                    int delay = 0;
                    for (String[] carrera : carreras) {
                        String idCarrera = carrera[0];
                        String nombreCarrera = carrera[1];
                        String nombreFacultad = carrera[2];
                %>
                    <div class="carrera-card" style="animation-delay: <%= delay %>ms;">
                        <div class="card-header">
                            <div class="card-id">#<%= idCarrera %></div>
                            <div class="card-icon">
                                <ion-icon name="school"></ion-icon>
                            </div>
                        </div>
                        
                        <h3 class="card-title"><%= nombreCarrera %></h3>
                        
                        <div class="card-faculty">
                            <ion-icon name="business"></ion-icon>
                            <span><%= nombreFacultad %></span>
                        </div>

                        <!-- Acciones -->
                        <div class="card-actions">
                            <a href="editarCarrera?id=<%= idCarrera %>" class="btn-action btn-edit">
                                <ion-icon name="create-outline"></ion-icon>
                                <span>Editar</span>
                            </a>
                            <button class="btn-action btn-delete" onclick="confirmarEliminacion(<%= idCarrera %>, '<%= nombreCarrera.replace("'", "\\'") %>')">
                                <ion-icon name="trash-outline"></ion-icon>
                                <span>Eliminar</span>
                            </button>
                        </div>
                    </div>
                <%
                        delay += 100;
                    }
                %>
            </div>
        <%
            }
        %>
    </div>

    <!-- Modal de confirmación de eliminación -->
    <div class="modal-overlay" id="modalEliminar">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-icon">
                    <ion-icon name="warning-outline"></ion-icon>
                </div>
                <h3>¿Eliminar carrera?</h3>
            </div>
            <div class="modal-body">
                <p>¿Estás seguro de que deseas eliminar la carrera?</p>
                <p><strong id="nombreCarreraEliminar"></strong></p>
                <p style="color: #dc2626; font-weight: 600; margin-top: 1rem;">Esta acción no se puede deshacer.</p>
            </div>
            <div class="modal-footer">
                <button class="btn-modal btn-cancel" onclick="cerrarModal()">Cancelar</button>
                <form id="formEliminar" method="post" action="eliminarCarrera" style="flex: 1; margin: 0;">
                    <input type="hidden" name="idCarrera" id="idCarreraEliminar">
                    <button type="submit" class="btn-modal btn-confirm-delete" style="width: 100%;">
                        Eliminar
                    </button>
                </form>
            </div>
        </div>
    </div>

    <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
    
    <script>
        function confirmarEliminacion(idCarrera, nombreCarrera) {
            document.getElementById('idCarreraEliminar').value = idCarrera;
            document.getElementById('nombreCarreraEliminar').textContent = nombreCarrera;
            document.getElementById('modalEliminar').classList.add('active');
        }

        function cerrarModal() {
            document.getElementById('modalEliminar').classList.remove('active');
        }

        // Cerrar modal al hacer click fuera
        document.getElementById('modalEliminar').addEventListener('click', function(e) {
            if (e.target === this) {
                cerrarModal();
            }
        });
        
        // Limpiar todos los filtros
        function limpiarFiltros() {
            window.location.href = 'ListaCarreras';
        }
        
        // Remover un filtro específico
        function removeFilter(filterName) {
            const form = document.getElementById('filterForm');
            const input = form.querySelector(`[name="${filterName}"]`);
            
            if (input.tagName === 'SELECT') {
                input.value = 'todas';
            } else {
                input.value = '';
            }
            
            form.submit();
        }
        
    </script>
</body>
</html>
<%@page import="entities.Usuario"%>
<%@page import="java.util.LinkedList"%>
<%@page import="entities.Facultad"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Sistema de Gestión de Facultades">
    <meta name="author" content="">
    <title>Gestión de Facultades</title>
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="icon" href="https://cdn-icons-png.flaticon.com/512/3197/3197967.png">
    <link rel="stylesheet" href="style/university-management.css">
    
    <%
        Usuario u = (Usuario)session.getAttribute("usuario");
        LinkedList<Facultad> lf = (LinkedList<Facultad>)request.getAttribute("listaFacultades");
        int totalFacultades = (lf != null) ? lf.size() : 0;
    %>
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
                        <ion-icon name="business-outline"></ion-icon> Alta Facultades
                </li>
            </ol>
        </nav>
    
        <!-- Header -->
        <div class="header-section">
            <div class="header-content">
                <div class="header-title">
                    <ion-icon class="business" name="business"></ion-icon>
                    <h1>Gestión de Facultades</h1>
                </div>
                <form action="agregarFacultad" method="get">
                    <input type="hidden" name="botonAgregar" />
                    <button type="submit" class="btn-agregar">
                        <ion-icon class="add-circle" name="add-circle"></ion-icon>
                        <span>Nueva Facultad</span>
                    </button>
                </form>
            </div>
            
            <!-- Barra de búsqueda y controles -->
            <div class="controls-bar">
                <div class="search-box">
                    <i class="fas fa-search"></i>
                    <input type="text" id="searchInput" placeholder="Buscar facultades por nombre, dirección o email...">
                </div>
                <div class="view-toggle">
                    <button class="view-btn active" onclick="changeView('grid')">
                        <i class="fas fa-th"></i>
                    </button>
                    <button class="view-btn" onclick="changeView('list')">
                        <i class="fas fa-list"></i>
                    </button>
                </div>
            </div>
        </div>
        
        <!-- Facultades Grid -->
        <% if (lf != null && !lf.isEmpty()) { %>
            <div class="facultades-grid" id="facultadesContainer">
                <% for (Facultad fac : lf) { %>
                    <div class="facultad-card" data-facultad-info="<%= fac.getNombre().toLowerCase() %> <%= fac.getDireccion().toLowerCase() %> <%= fac.getEmailContacto().toLowerCase() %>">
                        <div class="card-header-section">
                            <div class="card-id"><%= fac.getId() %></div>
                            <div class="card-actions">
                                <form action="editarFacultad" method="get" style="display: inline;">
                                    <input type="hidden" name="id" value="<%= fac.getId() %>" />
                                    <button type="submit" class="btn-action btn-editar">
                                        <ion-icon class="edit" name="create-outline"></ion-icon>
                                        Editar
                                    </button>
                                </form>
                                <button type="button" class="btn-action btn-borrar" 
                                        onclick="confirmarEliminacion(<%= fac.getId() %>, '<%= fac.getNombre().replace("'", "\\'") %>')">
                                    <ion-icon class="delete" name="trash"></ion-icon>
                                    Borrar
                                </button>
                            </div>
                        </div>
                        
                        <h2 class="facultad-nombre"><%= fac.getNombre() %></h2>
                        
                        <div class="info-row">
                            <div class="info-icon">
                                <ion-icon class="icono-fac" name="location-sharp"></ion-icon>
                            </div>
                            <div class="info-content">
                                <div class="info-label">Dirección</div>
                                <div class="info-value"><%= fac.getDireccion() %></div>
                            </div>
                        </div>
                        
                        <div class="info-row">
                            <div class="info-icon">
                                <ion-icon class="icono-fac" name="mail-sharp"></ion-icon>
                            </div>
                            <div class="info-content">
                                <div class="info-label">Email de Contacto</div>
                                <div class="info-value"><%= fac.getEmailContacto() %></div>
                            </div>
                        </div>
                        
                        <div class="info-row">
                            <div class="info-icon">
                                <ion-icon class="icono-fac" name="call"></ion-icon>
                            </div>
                            <div class="info-content">
                                <div class="info-label">Teléfono</div>
                                <div class="info-value"><%= fac.getTelefono() %></div>
                            </div>
                        </div>
                        
                        <div class="info-row">
                            <div class="info-icon">
                                <ion-icon class="icono-fac" name="calendar-number"></ion-icon>
                            </div>
                            <div class="info-content">
                                <div class="info-label">Fecha de Alta</div>
                                <div class="info-value"><%= fac.getFechaAlta() %></div>
                            </div>
                        </div>
                    </div>
                <% } %>
            </div>
        <% } else { %>
            <div class="empty-state">
                <i class="fas fa-university"></i>
                <h3>No hay facultades registradas</h3>
                <p>Comienza agregando tu primera facultad al sistema</p>
            </div>
        <% } %>
    </div>
    
    <!-- Modal de confirmación de eliminación -->
    <div class="modal fade" id="modalEliminar" tabindex="-1" aria-labelledby="modalEliminarLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Cerrar"></button>
                    <div class="delete-icon">
                        <i class="fas fa-exclamation-triangle"></i>
                    </div>
                    <h5 class="modal-title" id="modalEliminarLabel">
                        ¿Eliminar Facultad?
                    </h5>
                </div>
                <div class="modal-body">
                    <p class="delete-message">
                        ¿Estás seguro de que deseas eliminar <strong id="nombreFacultad"></strong>?
                    </p>
                    <div class="delete-warning">
                        <i class="fas fa-info-circle"></i>
                        <span>Esta acción no se puede deshacer. Se eliminarán todos los datos asociados.</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                        <i class="fas fa-times"></i>
                        Cancelar
                    </button>
                    <form id="formEliminar" action="eliminarFacultad" method="post" style="display: inline; margin: 0;">
                        <input type="hidden" name="idFac" id="idFacultadEliminar" />
                        <button type="submit" class="btn btn-danger">
                            <i class="fas fa-trash-alt"></i>
                            Eliminar
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>	
    <script>
        // Búsqueda en tiempo real
        document.getElementById('searchInput').addEventListener('input', function(e) {
            const searchTerm = e.target.value.toLowerCase();
            const cards = document.querySelectorAll('.facultad-card');
            let visibleCount = 0;
            
            cards.forEach(card => {
                const facultadInfo = card.getAttribute('data-facultad-info');
                if (facultadInfo.includes(searchTerm)) {
                    card.style.display = 'block';
                    visibleCount++;
                } else {
                    card.style.display = 'none';
                }
            });
            
            // Mostrar mensaje si no hay resultados
            const container = document.getElementById('facultadesContainer');
            let noResults = document.getElementById('noResultsMessage');
            
            if (visibleCount === 0 && searchTerm !== '') {
                if (!noResults) {
                    noResults = document.createElement('div');
                    noResults.id = 'noResultsMessage';
                    noResults.className = 'empty-state';
                    noResults.innerHTML = `
                        <i class="fas fa-search"></i>
                        <h3>No se encontraron resultados</h3>
                        <p>Intenta con otros términos de búsqueda</p>
                    `;
                    container.parentNode.appendChild(noResults);
                }
                container.style.display = 'none';
            } else {
                if (noResults) {
                    noResults.remove();
                }
                container.style.display = 'grid';
            }
        });
        
        // Cambiar vista
        function changeView(view) {
            const container = document.getElementById('facultadesContainer');
            const buttons = document.querySelectorAll('.view-btn');
            
            buttons.forEach(btn => btn.classList.remove('active'));
            event.target.closest('.view-btn').classList.add('active');
            
            if (view === 'list') {
                container.style.gridTemplateColumns = '1fr';
            } else {
                container.style.gridTemplateColumns = 'repeat(auto-fill, minmax(480px, 1fr))';
            }
        }
        
        // Confirmación de eliminación
        function confirmarEliminacion(id, nombre) {
            document.getElementById('nombreFacultad').textContent = nombre;
            document.getElementById('idFacultadEliminar').value = id;
            const modal = new bootstrap.Modal(document.getElementById('modalEliminar'));
            modal.show();
        }
    </script>
</body>
</html>
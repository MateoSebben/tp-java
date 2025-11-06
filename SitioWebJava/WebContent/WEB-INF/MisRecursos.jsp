<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.LinkedList" %>
<%@ page import="entities.Archivo" %>
<%@ page import="entities.Carrera" %>
<%@ page import="entities.Usuario" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mis Recursos - SGRAC</title>

    <!-- Estilos -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="icon" href="https://cdn-icons-png.flaticon.com/512/3197/3197967.png">
    <link rel="stylesheet" href="style/misRecursos.css">
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
                <ion-icon name="folder-open-outline"></ion-icon> Mis Recursos
            </li>
        </ol>
    </nav>

    <!-- Contenedor principal -->
    <div class="page-header">
        <div class="header-content-left">
            <div class="header-icon">
                <ion-icon name="document-text-outline"></ion-icon>
            </div>
            <div class="header-text">
                <h1>Mis Recursos</h1>
                <p>Administra tus materiales académicos compartidos</p>
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

    <!-- Tabla de recursos -->
    <div class="files-section">
        <% if (archivos != null && !archivos.isEmpty()) { %>
        
        <div class="table-responsive">
            <table class="files-table">
                <thead>
                    <tr>
                        <th width="25%">Archivo</th>
                        <th width="8%">Tipo</th>
                        <th width="7%">Tamaño</th>
                        <th width="20%">Descripción</th>
                        <th width="15%">Materia/Carrera</th>
                        <th width="10%">Fecha</th>
                        <th width="15%">Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    for (Archivo archivo : archivos) {
                        String extension = archivo.getExtension() != null ? archivo.getExtension().toLowerCase() : "";
                        
                        // Determinar ícono según extensión
                        String iconClass = "bi bi-file-earmark"; 
                        String iconStyle = "color: #6c757d;";

                        if ("pdf".equals(extension)) {
                            iconClass = "bi bi-file-earmark-pdf";
                            iconStyle = "color: #dc3545;";
                        } else if ("doc".equals(extension) || "docx".equals(extension)) {
                            iconClass = "bi bi-file-earmark-word";
                            iconStyle = "color: #0d6efd;";
                        } else if ("xls".equals(extension) || "xlsx".equals(extension)) {
                            iconClass = "bi bi-file-earmark-excel";
                            iconStyle = "color: #198754;";
                        } else if ("ppt".equals(extension) || "pptx".equals(extension)) {
                            iconClass = "bi bi-file-earmark-ppt";
                            iconStyle = "color: #ffc107;";
                        } else if ("txt".equals(extension)) {
                            iconClass = "bi bi-file-earmark-text";
                            iconStyle = "color: #6c757d;";
                        } else if ("jpg".equals(extension) || "jpeg".equals(extension) || 
                                   "png".equals(extension) || "gif".equals(extension)) {
                            iconClass = "bi bi-file-earmark-image";
                            iconStyle = "color: #0dcaf0;";
                        }
                        
                        String descripcion = archivo.getDescripcion() != null && !archivo.getDescripcion().trim().isEmpty() 
                            ? archivo.getDescripcion() 
                            : "Sin descripción";
                    %>
                    <tr>
                        <!-- Columna: Archivo -->
                        <td>
                            <div class="file-info">
                                <div class="file-icon">
                                    <i class="<%= iconClass %>" style="<%= iconStyle %>"></i>
                                </div>
                                <div class="file-details">
                                    <span class="file-name" title="<%= archivo.getNombre() %>">
                                        <%= archivo.getNombre() %>
                                    </span>
                                    <span class="file-extension">.<%= archivo.getExtension() %></span>
                                </div>
                            </div>
                        </td>
                        
                        <!-- Columna: Tipo -->
                        <td>
                            <span class="badge badge-<%= archivo.getTipoArchivo().toLowerCase().replace(" ", "-") %>">
                                <%= archivo.getTipoArchivo() %>
                            </span>
                        </td>
                        
                        <!-- Columna: Tamaño -->
                        <td class="text-center">
                            <span class="file-size">
                                <ion-icon name="cloud-outline"></ion-icon>
                                <%= String.format("%.2f", archivo.getPeso()) %> MB
                            </span>
                        </td>
                        
                        <!-- Columna: Descripción -->
                        <td>
                            <div class="description-cell" title="<%= descripcion %>">
                                <%= descripcion %>
                            </div>
                        </td>
                        
                        <!-- Columna: Materia -->
                        <td>
                            <div class="subject-cell">
                                <div class="subject-name">
                                    <ion-icon name="book-outline"></ion-icon>
                                    <%= archivo.getMateria().getNombreMateria() %>
                                </div>
                                <%
                                    if (archivo.getMateria() != null && 
                                        archivo.getMateria().getCarreras() != null &&
                                        !archivo.getMateria().getCarreras().isEmpty()) {
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
                                <% } %>
                            </div>
                        </td>
                        
                        <!-- Columna: Fecha -->
                        <td class="text-center">
                            <div class="date-cell">
                                <ion-icon name="calendar-outline"></ion-icon>
                                <span><%= archivo.getFechaSubida() != null ? sdf.format(archivo.getFechaSubida()) : "-" %></span>
                            </div>
                        </td>
                        
                        <!-- Columna: Acciones -->
                        <td>
                            <div class="actions">
                                <a href="EditarArchivo?id=<%= archivo.getIdArchivo() %>" 
                                   class="btn-action btn-edit" 
                                   title="Editar archivo">
                                    <ion-icon name="create-outline"></ion-icon>
                                </a>
                                <button class="btn-action btn-delete" 
                                        data-id="<%= archivo.getIdArchivo() %>"
                                        data-nombre="<%= archivo.getNombre() %>"
                                        title="Eliminar archivo">
                                    <ion-icon name="trash-outline"></ion-icon>
                                </button>
                            </div>
                        </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
        
        <% } else { %>
        
        <!-- Mensaje para cuando no hay archivos -->
        <div class="empty-state">
            <ion-icon name="folder-open-outline"></ion-icon>
            <h3>No tienes recursos cargados</h3>
            <p>Comienza a compartir materiales académicos con la comunidad</p>
            <a href="Upload" class="btn-primary">
                Subir Archivo
            </a>
        </div>
        <% } %>
    </div>
</div>

<!-- Modal de confirmación de eliminación -->
<div id="modalEliminar" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <ion-icon name="warning-outline"></ion-icon>
            <h2>Confirmar Eliminación</h2>
        </div>
        <div class="modal-body">
            <p>¿Estás seguro de que deseas eliminar el archivo?</p>
            <p class="archivo-nombre"><strong id="nombreArchivo"></strong></p>
            <p class="warning-text">Esta acción no se puede deshacer.</p>
        </div>
        <div class="modal-footer">
            <button onclick="cerrarModal()" class="btn-secondary">Cancelar</button>
            <button id="btnConfirmarEliminar" class="btn-danger">Eliminar</button>
        </div>
    </div>
</div>

<!-- Iconos -->
<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>

<script>
    let idArchivoEliminar = null;

    // Usar delegación de eventos para los botones de eliminar
    document.addEventListener('click', function(e) {
        const btnDelete = e.target.closest('.btn-delete');
        if (btnDelete) {
            const id = btnDelete.getAttribute('data-id');
            const nombre = btnDelete.getAttribute('data-nombre');
            confirmarEliminacion(id, nombre);
        }
    });

    function confirmarEliminacion(id, nombre) {
        idArchivoEliminar = id;
        document.getElementById('nombreArchivo').textContent = nombre;
        document.getElementById('modalEliminar').classList.add('active');
        document.body.style.overflow = 'hidden';
    }

    function cerrarModal() {
        document.getElementById('modalEliminar').classList.remove('active');
        document.body.style.overflow = 'auto';
        idArchivoEliminar = null;
    }

    document.getElementById('btnConfirmarEliminar').addEventListener('click', function() {
        if (idArchivoEliminar) {
            window.location.href = 'EliminarArchivo?id=' + idArchivoEliminar;
        }
    });

    
</script>

</body>
</html>
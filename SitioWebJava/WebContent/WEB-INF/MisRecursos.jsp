<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.LinkedList" %>
<%@ page import="entities.Archivo" %>
<%@ page import="entities.Carrera" %>
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
    <title>Mis Recursos</title>

    <!-- Íconos externos -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
    <link rel="icon" href="https://cdn-icons-png.flaticon.com/512/3197/3197967.png">
    
    <!-- Hoja de estilos única -->
    <link rel="stylesheet" href="style/misRecursos.css">
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
                        <ion-icon name="business-outline"></ion-icon> Mis Recursos
                </li>
            </ol>
        </nav>

    <div class="container">
        <div class="files-section">
            <!-- Header de la sección -->
            <div class="section-header">
                <h2>Mis Recursos</h2>
                <div class="results-info">
                    <%
                        LinkedList<Archivo> archivos = (LinkedList<Archivo>) request.getAttribute("archivos");
                        int totalArchivos = (archivos != null) ? archivos.size() : 0;
                    %>
                    <span>Mostrando <%= totalArchivos %> archivo(s)</span>
                </div>
            </div>

            <!-- Tabla de archivos -->
            <div class="table-container">
                <table class="files-table">
                    <thead>
                        <tr>
                            <th>Archivo</th>
                            <th>Tipo</th>
                            <th>Tamaño</th>
                            <th>Fecha</th>
                            <th>Materia</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                        if (archivos != null && !archivos.isEmpty()) {
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
                        %>
                        <tr>
                            <!-- Columna: Archivo -->
                            <td class="file-info">
                                <div class="file-icon">
                                    <i class="<%= iconClass %>" style="<%= iconStyle %>"></i>
                                </div>
                                <div class="file-details">
                                    <span class="file-name" title="<%= archivo.getNombre() %>">
                                        <%= archivo.getNombre() %>
                                    </span>
                                    <span class="file-extension">.<%= archivo.getExtension() %></span>
                                </div>
                            </td>
                            
                            <!-- Columna: Tipo -->
                            <td>
                                <span class="badge badge-<%= archivo.getTipoArchivo() %>">
                                    <%= archivo.getTipoArchivo() %>
                                </span>
                            </td>
                            
                            <!-- Columna: Tamaño -->
                            <td><%= String.format("%.2f", archivo.getPeso()) %> MB</td>
                            
                            <!-- Columna: Fecha -->
                            <td><%= archivo.getFechaSubida() != null ? sdf.format(archivo.getFechaSubida()) : "-" %></td>
                            
                            <!-- Columna: Materia -->
                            <td>
                                <div class="subject-info">
                                    <div class="subject-name">
                                        <%= archivo.getMateria().getNombreMateria() %>
                                    </div>
                                    <div class="subject-details">
                                        <%
                                            if (archivo.getMateria() != null && 
                                                archivo.getMateria().getCarreras() != null &&
                                                !archivo.getMateria().getCarreras().isEmpty()) {
                                        %>
                                            <ul class="carreras-list">
                                                <%
                                                    for (Carrera c : archivo.getMateria().getCarreras()) {
                                                        if (c != null && c.getNombreCarrera() != null) {
                                                %>
                                                            <li><%= c.getNombreCarrera() %></li>
                                                <%
                                                        }
                                                    }
                                                %>
                                            </ul>
                                        <%
                                            } else {
                                        %>
                                            <span>-</span>
                                        <%
                                            }
                                        %>
                                    </div>
                                </div>
                            </td>
                            
                            <!-- Columna: Acciones -->
                            <td>
                                <div class="actions">
                                    <a href="EditarArchivo?id=<%= archivo.getIdArchivo() %>" 
                                       class="btn-edit" 
                                       title="Editar archivo">
                                        <ion-icon name="create-outline"></ion-icon>
                                        <span>Editar</span>
                                    </a>
                                    <button onclick="confirmarEliminacion(<%= archivo.getIdArchivo() %>)" 
                                            class="btn-delete" 
                                            title="Eliminar archivo">
                                        <ion-icon name="trash-outline"></ion-icon>
                                        <span>Eliminar</span>
                                    </button>
                                </div>
                            </td>
                        </tr>
                        <% 
                            }
                        } else { 
                        %>
                        <tr>
                            <td colspan="6" class="no-results">
                                <div class="no-results-content">
                                    <i class="fas fa-folder-open"></i>
                                    <p>No has subido archivos todavía</p>
                                </div>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</main>

<!-- Ionicons -->
<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>

<!-- Script para confirmación de eliminación -->
<script>

function confirmarEliminacion(idArchivo) {
    if (confirm('¿Estás seguro de que deseas eliminar este archivo?\n\nEsta acción no se puede deshacer.')) {
        window.location.href = 'EliminarArchivo?id=' + idArchivo;
    }
}


</script>
</body>
</html>
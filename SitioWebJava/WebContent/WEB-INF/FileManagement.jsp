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
    <link rel="stylesheet" href="style/filelist.css">  
    <link rel="stylesheet" href="style/listaArchivos.css"> 
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
</head>
<body>
<main class="main-content">

 <!-- Breadcrumb -->
<nav aria-label="breadcrumb" class="mb-4">
    <ol class="breadcrumb">
        <li class="breadcrumb-item">
            <a href="bienvenida.jsp" class="text-decoration-none">
                <ion-icon name="home-outline"></ion-icon> Inicio
            </a>
        </li>
        <li class="breadcrumb-item active" aria-current="page">
            <ion-icon name="search-circle-outline"></ion-icon> Buscar Material
        </li>
    </ol>
</nav>

<div class="container">
    <div class="files-section">
        <div class="section-header">
            <h2>Archivos Disponibles</h2>

            <div class="results-info">
                <%
                    LinkedList<Archivo> archivos = (LinkedList<Archivo>) request.getAttribute("archivos");
                    int totalArchivos = (archivos != null) ? archivos.size() : 0;
                %>
                <span>Mostrando <%= totalArchivos %> archivo(s)</span>
            </div>
        </div>

        <div class="table-container">
            <table class="files-table">
                <thead>
                    <tr>
                        <th>Archivo</th>
                        <th>Tipo</th>
                        <th>Tamaño</th>
                        <th>Usuario</th>
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
                            
                            String iconClass = "bi bi-file-earmark"; 
                            String iconStyle = "color: gray;";

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
                            } else if ("jpg".equals(extension) || "jpeg".equals(extension) || "png".equals(extension) || "gif".equals(extension)) {
                                iconClass = "bi bi-file-earmark-image";
                                iconStyle = "color: #0dcaf0;";
                            }
                    %>
                    <tr>
                        <td class="file-info">
                            <div class="file-icon">
                                <i class="<%= iconClass %>" style="<%= iconStyle %>"></i>
                            </div>
                            <div class="file-details">
                                <span class="file-name"><%= archivo.getNombre() %></span>
                                <span class="file-extension">.<%= archivo.getExtension() %></span>
                            </div>
                        </td>
                        <td>
                            <span class="badge badge-<%= archivo.getTipoArchivo() %>">
                                <%= archivo.getTipoArchivo() %>
                            </span>
                        </td>
                        <td><%= String.format("%.2f", archivo.getPeso()) %> MB</td>
                        <td class="user-info">
                            <i class="fas fa-user"></i>
                            <%= archivo.getUsuario().getNombre() %> <%= archivo.getUsuario().getApellido() %>
                        </td>
                        <td><%= archivo.getFechaSubida() != null ? sdf.format(archivo.getFechaSubida()) : "" %></td>
                        <td class="subject-info">
                            <div class="subject-name"><%= archivo.getMateria().getNombreMateria() %></div>
                            <div class="subject-details">
                                <%
                                    if (archivo.getMateria() != null && archivo.getMateria().getCarreras() != null
                                            && !archivo.getMateria().getCarreras().isEmpty()) {
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
                        </td>
                        <td class="actions">
                            <a href="DescargarArchivo?id=<%= archivo.getIdArchivo() %>" 
                               class="btn btn-download" title="Descargar archivo">
                                <ion-icon name="download-outline" size="small"></ion-icon>
                            </a>
                        </td>
                    </tr>
                    <% 
                        }
                    } else { 
                    %>
                    <tr>
                        <td colspan="7" class="no-results">
                            <div class="no-results-content">
                                <i class="fas fa-folder-open"></i>
                                <p>No se encontraron archivos disponibles.</p>
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

<!-- Iconos -->
<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
</body>
</html>

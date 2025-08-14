<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.LinkedList" %>
<%@ page import="entities.Archivo" %>
<%@ page import="entities.Usuario" %>
<%@ page import="entities.Materia" %>
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
    <link rel="stylesheet" href="style/filelist.css">
 <!-- <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"> -->   
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>

<main class="main-content">
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
                            <th>Materia / Carrera</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                        if (archivos != null && !archivos.isEmpty()) {
                            for (Archivo archivo : archivos) {
                                String extension = "";
                                if (archivo.getNombre() != null && archivo.getNombre().contains(".")) {
                                    extension = archivo.getNombre().substring(archivo.getNombre().lastIndexOf('.') + 1).toLowerCase();
                                }
                                
                                String iconClass = "fas fa-file";
                                if ("pdf".equals(extension)) iconClass = "fas fa-file-pdf";
                                else if ("doc".equals(extension) || "docx".equals(extension)) iconClass = "fas fa-file-word";
                                else if ("xls".equals(extension) || "xlsx".equals(extension)) iconClass = "fas fa-file-excel";
                                else if ("ppt".equals(extension) || "pptx".equals(extension)) iconClass = "fas fa-file-powerpoint";
                        %>
                        <tr>
                            <td class="file-info">
                                <div class="file-icon">
                                    <i class="<%= iconClass %>"></i>
                                </div>
                                <div class="file-details">
                                    <span class="file-name"><%= archivo.getNombre() %></span>
                                    <span class="file-extension">.<%= extension %></span>
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
                                    <%= archivo.getMateria().getCarrera().getNombreCarrera() %>
                                </div>
                            </td>
                            <td class="actions">
                                <a href="DescargarArchivoServlet?id=<%= archivo.getIdArchivo() %>" 
                                   class="btn btn-download" title="Descargar archivo">
                                    <i class="fas fa-download"></i>
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

</body>
</html>

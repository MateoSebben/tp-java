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
    <link rel="icon" href="http://getbootstrap.com/favicon.ico">
    <title>Gestión de Facultades</title>
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="style/boton.css"> 
    
    
    <%
        Usuario u = (Usuario)session.getAttribute("usuario");
        LinkedList<Facultad> lf = (LinkedList<Facultad>)request.getAttribute("listaFacultades");
        int totalFacultades = (lf != null) ? lf.size() : 0;
    %>
</head>
<body>
    <div class="main-container">
        <!-- Header -->
        <div class="header-section">
            <div class="header-title">
                <i class="fas fa-university"></i>
                <h1>Gestión de Facultades</h1>
            </div>
            <form action="agregarFacultad" method="get">
                <input type="hidden" name="botonAgregar" />
                <button type="submit" class="btn-agregar">
                    <i class="fas fa-plus-circle"></i>
                    <span>Nueva Facultad</span>
                </button>
            </form>
        </div>
        
        <!-- Stats Bar -->
        <div class="stats-bar">
            <div class="stat-item">
                <div class="stat-number"><%= totalFacultades %></div>
                <div class="stat-label">Total Facultades</div>
            </div>
            <div class="stat-item">
                <div class="stat-number"><i class="fas fa-check-circle" style="color: #48bb78;"></i></div>
                <div class="stat-label">Activas</div>
            </div>
            <div class="stat-item">
                <div class="stat-number"><i class="fas fa-chart-line" style="color: #667eea;"></i></div>
                <div class="stat-label">En Crecimiento</div>
            </div>
        </div>
        
        <!-- Facultades Grid -->
        <% if (lf != null && !lf.isEmpty()) { %>
            <div class="facultades-grid">
                <% for (Facultad fac : lf) { %>
                    <div class="facultad-card">
                        <div class="card-header-section">
                            <div class="card-id"><%= fac.getId() %></div>
                            <div class="card-actions">
                                <form action="editarFacultad" method="get" style="display: inline;">
                                    <input type="hidden" name="id" value="<%= fac.getId() %>" />
                                    <button type="submit" class="btn-action btn-editar">
                                        <i class="fas fa-edit"></i>
                                        Editar
                                    </button>
                                </form>
                                <form action="eliminarFacultad" method="post" style="display: inline;">
                                    <input type="hidden" name="idFac" value="<%= fac.getId() %>" />
                                    <button type="submit" class="btn-action btn-borrar" 
                                            onclick="return confirm('¿Está seguro que desea eliminar esta facultad?');">
                                        <i class="fas fa-trash-alt"></i>
                                        Borrar
                                    </button>
                                </form>
                            </div>
                        </div>
                        
                        <h2 class="facultad-nombre"><%= fac.getNombre() %></h2>
                        
                        <div class="info-row">
                            <div class="info-icon">
                                <i class="fas fa-map-marker-alt"></i>
                            </div>
                            <div class="info-content">
                                <div class="info-label">Dirección</div>
                                <div class="info-value"><%= fac.getDireccion() %></div>
                            </div>
                        </div>
                        
                        <div class="info-row">
                            <div class="info-icon">
                                <i class="fas fa-envelope"></i>
                            </div>
                            <div class="info-content">
                                <div class="info-label">Email de Contacto</div>
                                <div class="info-value"><%= fac.getEmailContacto() %></div>
                            </div>
                        </div>
                        
                        <div class="info-row">
                            <div class="info-icon">
                                <i class="fas fa-phone"></i>
                            </div>
                            <div class="info-content">
                                <div class="info-label">Teléfono</div>
                                <div class="info-value"><%= fac.getTelefono() %></div>
                            </div>
                        </div>
                        
                        <div class="info-row">
                            <div class="info-icon">
                                <i class="fas fa-calendar-plus"></i>
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
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

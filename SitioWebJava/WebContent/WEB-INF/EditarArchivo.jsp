<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.LinkedList" %>
<%@ page import="entities.Archivo" %>
<%@ page import="entities.Materia" %>
<%
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    
    Archivo archivo = (Archivo) request.getAttribute("archivo");
    LinkedList<Materia> materias = (LinkedList<Materia>) request.getAttribute("materias");
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Archivo - SGRAC</title>

    <!-- Estilos -->
    <link rel="stylesheet" href="style/filelist.css">  
    <link rel="stylesheet" href="style/listaArchivos.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
    
    <style>
        .edit-form-container {
            max-width: 800px;
            margin: 2rem auto;
            background: white;
            border-radius: 12px;
            padding: 2.5rem;
            box-shadow: 0 2px 15px rgba(0, 0, 0, 0.08);
        }
        
        .form-header {
            margin-bottom: 2rem;
            padding-bottom: 1rem;
            border-bottom: 2px solid #f1f3f4;
        }
        
        .form-header h2 {
            color: #2c3e50;
            font-size: 1.8rem;
            font-weight: 600;
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }
        
        .form-group {
            margin-bottom: 1.5rem;
        }
        
        .form-group label {
            display: block;
            font-weight: 600;
            margin-bottom: 0.5rem;
            color: #555;
            font-size: 0.95rem;
        }
        
        .form-group label .required {
            color: #dc3545;
            margin-left: 0.2rem;
        }
        
        .form-control {
            width: 100%;
            padding: 0.75rem;
            border: 2px solid #e1e8ed;
            border-radius: 8px;
            font-size: 1rem;
            transition: all 0.3s ease;
            font-family: inherit;
        }
        
        .form-control:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
        }
        
        textarea.form-control {
            resize: vertical;
            min-height: 100px;
        }
        
        .form-actions {
            display: flex;
            gap: 1rem;
            justify-content: flex-end;
            margin-top: 2rem;
            padding-top: 1.5rem;
            border-top: 1px solid #e1e8ed;
        }
        
        .btn-save {
            background: linear-gradient(135deg, #28a745, #218838);
            color: white;
            padding: 0.75rem 2rem;
            border-radius: 8px;
            font-size: 1rem;
            font-weight: 600;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            gap: 0.5rem;
            transition: all 0.3s ease;
            box-shadow: 0 3px 6px rgba(0, 0, 0, 0.15);
            border: none;
            cursor: pointer;
        }
        
        .btn-save:hover {
            background: linear-gradient(135deg, #218838, #1e7e34);
            transform: translateY(-2px);
            box-shadow: 0 6px 12px rgba(0, 0, 0, 0.25);
        }
        
        .btn-cancel {
            background: #6c757d;
            color: white;
            padding: 0.75rem 2rem;
            border-radius: 8px;
            font-size: 1rem;
            font-weight: 600;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            gap: 0.5rem;
            transition: all 0.3s ease;
            border: none;
            cursor: pointer;
        }
        
        .btn-cancel:hover {
            background: #5a6268;
            transform: translateY(-2px);
        }
        
        .alert {
            padding: 1rem;
            border-radius: 8px;
            margin-bottom: 1.5rem;
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }
        
        .alert-danger {
            background: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        
        .file-info-display {
            background: #f8f9fa;
            padding: 1rem;
            border-radius: 8px;
            margin-bottom: 1.5rem;
            display: flex;
            align-items: center;
            gap: 1rem;
        }
        
        .file-info-display .file-icon {
            font-size: 2.5rem;
        }
        
        .file-info-display .file-details {
            flex: 1;
        }
        
        .file-info-display .file-name {
            font-weight: 600;
            color: #2c3e50;
            font-size: 1.1rem;
        }
        
        .file-info-display .file-meta {
            color: #6c757d;
            font-size: 0.9rem;
            margin-top: 0.3rem;
        }
    </style>
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
        <li class="breadcrumb-item">
            <a href="ListarMisRecursos" class="text-decoration-none">
                <ion-icon name="document-text-outline"></ion-icon> Mis Recursos
            </a>
        </li>
        <li class="breadcrumb-item active" aria-current="page">
            <ion-icon name="create-outline"></ion-icon> Editar Archivo
        </li>
    </ol>
</nav>

<div class="container">
    <div class="edit-form-container">
        <div class="form-header">
            <h2>
                <ion-icon name="create-outline"></ion-icon>
                Editar Archivo
            </h2>
        </div>
        
        <% if (error != null) { %>
        <div class="alert alert-danger">
            <ion-icon name="alert-circle-outline"></ion-icon>
            <%= error %>
        </div>
        <% } %>
        
        <% if (archivo != null) { %>
        
        <!-- Información del archivo actual -->
        <div class="file-info-display">
            <div class="file-icon">
                <%
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
                    }
                %>
                <i class="<%= iconClass %>" style="<%= iconStyle %>"></i>
            </div>
            <div class="file-details">
                <div class="file-name"><%= archivo.getNombre() %>.<%= archivo.getExtension() %></div>
                <div class="file-meta">
                    Tipo: <%= archivo.getTipoArchivo() %> | 
                    Tamaño: <%= String.format("%.2f", archivo.getPeso()) %> MB
                </div>
            </div>
        </div>
        
        <!-- Formulario de edición -->
        <form action="EditarArchivo" method="post" accept-charset="UTF-8">
            <input type="hidden" name="idArchivo" value="<%= archivo.getIdArchivo() %>">
            
            <div class="form-group">
                <label for="nombre">
                    Nombre del archivo <span class="required">*</span>
                </label>
                <input 
                    type="text" 
                    class="form-control" 
                    id="nombre" 
                    name="nombre" 
                    value="<%= archivo.getNombre() %>" 
                    required
                    maxlength="255"
                    placeholder="Ingrese el nombre del archivo">
            </div>
            
            <div class="form-group">
                <label for="descripcion">
                    Descripción
                </label>
                <textarea 
                    class="form-control" 
                    id="descripcion" 
                    name="descripcion" 
                    rows="4"
                    maxlength="500"
                    placeholder="Agregue una descripción del archivo (opcional)"><%= archivo.getDescripcion() != null ? archivo.getDescripcion() : "" %></textarea>
            </div>
            
            <div class="form-group">
                <label for="tipoArchivo">
                    Tipo de archivo <span class="required">*</span>
                </label>
                <select class="form-control" id="tipoArchivo" name="tipoArchivo" required>
                    <option value="">Seleccione un tipo</option>
                    <option value="APUNTES" <%= "APUNTES".equals(archivo.getTipoArchivo()) ? "selected" : "" %>>Apuntes</option>
                    <option value="PARCIAL" <%= "PARCIAL".equals(archivo.getTipoArchivo()) ? "selected" : "" %>>Parcial</option>
                    <option value="FINAL" <%= "FINAL".equals(archivo.getTipoArchivo()) ? "selected" : "" %>>Final</option>
                    <option value="EJERCICIOS" <%= "EJERCICIOS".equals(archivo.getTipoArchivo()) ? "selected" : "" %>>Ejercicios</option>
                    <option value="BIBLIOGRAFIA" <%= "BIBLIOGRAFIA".equals(archivo.getTipoArchivo()) ? "selected" : "" %>>Bibliografía</option>
                    <option value="TRABAJO" <%= "TRABAJO".equals(archivo.getTipoArchivo()) ? "selected" : "" %>>Trabajo Práctico</option>
                </select>
            </div>
            
            <div class="form-group">
                <label for="idMateria">
                    Materia <span class="required">*</span>
                </label>
                <select class="form-control" id="idMateria" name="idMateria" required>
                    <option value="">Seleccione una materia</option>
                    <% 
                    if (materias != null) {
                        for (Materia m : materias) {
                            boolean selected = (archivo.getMateria() != null && 
                                              archivo.getMateria().getIdMateria() == m.getIdMateria());
                    %>
                    <option value="<%= m.getIdMateria() %>" <%= selected ? "selected" : "" %>>
                        <%= m.getNombreMateria() %>
                    </option>
                    <% 
                        }
                    }
                    %>
                </select>
            </div>
            
            <div class="form-actions">
                <a href="ListarMisRecursos" class="btn-cancel">
                    <ion-icon name="close-outline"></ion-icon>
                    Cancelar
                </a>
                <button type="submit" class="btn-save">
                    <ion-icon name="save-outline"></ion-icon>
                    Guardar Cambios
                </button>
            </div>
        </form>
        
        <% } else { %>
        <div class="alert alert-danger">
            <ion-icon name="alert-circle-outline"></ion-icon>
            No se encontró el archivo solicitado.
        </div>
        <% } %>
    </div>
</div>
</main>

<!-- Iconos -->
<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
</body>
</html>

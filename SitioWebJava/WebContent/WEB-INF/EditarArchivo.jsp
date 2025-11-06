<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.LinkedList" %>
<%@ page import="entities.Archivo" %>
<%@ page import="entities.Materia" %>
<%@ page import="entities.Facultad" %>
<%@ page import="entities.Carrera" %>
<%
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    
    Archivo archivo = (Archivo) request.getAttribute("archivo");
    LinkedList<Facultad> facultades = (LinkedList<Facultad>) request.getAttribute("facultades");
    Integer idFacultadActual = (Integer) request.getAttribute("idFacultadActual");
    Integer idCarreraActual = (Integer) request.getAttribute("idCarreraActual");
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
    <link rel="stylesheet" href="style/editarArchivo.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
   
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
            <li class="breadcrumb-item">
                <a href="ListarMisRecursos">
                    <ion-icon name="folder-open-outline"></ion-icon> Mis Recursos
                </a>
            </li>
            <li class="breadcrumb-item active" aria-current="page">
                <ion-icon name="create-outline"></ion-icon> Editar Archivo
            </li>
        </ol>
    </nav>

    <div class="edit-form-container">
        <div class="form-header">
            <div class="header-icon">
                <ion-icon name="create-outline"></ion-icon>
            </div>
            <div class="header-text">
                <h1>Editar Archivo</h1>
                <p>Modifica la información del recurso académico</p>
            </div>
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
                    } else if ("jpg".equals(extension) || "jpeg".equals(extension) || "png".equals(extension) || "gif".equals(extension)) {
                        iconClass = "bi bi-file-earmark-image";
                        iconStyle = "color: #0dcaf0;";
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
        <form action="EditarArchivo" method="post" accept-charset="UTF-8" id="editForm">
            <input type="hidden" name="idArchivo" value="<%= archivo.getIdArchivo() %>">
            
            <div class="form-group">
                <label for="nombre">
                    <ion-icon name="document-text-outline"></ion-icon>
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
                    <ion-icon name="chatbox-outline"></ion-icon>
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
                    <ion-icon name="pricetag-outline"></ion-icon>
                    Tipo de archivo <span class="required">*</span>
                </label>
                <select class="form-control" id="tipoArchivo" name="tipoArchivo" required>
                    <option value="">Seleccione un tipo</option>
                    <option value="Apuntes de Clase" <%= "Apuntes de Clase".equals(archivo.getTipoArchivo()) ? "selected" : "" %>>Apuntes de Clase</option>
                    <option value="Examen Parcial" <%= "Examen Parcial".equals(archivo.getTipoArchivo()) ? "selected" : "" %>>Examen Parcial</option>
                    <option value="Examen Final" <%= "Examen Final".equals(archivo.getTipoArchivo()) ? "selected" : "" %>>Examen Final</option>
                    <option value="Ejercicios Resueltos" <%= "Ejercicios Resueltos".equals(archivo.getTipoArchivo()) ? "selected" : "" %>>Ejercicios Resueltos</option>
                    <option value="Trabajo Práctico" <%= "Trabajo Práctico".equals(archivo.getTipoArchivo()) ? "selected" : "" %>>Trabajo Práctico</option>
                    <option value="Resumen" <%= "Resumen".equals(archivo.getTipoArchivo()) ? "selected" : "" %>>Resumen</option>
                    <option value="Libro/Manual" <%= "Libro/Manual".equals(archivo.getTipoArchivo()) ? "selected" : "" %>>Libro/Manual</option>
                    <option value="Presentación" <%= "Presentación".equals(archivo.getTipoArchivo()) ? "selected" : "" %>>Presentación</option>
                    <option value="Notas de Clase" <%= "Notas de Clase".equals(archivo.getTipoArchivo()) ? "selected" : "" %>>Notas de Clase</option>
                    <option value="Otro" <%= "Otro".equals(archivo.getTipoArchivo()) ? "selected" : "" %>>Otro</option>
                </select>
            </div>

            <!-- DROPDOWNS EN CASCADA -->
            <div class="form-row">
                <div class="form-group">
                    <label for="facultad">
                        <ion-icon name="business-outline"></ion-icon>
                        Facultad <span class="required">*</span>
                    </label>
                    <select class="form-control" id="facultad" required>
                        <option value="">Selecciona una facultad</option>
                        <%
                            if (facultades != null) {
                                for (Facultad f : facultades) {
                        %>
                            <option value="<%= f.getId() %>"><%= f.getNombre() %></option>
                        <%
                                }
                            }
                        %>
                    </select>
                </div>

                <div class="form-group">
                    <label for="carrera">
                        <ion-icon name="school-outline"></ion-icon>
                        Carrera <span class="required">*</span>
                    </label>
                    <select class="form-control" id="carrera" required disabled>
                        <option value="">Primero selecciona una facultad</option>
                    </select>
                    <small class="loading-indicator">
                        <i class="fas fa-spinner fa-spin d-none" id="carrera-loading"></i>
                    </small>
                </div>
            </div>

            <div class="form-group">
                <label for="idMateria">
                    <ion-icon name="book-outline"></ion-icon>
                    Materia <span class="required">*</span>
                </label>
                <select class="form-control" id="idMateria" name="idMateria" required disabled>
                    <option value="">Primero selecciona una carrera</option>
                </select>
                <small class="loading-indicator">
                    <i class="fas fa-spinner fa-spin d-none" id="materia-loading"></i>
                </small>
            </div>
            
            <div class="form-actions">
                <a href="ListarMisRecursos" class="btn-cancel">
                    <ion-icon name="close-outline"></ion-icon>
                    Cancelar
                </a>
                <button type="submit" class="btn-save">
                    <ion-icon name="checkmark-circle-outline"></ion-icon>
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

<!-- Iconos -->
<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>

<% if (archivo != null) { %>
<script>
    
    // Datos del archivo actual para pre-cargar
    const materiaActual = {
        idMateria: <%= archivo.getMateria().getIdMateria() %>,
        idCarrera: <%= idCarreraActual != null ? idCarreraActual : 0 %>,
        idFacultad: <%= idFacultadActual != null ? idFacultadActual : 0 %>
    };

    // DROPDOWNS EN CASCADA
    
    document.addEventListener('DOMContentLoaded', function() {
        // Pre-seleccionar la facultad actual
        if (materiaActual.idFacultad) {
            document.getElementById('facultad').value = materiaActual.idFacultad;
            // Disparar el evento change para cargar las carreras
            cargarCarrerasYPreseleccionar();
        }
    });

    function cargarCarrerasYPreseleccionar() {
        const idFacultad = document.getElementById('facultad').value;
        const carreraSelect = document.getElementById('carrera');
        const materiaSelect = document.getElementById('idMateria');
        const carreraLoading = document.getElementById('carrera-loading');
        
        carreraSelect.innerHTML = '<option value="">Cargando carreras...</option>';
        carreraSelect.disabled = true;
        materiaSelect.innerHTML = '<option value="">Primero selecciona una carrera</option>';
        materiaSelect.disabled = true;
        
        carreraLoading.classList.remove('d-none');
        
        fetch('ObtenerCarrerasPorFacultad?idFacultad=' + idFacultad)
            .then(response => response.json())
            .then(carreras => {
                carreraSelect.innerHTML = '<option value="">Selecciona una carrera</option>';
                
                if (carreras.length === 0) {
                    carreraSelect.innerHTML = '<option value="">No hay carreras disponibles</option>';
                } else {
                    carreras.forEach(carrera => {
                        const option = document.createElement('option');
                        option.value = carrera.idCarrera;
                        option.textContent = carrera.nombreCarrera;
                        carreraSelect.appendChild(option);
                    });
                    carreraSelect.disabled = false;
                    
                    // Pre-seleccionar la carrera actual
                    if (materiaActual.idCarrera) {
                        carreraSelect.value = materiaActual.idCarrera;
                        cargarMateriasYPreseleccionar();
                    }
                }
            })
            .catch(error => {
                console.error('Error al cargar carreras:', error);
                carreraSelect.innerHTML = '<option value="">Error al cargar carreras</option>';
            })
            .finally(() => {
                carreraLoading.classList.add('d-none');
            });
    }

    function cargarMateriasYPreseleccionar() {
        const idCarrera = document.getElementById('carrera').value;
        const materiaSelect = document.getElementById('idMateria');
        const materiaLoading = document.getElementById('materia-loading');
        
        materiaSelect.innerHTML = '<option value="">Cargando materias...</option>';
        materiaSelect.disabled = true;
        materiaLoading.classList.remove('d-none');
        
        fetch('ObtenerMateriasPorCarrera?idCarrera=' + idCarrera)
            .then(response => response.json())
            .then(materias => {
                materiaSelect.innerHTML = '<option value="">Selecciona una materia</option>';
                
                if (materias.length === 0) {
                    materiaSelect.innerHTML = '<option value="">No hay materias disponibles</option>';
                } else {
                    materias.forEach(materia => {
                        const option = document.createElement('option');
                        option.value = materia.idMateria;
                        option.textContent = materia.nombreMateria;
                        materiaSelect.appendChild(option);
                    });
                    materiaSelect.disabled = false;
                    
                    // Pre-seleccionar la materia actual
                    if (materiaActual.idMateria) {
                        materiaSelect.value = materiaActual.idMateria;
                    }
                }
            })
            .catch(error => {
                console.error('Error al cargar materias:', error);
                materiaSelect.innerHTML = '<option value="">Error al cargar materias</option>';
            })
            .finally(() => {
                materiaLoading.classList.add('d-none');
            });
    }
    
    // Listener para cuando cambia la Facultad manualmente
    document.getElementById('facultad').addEventListener('change', function() {
        const idFacultad = this.value;
        const carreraSelect = document.getElementById('carrera');
        const materiaSelect = document.getElementById('idMateria');
        const carreraLoading = document.getElementById('carrera-loading');
        
        // Resetear materias
        materiaSelect.innerHTML = '<option value="">Primero selecciona una carrera</option>';
        materiaSelect.disabled = true;
        
        if (!idFacultad) {
            carreraSelect.innerHTML = '<option value="">Primero selecciona una facultad</option>';
            carreraSelect.disabled = true;
            return;
        }
        
        carreraSelect.innerHTML = '<option value="">Cargando carreras...</option>';
        carreraSelect.disabled = true;
        carreraLoading.classList.remove('d-none');
        
        fetch('ObtenerCarrerasPorFacultad?idFacultad=' + idFacultad)
            .then(response => response.json())
            .then(carreras => {
                carreraSelect.innerHTML = '<option value="">Selecciona una carrera</option>';
                
                if (carreras.length === 0) {
                    carreraSelect.innerHTML = '<option value="">No hay carreras disponibles</option>';
                } else {
                    carreras.forEach(carrera => {
                        const option = document.createElement('option');
                        option.value = carrera.idCarrera;
                        option.textContent = carrera.nombreCarrera;
                        carreraSelect.appendChild(option);
                    });
                    carreraSelect.disabled = false;
                }
            })
            .catch(error => {
                console.error('Error al cargar carreras:', error);
                carreraSelect.innerHTML = '<option value="">Error al cargar carreras</option>';
            })
            .finally(() => {
                carreraLoading.classList.add('d-none');
            });
    });
    
    // Listener para cuando cambia la Carrera manualmente
    document.getElementById('carrera').addEventListener('change', function() {
        const idCarrera = this.value;
        const materiaSelect = document.getElementById('idMateria');
        const materiaLoading = document.getElementById('materia-loading');
        
        if (!idCarrera) {
            materiaSelect.innerHTML = '<option value="">Primero selecciona una carrera</option>';
            materiaSelect.disabled = true;
            return;
        }
        
        materiaSelect.innerHTML = '<option value="">Cargando materias...</option>';
        materiaSelect.disabled = true;
        materiaLoading.classList.remove('d-none');
        
        fetch('ObtenerMateriasPorCarrera?idCarrera=' + idCarrera)
            .then(response => response.json())
            .then(materias => {
                materiaSelect.innerHTML = '<option value="">Selecciona una materia</option>';
                
                if (materias.length === 0) {
                    materiaSelect.innerHTML = '<option value="">No hay materias disponibles</option>';
                } else {
                    materias.forEach(materia => {
                        const option = document.createElement('option');
                        option.value = materia.idMateria;
                        option.textContent = materia.nombreMateria;
                        materiaSelect.appendChild(option);
                    });
                    materiaSelect.disabled = false;
                }
            })
            .catch(error => {
                console.error('Error al cargar materias:', error);
                materiaSelect.innerHTML = '<option value="">Error al cargar materias</option>';
            })
            .finally(() => {
                materiaLoading.classList.add('d-none');
            });
    });
</script>
<% } %>
</body>
</html>
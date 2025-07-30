<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Subir Recurso Académico</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">

</head>
<body>
    <%  
    // Se podria hacer un método para que se vayan actualizando las facultades
           String[] facultades = {
            "Facultad de Ingeniería",
            "Facultad de Ciencias Exactas",
            "Facultad de Medicina",
            "Facultad de Derecho",
            "Facultad de Ciencias Económicas",
            "Facultad de Filosofía y Letras",
            "Facultad de Arquitectura"
        };
        
        String[] tiposArchivo = {
            "Apuntes de Clase",
            "Examen Parcial",
            "Examen Final",
            "Ejercicios Resueltos",
            "Trabajo Práctico",
            "Resumen",
            "Libro/Manual",
            "Presentación",
            "Otro"
        };
        
        String[] años = {"2020", "2021", "2022", "2023", "2024", "2025"};
    %>

    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card shadow">
                    <div class="card-header bg-primary text-white">
                        <h3 class="mb-0">
                            <i class="fas fa-upload me-2"></i>
                            Subir Nuevo Recurso Académico
                        </h3>
                    </div>
                    <div class="card-body">
                        <% if (request.getAttribute("error") != null) { %>
                            <div class="alert alert-danger">
                                <i class="fas fa-exclamation-triangle me-2"></i>
                                <%= request.getAttribute("error") %>
                            </div>
                        <% } %>
                        
                        <% if (request.getAttribute("success") != null) { %>
                            <div class="alert alert-success">
                                <i class="fas fa-check-circle me-2"></i>
                                <%= request.getAttribute("success") %>
                            </div>
                        <% } %>

                        <form action="upload" method="post" enctype="multipart/form-data" id="uploadForm">
                            <!-- Subida de archivo -->
                            <div class="mb-4">
                                <label class="form-label fw-bold">Archivo *</label>
                                <div class="upload-area" onclick="document.getElementById('archivo').click()">
                                    <input type="file" id="archivo" name="archivo" class="d-none" required
                                           accept=".pdf,.doc,.docx,.txt,.jpg,.jpeg,.png,.gif,.xls,.xlsx,.ppt,.pptx">
                                    <div id="upload-content">
                                        <i class="fas fa-cloud-upload-alt fa-3x text-muted mb-3"></i>
                                        <p class="text-muted">Haz clic para seleccionar un archivo</p>
                                        <small class="text-muted">PDF, DOC, TXT, imágenes, Excel, PowerPoint (máx. 50MB)</small>
                                    </div>
                                </div>
                                <div id="file-info" class="mt-2 d-none">
                                    <div class="d-flex align-items-center">
                                        <i id="file-icon" class="fas fa-file fa-2x me-3"></i>
                                        <div>
                                            <div id="file-name" class="fw-bold"></div>
                                            <div id="file-size" class="text-muted small"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Información académica -->
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label for="facultad" class="form-label fw-bold">Facultad *</label>
                                    <select class="form-select" id="facultad" name="facultad" required>
                                        <option value="">Selecciona una facultad</option>
                                        <% for (String facultad : facultades) { %>
                                            <option value="<%= facultad %>"><%= facultad %></option>
                                        <% } %>
                                    </select>
                                </div>
                                <div class="col-md-6">
                                    <label for="carrera" class="form-label fw-bold">Carrera *</label>
                                    <input type="text" class="form-control" id="carrera" name="carrera" 
                                           placeholder="Ej: Ingeniería en Sistemas" required>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label for="materia" class="form-label fw-bold">Materia *</label>
                                    <input type="text" class="form-control" id="materia" name="materia" 
                                           placeholder="Ej: Algoritmos y Estructuras de Datos" required>
                                </div>
                                <div class="col-md-6">
                                    <label for="año" class="form-label fw-bold">Año de Cursada *</label>
                                    <select class="form-select" id="año" name="año" required>
                                        <option value="">Selecciona el año</option>
                                        <% for (String año : años) { %>
                                            <option value="<%= año %>"><%= año %></option>
                                        <% } %>
                                    </select>
                                </div>
                            </div>

                            <!-- Tipo de archivo y título -->
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label for="tipoArchivo" class="form-label fw-bold">Tipo de Material *</label>
                                    <select class="form-select" id="tipoArchivo" name="tipoArchivo" required>
                                        <option value="">Selecciona el tipo</option>
                                        <% for (String tipo : tiposArchivo) { %>
                                            <option value="<%= tipo %>"><%= tipo %></option>
                                        <% } %>
                                    </select>
                                </div>
                                <div class="col-md-6">
                                    <label for="titulo" class="form-label fw-bold">Título del Recurso *</label>
                                    <input type="text" class="form-control" id="titulo" name="titulo" 
                                           placeholder="Ej: Parcial 2023 - Tema A" required>
                                </div>
                            </div>

                            <!-- Descripción -->
                            <div class="mb-3">
                                <label for="descripcion" class="form-label fw-bold">Descripción</label>
                                <textarea class="form-control" id="descripcion" name="descripcion" rows="3"
                                          placeholder="Describe brevemente el contenido del archivo..."></textarea>
                            </div>

                            <!-- Tags -->
                            <div class="mb-4">
                                <label class="form-label fw-bold">Palabras Clave</label>
                                <div class="tag-input" id="tagContainer">
                                    <input type="text" id="tagInput" placeholder="Agregar palabra clave..." 
                                           style="border: none; outline: none; flex: 1; min-width: 150px;">
                                </div>
                                <input type="hidden" id="tags" name="tags">
                                <small class="text-muted">Presiona Enter para agregar una palabra clave</small>
                            </div>

                            <!-- Botones -->
                            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                <button type="button" class="btn btn-secondary me-md-2" onclick="limpiarFormulario()">
                                    <i class="fas fa-eraser me-1"></i>Limpiar
                                </button>
                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-upload me-1"></i>Subir Recurso
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        let tags = [];

        // Manejo de archivo
        document.getElementById('archivo').addEventListener('change', function(e) {
            const file = e.target.files[0];
            if (file) {
                document.getElementById('upload-content').classList.add('d-none');
                document.getElementById('file-info').classList.remove('d-none');
                
                document.getElementById('file-name').textContent = file.name;
                document.getElementById('file-size').textContent = formatFileSize(file.size);
                
                // Cambiar icono según tipo de archivo
                const extension = file.name.split('.').pop().toLowerCase();
                const iconElement = document.getElementById('file-icon');
                iconElement.className = 'fas fa-2x me-3 ';
                
                switch(extension) {
                    case 'pdf':
                        iconElement.className += 'fa-file-pdf text-danger';
                        break;
                    case 'doc':
                    case 'docx':
                        iconElement.className += 'fa-file-word text-primary';
                        break;
                    case 'xls':
                    case 'xlsx':
                        iconElement.className += 'fa-file-excel text-success';
                        break;
                    case 'ppt':
                    case 'pptx':
                        iconElement.className += 'fa-file-powerpoint text-warning';
                        break;
                    case 'jpg':
                    case 'jpeg':
                    case 'png':
                    case 'gif':
                        iconElement.className += 'fa-file-image text-info';
                        break;
                    default:
                        iconElement.className += 'fa-file text-secondary';
                }
                
                // Auto-llenar título si está vacío
                if (!document.getElementById('titulo').value) {
                    document.getElementById('titulo').value = file.name.split('.')[0];
                }
            }
        });

        // Manejo de tags
        document.getElementById('tagInput').addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                e.preventDefault();
                addTag();
            }
        });

        function addTag() {
            const input = document.getElementById('tagInput');
            const tag = input.value.trim().toLowerCase();
            
            if (tag && !tags.includes(tag)) {
                tags.push(tag);
                updateTagDisplay();
                input.value = '';
                updateTagsInput();
            }
        }

        function removeTag(tag) {
            tags = tags.filter(t => t !== tag);
            updateTagDisplay();
            updateTagsInput();
        }

        function updateTagDisplay() {
            const container = document.getElementById('tagContainer');
            const input = document.getElementById('tagInput');
            
            // Limpiar tags existentes
            const existingTags = container.querySelectorAll('.tag');
            existingTags.forEach(tag => tag.remove());
            
            // Agregar tags actuales
            tags.forEach(tag => {
                const tagElement = document.createElement('span');
                tagElement.className = 'tag';
                tagElement.innerHTML = `${tag} <span class="remove-tag" onclick="removeTag('${tag}')">&times;</span>`;
                container.insertBefore(tagElement, input);
            });
        }

        function updateTagsInput() {
            document.getElementById('tags').value = tags.join(',');
        }

        function formatFileSize(bytes) {
            if (bytes === 0) return '0 Bytes';
            const k = 1024;
            const sizes = ['Bytes', 'KB', 'MB', 'GB'];
            const i = Math.floor(Math.log(bytes) / Math.log(k));
            return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
        }

        function limpiarFormulario() {
            document.getElementById('uploadForm').reset();
            document.getElementById('upload-content').classList.remove('d-none');
            document.getElementById('file-info').classList.add('d-none');
            tags = [];
            updateTagDisplay();
            updateTagsInput();
        }

        // Drag and drop
        const uploadArea = document.querySelector('.upload-area');
        
        uploadArea.addEventListener('dragover', function(e) {
            e.preventDefault();
            uploadArea.classList.add('dragover');
        });
        
        uploadArea.addEventListener('dragleave', function(e) {
            e.preventDefault();
            uploadArea.classList.remove('dragover');
        });
        
        uploadArea.addEventListener('drop', function(e) {
            e.preventDefault();
            uploadArea.classList.remove('dragover');
            
            const files = e.dataTransfer.files;
            if (files.length > 0) {
                document.getElementById('archivo').files = files;
                document.getElementById('archivo').dispatchEvent(new Event('change'));
            }
        });
    </script>
</body>
</html>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ page import="java.util.*" %>
<%@ page import="entities.*" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Subir Recurso Académico</title>
    
    <!-- Estilos -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="icon" href="https://cdn-icons-png.flaticon.com/512/3197/3197967.png">
    <link rel="stylesheet" href="style/upload.css">
</head>
<body>
    <%  
        String[] tiposArchivo = {
            "Apuntes de Clase",
            "Examen Parcial",
            "Examen Final",
            "Ejercicios Resueltos",
            "Trabajo Práctico",
            "Resumen",
            "Libro/Manual",
            "Presentación",
            "Notas de Clase",
            "Otro"
        };
    
        int añoActual = Calendar.getInstance().get(Calendar.YEAR);
        int añoInicio = 1950;
    %>

    <div class="container-fluid mt-4">
    
    <!-- Breadcrumb -->
    <nav aria-label="breadcrumb" class="breadcrumb-nav col-md-8 container-fluid mt-4">
        <ol class="breadcrumb">
            <li class="breadcrumb-item">
                <a href="bienvenida.jsp">
                    <ion-icon name="home-outline"></ion-icon> Inicio
                </a>
            </li>
            <li class="breadcrumb-item active" aria-current="page">
                <ion-icon name="cloud-upload-outline"></ion-icon> Subir Material
            </li>
        </ol>
    </nav>
        
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card shadow">
                    <div class="card-header bg-primary text-black" style="background: linear-gradient(220deg, #7296f7 0%, #e7ffff 100%);">
                        <h3 class="mb-0">
                            <ion-icon name="cloud-upload-sharp" size="large"></ion-icon>
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
                                <label class="form-label fw-bold">Archivo</label>
                                <div class="upload-area" onclick="document.getElementById('archivo').click()">
                                    <input type="file" id="archivo" name="archivo" class="d-none" required
                                           accept=".pdf,.doc,.docx,.txt,.jpg,.jpeg,.png,.gif,.xls,.xlsx,.ppt,.pptx,application/vnd.ms-powerpoint,application/vnd.openxmlformats-officedocument.presentationml.presentation">
                                    <div id="upload-content">
                                        <ion-icon name="cloud-upload" size="large"></ion-icon>
                                        <p class="text-muted">Haz clic para seleccionar un archivo</p>
                                        <small class="text-muted">PDF, DOC, TXT, PNG, JPG, Excel, PowerPoint (máx. 50MB)</small>
                                    </div>
                                </div>
                              <div id="file-info" class="mt-2 d-none">
    								<button type="button" class="btn-change-file" onclick="cambiarArchivo()">
       							    <ion-icon name="sync-outline"></ion-icon>
       								 Cambiar
    								</button>
    									<div class="d-flex align-items-center">
        								<i id="file-icon" class="fas fa-file fa-2x"></i>
        									<div style="flex: 1;">
            								<div id="file-name" class="fw-bold"></div>
           								 <div id="file-size" class="text-muted small"></div>
        									</div>
    									</div>
							 </div>
                            </div>

                            <!-- Información académica -->
                            <div class="row mb-3">
                                
                                <!-- Dropdown de Facultad -->
                                <div class="col-md-6">
                                    <label for="facultad" class="form-label fw-bold">Facultad</label>
                                    <select class="form-select" id="facultad" name="idFacultad" required>
                                        <option value="">Selecciona una facultad</option>
                                        <%
                                            LinkedList<Facultad> facultades = (LinkedList<Facultad>) request.getAttribute("facultades");
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
                                
                                <!-- Dropdown de Carrera -->
                                <div class="col-md-6">
                                    <label for="carrera" class="form-label fw-bold">Carrera</label>
                                    <select class="form-select" id="carrera" name="idCarrera" required disabled>
                                        <option value="">Primero selecciona una facultad</option>
                                    </select>
                                    <small class="text-muted">
                                        <i class="fas fa-spinner fa-spin d-none" id="carrera-loading"></i>
                                    </small>
                                </div>
                            </div>

                            <div class="row mb-3">
                                
                                <!-- Dropdown de Materia con boton + -->
                                <div class="col-md-6">
                                    <label for="materia" class="form-label fw-bold">Materia</label>
                                    <div class="input-group">
                                        <select class="form-select" id="materia" name="idMateria" required disabled>
                                            <option value="">Primero selecciona una carrera</option>
                                        </select>
                                        <button class="btn btn-outline-primary" type="button" id="btnSolicitarMateria" 
                                                data-bs-toggle="modal" data-bs-target="#modalSolicitarMateria" 
                                                title="Solicitar nueva materia" disabled>
                                            <i class="fas fa-plus"></i>
                                        </button>
                                    </div>
                                    <small class="text-muted">
                                        <i class="fas fa-spinner fa-spin d-none" id="materia-loading"></i>
                                    </small>
                                </div>

                                <div class="col-md-6">
    								<label for="anioCursada" class="form-label fw-bold">Año de Cursada</label>
    								<select class="form-select" id="anioCursada" name="anioCursada" required>
        								<option value="">Selecciona el año</option>
        								<% 
            								for (int a = añoActual; a >= añoInicio; a--) { 
        								%>
            								<option value="<%= a %>"><%= a %></option>
        								<% 
            								} 
        								%>
    								</select>
								</div>
                            </div>

                            <!-- Tipo de archivo y título -->
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label for="tipoArchivo" class="form-label fw-bold">Tipo de Material</label>
                                    <select class="form-select" id="tipoArchivo" name="tipoMaterial" required>
                                        <option value="">Selecciona el tipo</option>
                                        <% for (String tipo : tiposArchivo) { %>
                                            <option value="<%= tipo %>"><%= tipo %></option>
                                        <% } %>
                                    </select>
                                </div>
                                <div class="col-md-6">
                                    <label for="titulo" class="form-label fw-bold">Título del Recurso</label>
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

                            <!-- Botones -->
                            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                <button type="button" class="btn btn-danger me-md-2" onclick="limpiarFormulario()">
                                    <ion-icon name="trash-outline"></ion-icon> Limpiar
                                </button>
                                <button type="submit" class="btn btn-primary d-flex align-items-center">
                                    <span class="me-2"><ion-icon name="cloud-upload-outline"></ion-icon></span>
                                    <span>Subir material</span>
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal para solicitar nueva materia -->
    <div class="modal fade" id="modalSolicitarMateria" tabindex="-1" aria-labelledby="modalSolicitarMateriaLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header bg-primary text-white">
                    <h5 class="modal-title" id="modalSolicitarMateriaLabel">
                        <i class="fas fa-plus-circle me-2"></i>Solicitar Nueva Materia
                    </h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="alert alert-info d-flex">
                        <ion-icon  class="info-circle" name="information-circle-outline"></ion-icon>
                        <small>Tu solicitud será revisada por los administradores. Te notificaremos cuando sea aprobada.</small>
                    </div>
                    
                    <div id="formSolicitarMateria">
                        <div class="mb-3">
                            <label for="nombreMateriaSolicitud" class="form-label fw-bold">Nombre de la Materia </label>
                            <input type="text" class="form-control" id="nombreMateriaSolicitud" 
                                   placeholder="Ej: Análisis Matemático II">
                        </div>
                        
                        <div class="mb-3">
                            <label for="descripcionSolicitud" class="form-label fw-bold">¿Por qué necesitas esta materia? </label>
                            <textarea class="form-control" id="descripcionSolicitud" rows="3" 
                                      placeholder="Explica brevemente por qué necesitas agregar esta materia..."></textarea>
                            <small class="text-muted">Ej: Es una materia nueva del plan 2024</small>
                        </div>
                        
                        <input type="hidden" id="idCarreraSolicitud">
                    </div>
                    
                    <div id="mensajeSolicitud" class="alert d-none"></div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <button type="button" class="btn btn-primary" id="btnEnviarSolicitud" onclick="enviarSolicitudMateria()">
                        <ion-icon class="paper-plane" name="paper-plane-outline"></ion-icon> Enviar Solicitud
                    </button>
                </div>
            </div>
        </div>
    </div>

	<!-- Iconos -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>	
    
    <script>
        let tags = [];

        // DROPDOWNS EN CASCADA - AJAX

        // Cuando cambia la Facultad, cargar Carreras
        document.getElementById('facultad').addEventListener('change', function() {
            const idFacultad = this.value;
            const carreraSelect = document.getElementById('carrera');
            const materiaSelect = document.getElementById('materia');
            const carreraLoading = document.getElementById('carrera-loading');
            const btnSolicitar = document.getElementById('btnSolicitarMateria');
            
            // Reset
            carreraSelect.innerHTML = '<option value="">Cargando carreras...</option>';
            carreraSelect.disabled = true;
            materiaSelect.innerHTML = '<option value="">Primero selecciona una carrera</option>';
            materiaSelect.disabled = true;
            btnSolicitar.disabled = true;
            
            if (!idFacultad) {
                carreraSelect.innerHTML = '<option value="">Primero selecciona una facultad</option>';
                return;
            }
            
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
        
        // Cuando cambia la Carrera, cargar Materias
        document.getElementById('carrera').addEventListener('change', function() {
            const idCarrera = this.value;
            const materiaSelect = document.getElementById('materia');
            const materiaLoading = document.getElementById('materia-loading');
            const btnSolicitar = document.getElementById('btnSolicitarMateria');
            
            materiaSelect.innerHTML = '<option value="">Cargando materias...</option>';
            materiaSelect.disabled = true;
            btnSolicitar.disabled = true;
            
            if (!idCarrera) {
                materiaSelect.innerHTML = '<option value="">Primero selecciona una carrera</option>';
                return;
            }
            
            // Guardar ID de carrera para la solicitud
            document.getElementById('idCarreraSolicitud').value = idCarrera;
            
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
                    
                    // Habilitar botón +
                    btnSolicitar.disabled = false;
                })
                .catch(error => {
                    console.error('Error al cargar materias:', error);
                    materiaSelect.innerHTML = '<option value="">Error al cargar materias</option>';
                    btnSolicitar.disabled = false;
                })
                .finally(() => {
                    materiaLoading.classList.add('d-none');
                });
        });

        // Solicitud de Materia
        function enviarSolicitudMateria() {
            const nombreMateria = document.getElementById('nombreMateriaSolicitud').value.trim();
            const descripcion = document.getElementById('descripcionSolicitud').value.trim();
            const idCarrera = document.getElementById('idCarreraSolicitud').value;
            
            if (!nombreMateria) {
                mostrarMensajeSolicitud('Por favor ingresa el nombre de la materia', 'danger');
                return;
            }
            
            if (!descripcion) {
                mostrarMensajeSolicitud('Por favor explica por qué necesitas esta materia', 'danger');
                return;
            }
            
            if (!idCarrera) {
                mostrarMensajeSolicitud('Error: No se detectó la carrera seleccionada', 'danger');
                return;
            }
            
            const btnEnviar = document.getElementById('btnEnviarSolicitud');
            btnEnviar.disabled = true;
            btnEnviar.innerHTML = '<i class="fas fa-spinner fa-spin me-1"></i>Enviando...';
            
            // CAMBIAR FormData por URLSearchParams
            const params = new URLSearchParams();
            params.append('nombreMateria', nombreMateria);
            params.append('descripcion', descripcion);
            params.append('idCarrera', idCarrera);
            
            fetch('CrearSolicitudMateria', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
                },
                body: params
            })
            .then(response => {
                console.log('Response status:', response.status);
                return response.json();
            })
            .then(data => {
                console.log('Response data:', data);
                if (data.success) {
                    mostrarMensajeSolicitud(data.message, 'success');
                    document.getElementById('nombreMateriaSolicitud').value = '';
                    document.getElementById('descripcionSolicitud').value = '';
                    
                    setTimeout(() => {
                        const modal = bootstrap.Modal.getInstance(document.getElementById('modalSolicitarMateria'));
                        modal.hide();
                    }, 2000);
                } else {
                    mostrarMensajeSolicitud(data.message, 'danger');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                mostrarMensajeSolicitud('Error al enviar la solicitud. Intenta de nuevo.', 'danger');
            })
            .finally(() => {
                btnEnviar.disabled = false;
                btnEnviar.innerHTML = '<i class="fas fa-paper-plane me-1"></i>Enviar Solicitud';
            });
        }
        
        function mostrarMensajeSolicitud(mensaje, tipo) {
            const mensajeDiv = document.getElementById('mensajeSolicitud');
            mensajeDiv.className = `alert alert-${tipo}`;
            mensajeDiv.textContent = mensaje;
            mensajeDiv.classList.remove('d-none');
            
            setTimeout(() => {
                mensajeDiv.classList.add('d-none');
            }, 5000);
        }

        document.getElementById('modalSolicitarMateria').addEventListener('hidden.bs.modal', function () {
            document.getElementById('nombreMateriaSolicitud').value = '';
            document.getElementById('descripcionSolicitud').value = '';
            document.getElementById('mensajeSolicitud').classList.add('d-none');
        });

     // Manejo de Archivo con VALIDACIÓN DE TAMAÑO
        document.getElementById('archivo').addEventListener('change', function(e) {
            const file = e.target.files[0];
            
            if (file) {
                // VALIDACIÓN: Máximo 50MB
                const maxSize = 50 * 1024 * 1024; // 50MB en bytes
                
                if (file.size > maxSize) {
                    //Archivo muy grande
                    mostrarAlerta(
                        'El archivo supera el límite de 50MB. Por favor, selecciona un archivo más pequeño. ' +
                        'Tamaño actual: ' + formatFileSize(file.size),
                        'danger'
                    );
                    
                    // Limpiar el input
                    this.value = '';
                    
                    // Resetear la UI
                    document.getElementById('upload-content').classList.remove('d-none');
                    document.getElementById('file-info').classList.add('d-none');
                    
                    return; // Detener ejecución
                }
                
                // Archivo válido - Mostrar información
                document.getElementById('upload-content').classList.add('d-none');
                document.getElementById('file-info').classList.remove('d-none');
                
                document.getElementById('file-name').textContent = file.name;
                document.getElementById('file-size').textContent = formatFileSize(file.size);
                
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
                
                if (!document.getElementById('titulo').value) {
                    document.getElementById('titulo').value = file.name.split('.')[0];
                }
            }
        });

        // Manejo de tags (código sin cambios)
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
            
            const existingTags = container.querySelectorAll('.tag');
            existingTags.forEach(tag => tag.remove());
            
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
            
            document.getElementById('carrera').innerHTML = '<option value="">Primero selecciona una facultad</option>';
            document.getElementById('carrera').disabled = true;
            document.getElementById('materia').innerHTML = '<option value="">Primero selecciona una carrera</option>';
            document.getElementById('materia').disabled = true;
            document.getElementById('btnSolicitarMateria').disabled = true;
            
            tags = [];
            updateTagDisplay();
            updateTagsInput();
        }

        // Mostrar alertas dinámicas
        function mostrarAlerta(mensaje, tipo) {
            // Eliminar alertas previas
            const alertasExistentes = document.querySelectorAll('.alert-dinamica');
            alertasExistentes.forEach(alerta => alerta.remove());
            
            // Crear nueva alerta
            const alerta = document.createElement('div');
            alerta.className = `alert alert-${tipo} alert-dinamica`;
            alerta.style.animation = 'slideDown 0.3s ease-out';
            
            // Icono según el tipo
            const icono = tipo === 'success' 
                ? '<i class="fas fa-check-circle me-2"></i>' 
                : '<i class="fas fa-exclamation-triangle me-2"></i>';
            
            alerta.innerHTML = icono + mensaje;
            
            // Insertar al inicio del card-body
            const cardBody = document.querySelector('.card-body');
            cardBody.insertBefore(alerta, cardBody.firstChild);
            
            // Scroll suave hacia la alerta
            alerta.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
            
            // Auto-ocultar después de 6 segundos
            setTimeout(() => {
                alerta.style.animation = 'slideUp 0.3s ease-out';
                setTimeout(() => alerta.remove(), 300);
            }, 6000);
        }

        // Drag and drop (código sin cambios)
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
        
     // Ocultar mensaje de éxito después de 4 segundos
        document.addEventListener('DOMContentLoaded', function() {
            const successAlert = document.querySelector('.alert-success');
            if (successAlert) {
                setTimeout(function() {
                    successAlert.style.display = 'none';
                }, 4000);
            }
        });
        
        function cambiarArchivo() {
            document.getElementById('archivo').click();
        }
    </script>
</body>
</html>
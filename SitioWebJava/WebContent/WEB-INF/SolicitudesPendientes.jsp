<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="entities.*" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Solicitudes Pendientes - SGRAC</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="style/solicitudes-pendientes.css">
    
</head>
<body>
    <%
        @SuppressWarnings("unchecked")
        LinkedList<SolicitudMateria> solicitudes = (LinkedList<SolicitudMateria>) request.getAttribute("solicitudes");
        int totalPendientes = (Integer) request.getAttribute("totalPendientes");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    %>

    <div class="container-fluid mt-4">
        <!-- Breadcrumb -->
        <nav aria-label="breadcrumb" class="mb-3">
            <ol class="breadcrumb">
                <li class="breadcrumb-item">
                    <a href="bienvenida.jsp" class="text-decoration-none">
                        <ion-icon name="home-outline"></ion-icon> Inicio
                    </a>
                </li>
                <li class="breadcrumb-item active" aria-current="page">
                    <ion-icon name="notifications-outline"></ion-icon> Solicitudes Pendientes
                </li>
            </ol>
        </nav>

        <!-- Header -->
        <div class="card shadow-sm mb-4">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h2 class="mb-0">
                            <ion-icon class="reader" name="reader-outline"></ion-icon>
                            Solicitudes de Materias
                        </h2>
                        <p class="text-muted mb-0 mt-2">Gestiona las solicitudes de nuevas materias</p>
                    </div>
                    <div class="text-end">
                        <span class="badge badge-pending fs-5">
                            <%= totalPendientes %> Pendientes
                        </span>
                    </div>
                </div>
            </div>
        </div>

        <!-- Mensajes -->
        <div id="mensajeGlobal" class="alert d-none"></div>

        <!-- Lista de Solicitudes -->
        <% if (solicitudes == null || solicitudes.isEmpty()) { %>
            <div class="card shadow-sm">
                <div class="card-body text-center py-5">
                    <ion-icon class="checkmark" name="checkmark-circle-sharp"></ion-icon>
                    <h4 class="mt-3">¡No hay solicitudes pendientes!</h4>
                    <p class="text-muted">Todas las solicitudes han sido procesadas.</p>
                    <a href="bienvenida.jsp" class="btn btn-primary mt-3">
                        <i class="fas fa-arrow-left me-2"></i>Volver al Inicio
                    </a>
                </div>
            </div>
        <% } else { %>
            <div class="row">
                <% for (SolicitudMateria sol : solicitudes) { %>
                <div class="col-md-6 mb-4">
                    <div class="card solicitud-card shadow-sm h-100">
                        <div class="card-header bg-primary text-white">
                            <div class="d-flex justify-content-between align-items-center">
                                <h5 class="mb-0">
                                    <ion-icon class="book" name="book-outline"></ion-icon>
                                    <%= sol.getNombreMateria() %>
                                </h5>
                                <span class="badge bg-warning text-dark">PENDIENTE</span>
                            </div>
                        </div>
                        <div class="card-body">
                            
                            <!-- Información de la solicitud -->
                            <div class="mb-3">
                                <strong><ion-icon class="sp-icon" name="school-outline"></ion-icon></i>  Carrera:</strong>
                                <span><%= sol.getNombreCarrera() %></span>
                            </div> 
                            
                            <div class="mb-3">
                                <strong><ion-icon class="sp-icon" name="person-outline"></ion-icon></i>  Solicitado por:</strong>
                                <span><%= sol.getNombreUsuarioSolicitante() %></span>
                                <br>
                                <small class="text-muted">
                                    <ion-icon class="mail" name="mail-outline"></ion-icon>
                                    <%= sol.getEmailUsuarioSolicitante() %>
                                </small>
                            </div>
                            
                            <div class="mb-3">
                                <strong><ion-icon class="sp-icon" name="calendar-clear-outline"></ion-icon></i>  Fecha:</strong>
                                <span><%= sol.getFechaSolicitud().format(formatter) %></span>
                            </div>
                            
                            <div class="mb-3">
                                <strong><ion-icon class="sp-icon" name="chatbubble-ellipses-outline"></ion-icon>  Justificación:</strong>
                                <p class="mb-0 mt-2 p-3 bg-light rounded">
                                    <%= sol.getDescripcion() != null && !sol.getDescripcion().isEmpty() 
                                        ? sol.getDescripcion() 
                                        : "Sin justificación proporcionada" %>
                                </p>
                            </div>
                        </div>
                        <div class="card-footer bg-white">
                            <div class="d-flex justify-content-end gap-2">
                                <button class="btn btn-danger" onclick="mostrarModalRechazo(<%= sol.getIdSolicitud() %>, '<%= sol.getNombreMateria() %>')">
                                    <i class="fas fa-times me-1"></i>Rechazar
                                </button>
                                <button class="btn btn-success" onclick="aprobarSolicitud(<%= sol.getIdSolicitud() %>, '<%= sol.getNombreMateria() %>')">
                                    <i class="fas fa-check me-1"></i>Aprobar
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <% } %>
            </div>
        <% } %>
    </div>

    <!-- Modal para rechazar -->
    <div class="modal fade" id="modalRechazo" tabindex="-1" aria-labelledby="modalRechazoLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header bg-danger text-white">
                    <h5 class="modal-title" id="modalRechazoLabel">
                        <i class="fas fa-times-circle me-2"></i>Rechazar Solicitud
                    </h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <p>¿Estás seguro de rechazar la materia <strong id="materiaRechazo"></strong>?</p>
                    
                    <div class="mb-3">
                        <label for="motivoRechazo" class="form-label fw-bold">Motivo del rechazo </label>
                        <textarea class="form-control" id="motivoRechazo" rows="3" 
                                  placeholder="Explica por qué se rechaza esta solicitud..." required></textarea>
                    </div>
                    
                    <input type="hidden" id="idSolicitudRechazo">
                    <div id="mensajeRechazo" class="alert d-none"></div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <button type="button" class="btn btn-danger" id="btnConfirmarRechazo" onclick="confirmarRechazo()">
                        <i class="fas fa-times me-1"></i>Rechazar Solicitud
                    </button>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Modal para aprobar -->
    
    <div class="modal fade" id="modalAprobar" tabindex="-1" aria-labelledby="modalAprobarLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header bg-success text-white">
                    <h5 class="modal-title" id="modalAprobarLabel">
                        <i class="fas fa-check-circle me-2"></i>Aprobar Solicitud
                    </h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="text-center mb-4">
                        <ion-icon class="school" name="school-outline"></ion-icon>
                    </div>
                    <p class="text-center mb-3">¿Confirmas que deseas <strong class="text-success">APROBAR</strong> la materia?</p>
                    <div class="alert alert-info border-0">
                        <div class="d-flex align-items-start">
                            <i class="fas fa-info-circle me-2 mt-1"></i>
                            <div>
                                <strong id="materiaAprobar"></strong>
                                <p class="mb-0 mt-2 small">Esta acción creará la materia en el sistema y la asociará con la carrera correspondiente.</p>
                            </div>
                        </div>
                    </div>
                    <input type="hidden" id="idSolicitudAprobar">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <button type="button" class="btn btn-success" id="btnConfirmarAprobar" onclick="confirmarAprobacion()">
                        <i class="fas fa-check me-1"></i>Confirmar Aprobación
                    </button>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>	
    
    <script>
    function aprobarSolicitud(idSolicitud, nombreMateria) {
        // Mostrar modal de confirmación
        document.getElementById('idSolicitudAprobar').value = idSolicitud;
        document.getElementById('materiaAprobar').textContent = nombreMateria;
        
        const modal = new bootstrap.Modal(document.getElementById('modalAprobar'));
        modal.show();
    }
    
    function confirmarAprobacion() {
        const idSolicitud = document.getElementById('idSolicitudAprobar').value;
        const modal = bootstrap.Modal.getInstance(document.getElementById('modalAprobar'));
        modal.hide();
        
        procesarSolicitud(idSolicitud, 'aprobar', null);
    }
        
        function mostrarModalRechazo(idSolicitud, nombreMateria) {
            document.getElementById('idSolicitudRechazo').value = idSolicitud;
            document.getElementById('materiaRechazo').textContent = nombreMateria;
            document.getElementById('motivoRechazo').value = '';
            document.getElementById('mensajeRechazo').classList.add('d-none');
            
            const modal = new bootstrap.Modal(document.getElementById('modalRechazo'));
            modal.show();
        }
        
        function confirmarRechazo() {
            const idSolicitud = document.getElementById('idSolicitudRechazo').value;
            const motivo = document.getElementById('motivoRechazo').value.trim();
            
            if (!motivo) {
                mostrarMensajeModal('Debes especificar el motivo del rechazo', 'danger');
                return;
            }
            
            procesarSolicitud(idSolicitud, 'rechazar', motivo);
        }
        
        function procesarSolicitud(idSolicitud, accion, motivoRechazo) {
            const params = new URLSearchParams();
            params.append('idSolicitud', idSolicitud);
            params.append('accion', accion);
            if (motivoRechazo) {
                params.append('motivoRechazo', motivoRechazo);
            }
            
            const btnConfirmar = document.getElementById('btnConfirmarRechazo');
            if (btnConfirmar) {
                btnConfirmar.disabled = true;
                btnConfirmar.innerHTML = '<i class="fas fa-spinner fa-spin me-1"></i>Procesando...';
            }
            
            fetch('ProcesarSolicitud', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
                },
                body: params
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    mostrarMensajeGlobal(data.message, 'success');
                    
                    // Cerrar modal si está abierto
                    const modalElement = document.getElementById('modalRechazo');
                    if (modalElement) {
                        const modal = bootstrap.Modal.getInstance(modalElement);
                        if (modal) modal.hide();
                    }
                    
                    // Recargar página después de 1.5 segundos
                    setTimeout(() => {
                        location.reload();
                    }, 1500);
                } else {
                    if (accion === 'rechazar') {
                        mostrarMensajeModal(data.message, 'danger');
                    } else {
                        mostrarMensajeGlobal(data.message, 'danger');
                    }
                }
            })
            .catch(error => {
                console.error('Error:', error);
                mostrarMensajeGlobal('Error al procesar la solicitud', 'danger');
            })
            .finally(() => {
                if (btnConfirmar) {
                    btnConfirmar.disabled = false;
                    btnConfirmar.innerHTML = '<i class="fas fa-times me-1"></i>Rechazar Solicitud';
                }
            });
        }
        
        function mostrarMensajeGlobal(mensaje, tipo) {
            const div = document.getElementById('mensajeGlobal');
            div.className = `alert alert-${tipo}`;
            div.innerHTML = `<i class="fas fa-${tipo == 'success' ? 'check' : 'exclamation'}-circle me-2"></i>${mensaje}`;
            div.classList.remove('d-none');
            
            window.scrollTo({ top: 0, behavior: 'smooth' });
            
            setTimeout(() => {
                div.classList.add('d-none');
            }, 5000);
        }
        
        function mostrarMensajeModal(mensaje, tipo) {
            const div = document.getElementById('mensajeRechazo');
            div.className = `alert alert-${tipo}`;
            div.textContent = mensaje;
            div.classList.remove('d-none');
        }
    </script>
</body>
</html>
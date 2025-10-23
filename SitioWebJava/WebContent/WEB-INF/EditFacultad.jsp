<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="entities.Facultad" %>

<%
    Facultad facu = (Facultad) request.getAttribute("facultadEditar");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Facultad</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="icon" href="https://cdn-icons-png.flaticon.com/512/3197/3197967.png">
    <link rel="stylesheet" href="style/editarFacultad.css">
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
                    <a href="listaFacultades">
                        <ion-icon name="business-outline"></ion-icon> Alta Facultades
                    </a>
                </li>
                <li class="breadcrumb-item active" aria-current="page">
                    <ion-icon name="create-outline"></ion-icon> Editar Facultad
                </li>
            </ol>
        </nav>

        <!-- Card Principal -->
        <div class="form-card">
            <!-- Header -->
            <div class="card-header-custom">
                <div class="header-icon">
                    <ion-icon name="create-outline"></ion-icon>
                </div>
                <div class="header-text">
                    <h1>Editar Facultad</h1>
                    <p>Actualiza la información de la facultad</p>
                </div>
            </div>

            <!-- Alert Info -->
            <div class="info-alert">
                <ion-icon name="information-circle-outline"></ion-icon>
                <div>
                    <strong>ID de la Facultad:</strong> <%= facu.getId() %>
                    <br>
                    <small>Los cambios se guardarán inmediatamente al hacer clic en "Guardar Cambios"</small>
                </div>
            </div>

            <!-- Formulario -->
            <form action="editarFacultad" method="post" id="formEditarFacultad">
                <input type="hidden" name="id" value="<%= facu.getId() %>" />

                <div class="form-section">
                    <h3 class="section-title">
                        <ion-icon name="business-outline"></ion-icon>
                        Información General
                    </h3>

                    <div class="form-group">
                        <label for="nombre" class="form-label">
                            <ion-icon name="school-outline"></ion-icon>
                            Nombre de la Facultad 
                        </label>
                        <input 
                            type="text" 
                            class="form-control" 
                            id="nombre" 
                            name="nombre" 
                            required 
                            value="<%= facu.getNombre() %>"
                            placeholder="Ej: Facultad de Ingeniería">
                        <div class="input-helper">Este campo es obligatorio</div>
                    </div>

                    <div class="form-group">
                        <label for="direccion" class="form-label">
                            <ion-icon name="location-outline"></ion-icon>
                            Dirección
                        </label>
                        <input 
                            type="text" 
                            class="form-control" 
                            id="direccion" 
                            name="direccion" 
                            value="<%= facu.getDireccion() %>"
                            placeholder="Ej: Av. Pellegrini 250, Rosario">
                    </div>
                </div>

                <div class="form-section">
                    <h3 class="section-title">
                        <ion-icon name="mail-outline"></ion-icon>
                        Datos de Contacto
                    </h3>

                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="emailContacto" class="form-label">
                                    <ion-icon name="at-outline"></ion-icon>
                                    Email de Contacto
                                </label>
                                <input 
                                    type="email" 
                                    class="form-control" 
                                    id="emailContacto" 
                                    name="emailContacto" 
                                    value="<%= facu.getEmailContacto() %>"
                                    placeholder="contacto@facultad.edu.ar">
                            </div>
                        </div>

                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="telefono" class="form-label">
                                    <ion-icon name="call-outline"></ion-icon>
                                    Teléfono
                                </label>
                                <input 
                                    type="text" 
                                    class="form-control" 
                                    id="telefono" 
                                    name="telefono" 
                                    value="<%= facu.getTelefono() %>"
                                    placeholder="+54 341 4801234">
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Información adicional -->
                <div class="info-box">
                    <div class="info-box-item">
                        <ion-icon name="calendar-outline"></ion-icon>
                        <div>
                            <span class="info-label">Fecha de Alta</span>
                            <span class="info-value"><%= facu.getFechaAlta() %></span>
                        </div>
                    </div>
                </div>

                <!-- Botones de Acción -->
                <div class="form-actions">
                    <a href="listaFacultades" class="btn btn-cancel">
                        <ion-icon name="close-outline"></ion-icon>
                        Cancelar
                    </a>
                    <button type="submit" class="btn btn-save">
                        <ion-icon name="checkmark-circle-outline"></ion-icon>
                        Guardar Cambios
                    </button>
                </div>
            </form>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
    
    <script>
        // Validación en tiempo real
        const inputs = document.querySelectorAll('.form-control');
        
        inputs.forEach(input => {
            input.addEventListener('input', function() {
                if (this.value.trim() !== '') {
                    this.classList.add('has-value');
                } else {
                    this.classList.remove('has-value');
                }
            });

            // Inicializar estado
            if (input.value.trim() !== '') {
                input.classList.add('has-value');
            }
        });

        // Confirmación antes de salir si hay cambios
        let formModified = false;
        const form = document.getElementById('formEditarFacultad');
        
        form.addEventListener('change', () => {
            formModified = true;
        });

        window.addEventListener('beforeunload', (e) => {
            if (formModified) {
                e.preventDefault();
                e.returnValue = '';
            }
        });

        form.addEventListener('submit', () => {
            formModified = false;
        });

        // Animación de entrada
        document.addEventListener('DOMContentLoaded', () => {
            document.querySelector('.form-card').classList.add('animate-in');
        });
    </script>
</body>
</html>
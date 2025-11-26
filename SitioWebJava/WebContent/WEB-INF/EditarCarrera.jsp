<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entities.Facultad" %>
<%@ page import="entities.Carrera" %>
<%@ page import="java.util.LinkedList" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Carrera</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="icon" href="https://cdn-icons-png.flaticon.com/512/3197/3197967.png">
    <link rel="stylesheet" href="style/agregarCarrera.css">
</head>
<body>
    <%
        Carrera carrera = (Carrera) request.getAttribute("carrera");
        Integer idFacultadActual = (Integer) request.getAttribute("idFacultad");
        
        if (carrera == null) {
            response.sendRedirect("listaCarreras");
            return;
        }
    %>
    
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
                    <a href="listaCarreras">
                        <ion-icon name="duplicate-outline"></ion-icon> Alta Carreras
                    </a>
                </li>
                <li class="breadcrumb-item active" aria-current="page">
                    <ion-icon name="create-outline"></ion-icon> Editar Carrera
                </li>
            </ol>
        </nav>

        <div class="content-wrapper">
            
            <!-- Sidebar Info -->
            <aside class="info-sidebar">
                <div class="sidebar-card">
                    <div class="sidebar-icon">
                        <ion-icon name="create-outline"></ion-icon>
                    </div>
                    <h3>Editando carrera</h3>
                    <ul class="tips-list">
                        <li>
                            <ion-icon name="checkmark-circle"></ion-icon>
                            <span>Verifica el nombre antes de guardar</span>
                        </li>
                        <li>
                            <ion-icon name="checkmark-circle"></ion-icon>
                            <span>Puedes cambiar la facultad asociada</span>
                        </li>
                        <li>
                            <ion-icon name="checkmark-circle"></ion-icon>
                            <span>Los cambios son permanentes</span>
                        </li>
                    </ul>
                </div>

                <div class="sidebar-card stats-card">
                    <div class="stat-icon">
                        <ion-icon name="information-circle-outline"></ion-icon>
                    </div>
                    <div class="stat-content">
                        <div class="stat-number">#<%= carrera.getIdCarrera() %></div>
                        <div class="stat-label">ID de la carrera</div>
                    </div>
                </div>
            </aside>

            <!-- Formulario Principal -->
            <div class="form-container">
                <div class="form-header">
                    <div class="header-content">
                        <div class="header-icon-wrapper">
                            <ion-icon name="create"></ion-icon>
                        </div>
                        <div>
                            <h1>Editar Carrera</h1>
                            <p>Modifica la información de la carrera</p>
                        </div>
                    </div>
                </div>

                <!-- Mensaje de error -->
                <% 
                    String error = (String) request.getAttribute("error");
                    if (error != null && !error.isEmpty()) {
                %>
                    <div class="alert-error">
                        <ion-icon name="alert-circle"></ion-icon>
                        <span><%= error %></span>
                    </div>
                <% } %>

                <form action="editarCarrera" method="post" id="formEditarCarrera">
                    
                    <input type="hidden" name="idCarrera" value="<%= carrera.getIdCarrera() %>">
                    
                    <!-- Información de la Carrera -->
                    <div class="form-step">
                        <div class="step-header">
                            <div class="step-number">1</div>
                            <div class="step-info">
                                <h3>Información de la Carrera</h3>
                                <p>Modifica los datos necesarios</p>
                            </div>
                        </div>

                        <div class="input-wrapper has-value">
                            <div class="input-icon">
                                <ion-icon name="business"></ion-icon>
                            </div>
                            <div class="input-content">
                                <label for="idFacultad">Facultad *</label>
                                <select 
                                    id="idFacultad" 
                                    name="idFacultad" 
                                    class="form-input form-select" 
                                    required>
                                    <option value="">Selecciona una facultad</option>
                                    <%
                                        data.DataFacultad dataFacultad = new data.DataFacultad();
                                        LinkedList<Facultad> facultades = dataFacultad.getAllFacultades();
                                        
                                        if (facultades != null && !facultades.isEmpty()) {
                                            for (Facultad fac : facultades) {
                                                boolean selected = (idFacultadActual != null && idFacultadActual == fac.getId());
                                    %>
                                                <option value="<%= fac.getId() %>" <%= selected ? "selected" : "" %>>
                                                    <%= fac.getNombre() %>
                                                </option>
                                    <%
                                            }
                                        }
                                    %>
                                </select>
                                <small class="input-hint">Puedes cambiar la facultad si es necesario</small>
                            </div>
                        </div>

                        <div class="input-wrapper has-value">
                            <div class="input-icon">
                                <ion-icon name="school"></ion-icon>
                            </div>
                            <div class="input-content">
                                <label for="nombreCarrera">Nombre de la Carrera *</label>
                                <input 
                                    type="text" 
                                    id="nombreCarrera" 
                                    name="nombreCarrera" 
                                    class="form-input" 
                                    value="<%= carrera.getNombreCarrera() %>"
                                    required>
                                <small class="input-hint">Modifica el nombre si es necesario</small>
                            </div>
                        </div>
                    </div>

                    <!-- Botones de Acción -->
                    <div class="form-actions">
                        <a href="listaCarreras" class="btn btn-cancel">
                            <ion-icon name="close-circle"></ion-icon>
                            <span>Cancelar</span>
                        </a>
                        <button type="submit" class="btn btn-submit">
                            <ion-icon name="checkmark-circle"></ion-icon>
                            <span>Guardar Cambios</span>
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
    
    <script>
        // Animaciones
        document.addEventListener('DOMContentLoaded', () => {
            const steps = document.querySelectorAll('.form-step');
            steps.forEach((step, index) => {
                setTimeout(() => {
                    step.classList.add('animate-in');
                }, index * 150);
            });

            const sidebar = document.querySelector('.info-sidebar');
            setTimeout(() => {
                sidebar.classList.add('animate-in');
            }, 300);
        });

        // Validación en tiempo real
        const inputs = document.querySelectorAll('.form-input:not(.form-select)');
        const select = document.querySelector('.form-select');
        
        inputs.forEach(input => {
            input.addEventListener('input', function() {
                const wrapper = this.closest('.input-wrapper');
                
                if (this.value.trim() !== '') {
                    wrapper.classList.add('has-value', 'is-valid');
                } else {
                    wrapper.classList.remove('has-value', 'is-valid');
                }
            });
        });

        if (select) {
            select.addEventListener('change', function() {
                const wrapper = this.closest('.input-wrapper');
                
                if (this.value !== '') {
                    wrapper.classList.add('has-value', 'is-valid');
                } else {
                    wrapper.classList.remove('has-value', 'is-valid');
                }
            });
        }
    </script>
</body>
</html>
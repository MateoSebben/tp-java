<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entities.Facultad" %>
<%@ page import="java.util.LinkedList" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Agregar Carrera</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="icon" href="https://cdn-icons-png.flaticon.com/512/3197/3197967.png">
    <link rel="stylesheet" href="style/agregarCarrera.css">
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
                    <a href="listaCarreras">
                        <ion-icon name="duplicate-outline"></ion-icon> Alta Carreras
                    </a>
                </li>
                <li class="breadcrumb-item active" aria-current="page">
                    <ion-icon name="add-circle-outline"></ion-icon> Nueva Carrera
                </li>
            </ol>
        </nav>

        <div class="content-wrapper">
            
            <!-- Sidebar Info -->
            <aside class="info-sidebar">
                <div class="sidebar-card">
                    <div class="sidebar-icon">
                        <ion-icon name="bulb-outline"></ion-icon>
                    </div>
                    <h3>Tips para agregar</h3>
                    <ul class="tips-list">
                        <li>
                            <ion-icon name="checkmark-circle"></ion-icon>
                            <span>Verifica que el nombre sea único</span>
                        </li>
                        <li>
                            <ion-icon name="checkmark-circle"></ion-icon>
                            <span>Selecciona la facultad correcta</span>
                        </li>
                        <li>
                            <ion-icon name="checkmark-circle"></ion-icon>
                            <span>Usa el nombre completo de la carrera</span>
                        </li>
                        <li>
                            <ion-icon name="checkmark-circle"></ion-icon>
                            <span>Revisa antes de guardar</span>
                        </li>
                    </ul>
                </div>

                <div class="sidebar-card stats-card">
                    <div class="stat-icon">
                        <ion-icon name="stats-chart-outline"></ion-icon>
                    </div>
                    <div class="stat-content">
                        <div class="stat-number">+1</div>
                        <div class="stat-label">Nueva carrera en el sistema</div>
                    </div>
                </div>
            </aside>

            <!-- Formulario Principal -->
            <div class="form-container">
                <div class="form-header">
                    <div class="header-content">
                        <div class="header-icon-wrapper">
                            <ion-icon name="add-circle"></ion-icon>
                        </div>
                        <div>
                            <h1>Agregar Nueva Carrera</h1>
                            <p>Completa la información para registrar una nueva carrera</p>
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

                <form action="agregarCarrera" method="post" id="formAgregarCarrera">
                    
                    <!-- Información de la Carrera -->
                    <div class="form-step">
                        <div class="step-header">
                            <div class="step-number">1</div>
                            <div class="step-info">
                                <h3>Información de la Carrera</h3>
                                <p>Datos de la nueva carrera</p>
                            </div>
                        </div>

                        <div class="input-wrapper">
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
                                        // Obtener la lista de facultades
                                        data.DataFacultad dataFacultad = new data.DataFacultad();
                                        LinkedList<Facultad> facultades = dataFacultad.getAllFacultades();
                                        
                                        if (facultades != null && !facultades.isEmpty()) {
                                            for (Facultad fac : facultades) {
                                    %>
                                                <option value="<%= fac.getId() %>"><%= fac.getNombre() %></option>
                                    <%
                                            }
                                        }
                                    %>
                                </select>
                                <small class="input-hint">Selecciona la facultad a la que pertenece esta carrera</small>
                            </div>
                        </div>

                        <div class="input-wrapper">
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
                                    placeholder="Ej: Ingeniería en Sistemas de Información"
                                    required>
                                <small class="input-hint">Ingresa el nombre completo de la carrera</small>
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
                            <span>Guardar Carrera</span>
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
        // Animación de entrada escalonada
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

        // Validación en tiempo real con feedback visual
        const inputs = document.querySelectorAll('.form-input:not(.form-select)');
        const select = document.querySelector('.form-select');
        
        inputs.forEach(input => {
            input.addEventListener('input', function() {
                const wrapper = this.closest('.input-wrapper');
                
                if (this.value.trim() !== '') {
                    wrapper.classList.add('has-value');
                    
                    if (this.required) {
                        wrapper.classList.add('is-valid');
                    }
                } else {
                    wrapper.classList.remove('has-value', 'is-valid', 'is-invalid');
                }
            });

            input.addEventListener('blur', function() {
                const wrapper = this.closest('.input-wrapper');
                if (this.required && this.value.trim() === '') {
                    wrapper.classList.add('is-invalid');
                }
            });
        });

        // Validación del select
        if (select) {
            select.addEventListener('change', function() {
                const wrapper = this.closest('.input-wrapper');
                
                if (this.value !== '') {
                    wrapper.classList.add('has-value', 'is-valid');
                    wrapper.classList.remove('is-invalid');
                } else {
                    wrapper.classList.remove('has-value', 'is-valid');
                }
            });

            select.addEventListener('blur', function() {
                const wrapper = this.closest('.input-wrapper');
                if (this.required && this.value === '') {
                    wrapper.classList.add('is-invalid');
                }
            });
        }

        // Confirmación antes de salir si hay datos
        let formHasData = false;
        const form = document.getElementById('formAgregarCarrera');
        
        const allInputs = [...inputs, select];
        allInputs.forEach(input => {
            input.addEventListener('input', () => {
                formHasData = true;
            });
            input.addEventListener('change', () => {
                formHasData = true;
            });
        });

        window.addEventListener('beforeunload', (e) => {
            if (formHasData && !form.submitted) {
                e.preventDefault();
                e.returnValue = '';
            }
        });

        form.addEventListener('submit', () => {
            form.submitted = true;
        });

        // Efecto de progreso en los pasos
        const formSteps = document.querySelectorAll('.form-step');
        const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    entry.target.classList.add('in-view');
                }
            });
        }, { threshold: 0.5 });

        formSteps.forEach(step => observer.observe(step));
    </script>
</body>
</html>
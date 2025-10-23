<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entities.Facultad" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Agregar Facultad</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="icon" href="https://cdn-icons-png.flaticon.com/512/3197/3197967.png">
    <link rel="stylesheet" href="style/agregarFacultad.css">
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
                    <ion-icon name="add-circle-outline"></ion-icon> Nueva Facultad
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
                            <span>Incluye una dirección completa</span>
                        </li>
                        <li>
                            <ion-icon name="checkmark-circle"></ion-icon>
                            <span>Usa un email institucional</span>
                        </li>
                        <li>
                            <ion-icon name="checkmark-circle"></ion-icon>
                            <span>Agrega un teléfono de contacto</span>
                        </li>
                    </ul>
                </div>

                <div class="sidebar-card stats-card">
                    <div class="stat-icon">
                        <ion-icon name="stats-chart-outline"></ion-icon>
                    </div>
                    <div class="stat-content">
                        <div class="stat-number">+1</div>
                        <div class="stat-label">Nueva facultad en el sistema</div>
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
                            <h1>Agregar Nueva Facultad</h1>
                            <p>Completa la información para registrar una nueva facultad</p>
                        </div>
                    </div>
                </div>

                <form action="agregarFacultad" method="post" id="formAgregarFacultad">
                    <!-- Paso 1: Información Básica -->
                    <div class="form-step">
                        <div class="step-header">
                            <div class="step-number">1</div>
                            <div class="step-info">
                                <h3>Información Básica</h3>
                                <p>Datos principales de la facultad</p>
                            </div>
                        </div>

                        <div class="input-wrapper">
                            <div class="input-icon">
                                <ion-icon name="school"></ion-icon>
                            </div>
                            <div class="input-content">
                                <label for="nombre">Nombre de la Facultad *</label>
                                <input 
                                    type="text" 
                                    id="nombre" 
                                    name="nombre" 
                                    class="form-input" 
                                    placeholder="Ej: Facultad de Ingeniería"
                                    required>
                            </div>
                        </div>

                        <div class="input-wrapper">
                            <div class="input-icon">
                                <ion-icon name="location"></ion-icon>
                            </div>
                            <div class="input-content">
                                <label for="direccion">Dirección</label>
                                <input 
                                    type="text" 
                                    id="direccion" 
                                    name="direccion" 
                                    class="form-input" 
                                    placeholder="Ej: Av. Pellegrini 250, Rosario">
                                <small class="input-hint">Incluye calle, número y ciudad</small>
                            </div>
                        </div>
                    </div>

                    <!-- Paso 2: Datos de Contacto -->
                    <div class="form-step">
                        <div class="step-header">
                            <div class="step-number">2</div>
                            <div class="step-info">
                                <h3>Datos de Contacto</h3>
                                <p>Información para comunicarse con la facultad</p>
                            </div>
                        </div>

                        <div class="input-wrapper">
                            <div class="input-icon">
                                <ion-icon name="mail"></ion-icon>
                            </div>
                            <div class="input-content">
                                <label for="emailContacto">Email de Contacto</label>
                                <input 
                                    type="email" 
                                    id="emailContacto" 
                                    name="emailContacto" 
                                    class="form-input" 
                                    placeholder="contacto@facultad.edu.ar">
                                <small class="input-hint">Usa un correo institucional</small>
                            </div>
                        </div>

                        <div class="input-wrapper">
                            <div class="input-icon">
                                <ion-icon name="call"></ion-icon>
                            </div>
                            <div class="input-content">
                                <label for="telefono">Teléfono</label>
                                <input 
                                    type="text" 
                                    id="telefono" 
                                    name="telefono" 
                                    class="form-input" 
                                    placeholder="+54 341 4801234">
                                <small class="input-hint">Incluye código de área</small>
                            </div>
                        </div>
                    </div>

                    <!-- Botones de Acción -->
                    <div class="form-actions">
                        <a href="listaFacultades" class="btn btn-cancel">
                            <ion-icon name="close-circle"></ion-icon>
                            <span>Cancelar</span>
                        </a>
                        <button type="submit" class="btn btn-submit">
                            <ion-icon name="checkmark-circle"></ion-icon>
                            <span>Guardar Facultad</span>
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
        const inputs = document.querySelectorAll('.form-input');
        
        inputs.forEach(input => {
            input.addEventListener('input', function() {
                const wrapper = this.closest('.input-wrapper');
                
                if (this.value.trim() !== '') {
                    wrapper.classList.add('has-value');
                    
                    // Validación específica por tipo
                    if (this.type === 'email') {
                        const isValid = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(this.value);
                        wrapper.classList.toggle('is-valid', isValid);
                        wrapper.classList.toggle('is-invalid', !isValid && this.value.length > 0);
                    } else if (this.required) {
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

        // Confirmación antes de salir si hay datos
        let formHasData = false;
        const form = document.getElementById('formAgregarFacultad');
        
        inputs.forEach(input => {
            input.addEventListener('input', () => {
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
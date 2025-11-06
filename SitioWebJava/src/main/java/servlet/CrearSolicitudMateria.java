package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.DataSolicitudMateria;
import entities.SolicitudMateria;
import entities.Usuario;

/**
 * Servlet para crear solicitudes de nuevas materias
 */
@WebServlet("/CrearSolicitudMateria")
public class CrearSolicitudMateria extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        // Configurar respuesta JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            // Verificar que el usuario esté logueado
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("usuario") == null) {
                response.getWriter().print("{\"success\": false, \"message\": \"Debes iniciar sesión\"}");
                return;
            }
            
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            
            // Obtener parámetros del formulario
            String nombreMateria = request.getParameter("nombreMateria");
            String descripcion = request.getParameter("descripcion");
            String idCarreraStr = request.getParameter("idCarrera");
            
            // Debug
            System.out.println("===== DEBUG SERVLET =====");
            System.out.println("nombreMateria recibido: [" + nombreMateria + "]");
            System.out.println("descripcion recibida: [" + descripcion + "]");
            System.out.println("idCarrera recibido: [" + idCarreraStr + "]");
            System.out.println("========================");
            
            // Validaciones
            if (nombreMateria == null || nombreMateria.trim().isEmpty()) {
                System.out.println("ERROR: nombreMateria es null o vacío");
                response.getWriter().print("{\"success\": false, \"message\": \"El nombre de la materia es obligatorio\"}");
                return;
            }
            
            if (idCarreraStr == null || idCarreraStr.trim().isEmpty()) {
                response.getWriter().print("{\"success\": false, \"message\": \"Debes seleccionar una carrera primero\"}");
                return;
            }
            
            int idCarrera = Integer.parseInt(idCarreraStr);
            
            // Crear objeto solicitud
            SolicitudMateria solicitud = new SolicitudMateria();
            solicitud.setNombreMateria(nombreMateria.trim());
            solicitud.setDescripcion(descripcion != null ? descripcion.trim() : "");
            solicitud.setIdCarrera(idCarrera);
            solicitud.setIdUsuarioSolicitante(usuario.getId());
            solicitud.setEstado("PENDIENTE");
            
            // Guardar en BD
            DataSolicitudMateria dataSolicitud = new DataSolicitudMateria();
            dataSolicitud.crearSolicitud(solicitud);
            
            // Log para debugging
            System.out.println("===== SOLICITUD CREADA =====");
            System.out.println("ID Solicitud: " + solicitud.getIdSolicitud());
            System.out.println("Materia: " + nombreMateria);
            System.out.println("Carrera ID: " + idCarrera);
            System.out.println("Usuario: " + usuario.getNombre() + " " + usuario.getApellido());
            System.out.println("============================");
            
            // Respuesta exitosa
            response.getWriter().print("{\"success\": true, \"message\": \"Solicitud enviada correctamente. " +
                    "Los administradores la revisarán pronto.\"}");
            
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.getWriter().print("{\"success\": false, \"message\": \"Datos inválidos\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print("{\"success\": false, \"message\": \"Error al procesar la solicitud: " + 
                    e.getMessage() + "\"}");
        }
    }
}
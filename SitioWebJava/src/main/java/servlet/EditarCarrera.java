package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.DataCarrera;
import entities.Carrera;
import entities.Usuario;

@WebServlet({ "/EditarCarrera", "/editarcarrera", "/Editarcarrera", "/editarCarrera", "/EDITARCARRERA" })
public class EditarCarrera extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    public EditarCarrera() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Verificar sesión
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("Signin");
            return;
        }
        
        // Verificar que el usuario sea administrador
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (!"administrador".equalsIgnoreCase(usuario.getRol())) {
            response.sendRedirect("bienvenida.jsp");
            return;
        }
        
        // Obtener ID de la carrera a editar
        String idCarreraStr = request.getParameter("id");
        
        if (idCarreraStr == null || idCarreraStr.trim().isEmpty()) {
            response.sendRedirect("listaCarreras");
            return;
        }
        
        try {
            int idCarrera = Integer.parseInt(idCarreraStr);
            
            // Obtener datos de la carrera
            DataCarrera dataCarrera = new DataCarrera();
            Carrera carrera = dataCarrera.getById(idCarrera);
            
            if (carrera == null) {
                response.sendRedirect("listaCarreras");
                return;
            }
            
            // Obtener ID de la facultad asociada
            int idFacultad = dataCarrera.getFacultadByCarrera(idCarrera);
            
            // Pasar datos al JSP
            request.setAttribute("carrera", carrera);
            request.setAttribute("idFacultad", idFacultad);
            
            // Forward al JSP de edición
            request.getRequestDispatcher("/WEB-INF/EditarCarrera.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("listaCarreras");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Verificar sesión y permisos
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("Signin");
            return;
        }
        
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (!"administrador".equalsIgnoreCase(usuario.getRol())) {
            response.sendRedirect("bienvenida.jsp");
            return;
        }
        
        request.setCharacterEncoding("UTF-8");
        
        // Recibir parámetros
        String idCarreraStr = request.getParameter("idCarrera");
        String nombreCarrera = request.getParameter("nombreCarrera");
        String idFacultadStr = request.getParameter("idFacultad");
        
        // Validar parámetros
        if (idCarreraStr == null || nombreCarrera == null || nombreCarrera.trim().isEmpty() ||
            idFacultadStr == null || idFacultadStr.trim().isEmpty()) {
            
            request.setAttribute("error", "Debe completar todos los campos");
            doGet(request, response);
            return;
        }
        
        try {
            int idCarrera = Integer.parseInt(idCarreraStr);
            int idFacultadNueva = Integer.parseInt(idFacultadStr);
            
            // Actualizar carrera
            DataCarrera dataCarrera = new DataCarrera();
            boolean exito = dataCarrera.actualizarCarrera(idCarrera, nombreCarrera.trim(), idFacultadNueva);
            
            if (exito) {
                System.out.println("✓ Carrera actualizada exitosamente (ID: " + idCarrera + ")");
                response.sendRedirect("listaCarreras");
            } else {
                request.setAttribute("error", "Error al actualizar la carrera");
                doGet(request, response);
            }
            
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "Datos inválidos");
            doGet(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error inesperado: " + e.getMessage());
            doGet(request, response);
        }
    }
}
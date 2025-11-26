package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.DataCarrera;
import entities.Usuario;

@WebServlet({ "/EliminarCarrera", "/eliminarcarrera", "/Eliminarcarrera", "/eliminarCarrera", "/ELIMINARCARRERA" })
public class EliminarCarrera extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    public EliminarCarrera() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Redirigir a la lista
        response.sendRedirect("listaCarreras");
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
        
        // Obtener ID de la carrera a eliminar
        String idCarreraStr = request.getParameter("idCarrera");
        
        if (idCarreraStr == null || idCarreraStr.trim().isEmpty()) {
            response.sendRedirect("listaCarreras");
            return;
        }
        
        try {
            int idCarrera = Integer.parseInt(idCarreraStr);
            
            // Eliminar carrera
            DataCarrera dataCarrera = new DataCarrera();
            boolean exito = dataCarrera.eliminarCarrera(idCarrera);
            
            if (exito) {
                System.out.println("✓ Carrera eliminada exitosamente (ID: " + idCarrera + ")");
            } else {
                System.out.println("✗ Error al eliminar carrera (ID: " + idCarrera + ")");
            }
            
            // Redirigir a la lista actualizada
            response.sendRedirect("listaCarreras");
            
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("listaCarreras");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("listaCarreras");
        }
    }
}
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

@WebServlet({ "/AgregarCarrera", "/agregarcarrera", "/Agregarcarrera", "/agregarCarrera", "/AGREGARCARRERA" })
public class AgregarCarrera extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    public AgregarCarrera() {
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
        
        // Forward al JSP de agregar carrera
        request.getRequestDispatcher("/WEB-INF/AgregarCarrera.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Verificar sesión y permisos también en POST
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
        
        // Recibir parámetros del formulario
        String nombreCarrera = request.getParameter("nombreCarrera");
        String idFacultadStr = request.getParameter("idFacultad");
        
        // Validar parámetros obligatorios
        if (nombreCarrera == null || nombreCarrera.trim().isEmpty() || 
            idFacultadStr == null || idFacultadStr.trim().isEmpty()) {
            
            request.setAttribute("error", "Debe completar todos los campos obligatorios");
            request.getRequestDispatcher("/WEB-INF/AgregarCarrera.jsp").forward(request, response);
            return;
        }
        
        try {
            int idFacultad = Integer.parseInt(idFacultadStr);
            
            // Crear objeto Carrera (solo nombre, el ID es autoincremental)
            Carrera carrera = new Carrera();
            carrera.setNombreCarrera(nombreCarrera.trim());
            
            // Verificar si la carrera ya existe para esa facultad
            DataCarrera dataCarrera = new DataCarrera();
            
            if (dataCarrera.existeCarreraEnFacultad(nombreCarrera.trim(), idFacultad)) {
                // La carrera ya existe para esa facultad
                request.setAttribute("error", "La carrera '" + nombreCarrera.trim() + 
                                             "' ya está registrada en la facultad seleccionada.");
                request.getRequestDispatcher("/WEB-INF/AgregarCarrera.jsp").forward(request, response);
                return;
            }
            
            // Guardar en la Base de Datos
            boolean exito = dataCarrera.agregarCarrera(carrera, idFacultad);
            
            if (exito) {
                // Redirigir a la lista actualizada
                response.sendRedirect("listaCarreras");
            } else {
                request.setAttribute("error", "Error al guardar la carrera. Puede que ya exista.");
                request.getRequestDispatcher("/WEB-INF/AgregarCarrera.jsp").forward(request, response);
            }
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Datos inválidos");
            request.getRequestDispatcher("/WEB-INF/AgregarCarrera.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error inesperado: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/AgregarCarrera.jsp").forward(request, response);
        }
    }
}
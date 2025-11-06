package servlet;
import java.io.IOException;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import data.DataFacultad;
import entities.Facultad;
import entities.Usuario;

@WebServlet({ "/AgregarFacultad", "/agregarfacultad", "/Agregarfacultad", "/agregarFacultad", "/AGREGARFACULTAD" })
public class AgregarFacultad extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    public AgregarFacultad() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
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
        
        request.getRequestDispatcher("/WEB-INF/AgregFacultad.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
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
        String nombre = request.getParameter("nombre");
        String direccion = request.getParameter("direccion");
        String email = request.getParameter("emailContacto");
        String telefono = request.getParameter("telefono");
        
        // Crear objeto facultad 
        Facultad f = new Facultad();
        f.setNombre(nombre);
        f.setDireccion(direccion);
        f.setEmailContacto(email);
        f.setTelefono(telefono);
        f.setFechaAlta(LocalDate.now());
        
        // Guardar en la Base de Datos 
        DataFacultad df = new DataFacultad();
        df.agregarFacultad(f);
        
        // Redirigir a la lista actualizada
        response.sendRedirect("listaFacultades");
    }
}
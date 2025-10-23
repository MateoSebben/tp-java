package servlet;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import data.DataFacultad;
import entities.Facultad;
import entities.Usuario;
import logic.Login;

@WebServlet({ "/Listafacultades", "/ListaFacultades", "/LISTAFACULTADES", "/listaFacultades", "/listafacultades" })
public class Listafacultades extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    public Listafacultades() {
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
            // Redirigir con mensaje de error
            request.setAttribute("error", "No tienes permisos para acceder a esta sección");
            response.sendRedirect("bienvenida.jsp");
            return;
        }
        
        // Si es administrador, continuar normalmente
        List<Facultad> lista = new DataFacultad().getAllFacultades();
        request.setAttribute("listaFacultades", lista);
        request.getRequestDispatcher("/WEB-INF/UniversityManagement.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario usu = new Usuario();
        Login ctrl = new Login();
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        usu.setEmail(email);
        usu.setPassword(password);
        
        usu = ctrl.validate(usu);
        
        if (usu != null) {
            // Login válido
            LinkedList<Facultad> facultades = ctrl.getAllFacultades();
            request.getSession().setAttribute("usuario", usu);
            request.setAttribute("listaFacultades", facultades);
            request.getRequestDispatcher("WEB-INF/UniversityManagement.jsp").forward(request, response);
      
        } else {
            // Login inválido
            request.setAttribute("error", "Email o contraseña incorrectos");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
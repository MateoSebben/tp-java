package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import entities.Usuario;
import logic.Login;

/**
 * Servlet implementation class Signin
 */
@WebServlet({ "/Signin", "/SIGNIN", "/signin", "/SignIn", "/signIn" })
public class Signin extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Signin() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/Signin.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        // Validaciones básicas
        if (email == null || email.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Email y contraseña son requeridos");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
        
        // Crear usuario y validar
        Usuario usu = new Usuario();
        usu.setEmail(email.trim());
        usu.setPassword(password);
        
        Login ctrl = new Login();
        Usuario usuarioValidado = ctrl.validate(usu);
        
        if (usuarioValidado != null) {
            // Login válido - REDIRIGIR A BIENVENIDA
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuarioValidado);
            
            // Redirigir a la página de bienvenida
            response.sendRedirect("bienvenida.jsp");
            
        } else {
            // Login inválido
            request.setAttribute("error", "Email o contraseña incorrectos");
            request.setAttribute("email", email); // Mantener el email en el formulario
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
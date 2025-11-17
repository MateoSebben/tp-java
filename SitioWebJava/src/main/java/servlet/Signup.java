package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.DataUsuario;
import entities.Usuario;
import logic.Login;

/**
 * Servlet implementation class Signup
 */
@WebServlet({ "/Signup", "/SIGNUP", "/signup", "/signUp", "/SignUp" })
public class Signup extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Signup() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/Registrarse.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

    	request.setCharacterEncoding("UTF-8");
    	
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        DataUsuario du = new DataUsuario();

        // Verificar si existe el mail
        if (du.existeEmail(email)) {
            request.setAttribute("error", "Ya existe un usuario con ese email.");
            request.getRequestDispatcher("/WEB-INF/Registrarse.jsp").forward(request, response);
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Las contraseñas no coinciden.");
            request.getRequestDispatcher("/WEB-INF/Registrarse.jsp").forward(request, response);
            return;
        }

        try {
            // Generar salt
            String salt = Login.generateSalt().trim();

            // Hashear contraseña con salt
            String hashedPassword = Login.hashPassword(password, salt).trim();

            // Crear Usuario
            Usuario nuevo = new Usuario();
            nuevo.setNombre(nombre);
            nuevo.setApellido(apellido);
            nuevo.setEmail(email);
            nuevo.setPassword(hashedPassword);
            nuevo.setSalt(salt);
            nuevo.setRol("usuario");

            // Guardar en BD
            du.add(nuevo);
            
            // Después de guardar el usuario, necesitamos recuperarlo de la BD
            // para tener el ID y todos los datos completos
            Usuario usuarioRegistrado = du.getByEmail(email);
            
            if (usuarioRegistrado != null) {
                // Crear sesión y guardar el usuario completo
                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuarioRegistrado);
                
                // Redirigir a Bienvenida (ahora con sesión activa)
                response.sendRedirect("bienvenida.jsp");
            } else {
                // Si por alguna razón no se pudo recuperar el usuario
                request.setAttribute("error", "Usuario registrado pero no se pudo iniciar sesión automáticamente.");
                request.getRequestDispatcher("/WEB-INF/Registrarse.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Hubo un problema al registrar el usuario: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/Registrarse.jsp").forward(request, response);
        }
    }
}
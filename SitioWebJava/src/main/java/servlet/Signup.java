package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.DataUsuario;
import entities.Usuario;

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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/Registrarse.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		// Obtener parámetros del formulario
		
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
		
		// Crear Usuario y Guardar
		
		Usuario nuevo = new Usuario();
		nuevo.setNombre(nombre);
		nuevo.setApellido(apellido);
		nuevo.setEmail(email);
		nuevo.setPassword(password);
		nuevo.setRol("usuario");
		
		
		du.add(nuevo); //se agrega a la bd
		
		//Redirigir a Welcome 
		response.sendRedirect("bienvenida.jsp");
		
		
	}

}
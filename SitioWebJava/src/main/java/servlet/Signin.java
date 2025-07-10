package servlet;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("get at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usu = new Usuario();
		Login ctrl = new Login();
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		//System.out.println("EMAIL: " + email);
		//System.out.println("PASSWORD: " + password);
		
		// validar email y password
		
		usu.setEmail(email);
		usu.setPassword(password);
		
		usu = ctrl.validate(usu);
		/*LinkedList<Usuario> usuarios = ctrl.getAll();
	
		
		request.getSession().setAttribute("usuario", usu);
		request.setAttribute("listaUsuarios", usuarios);
		
		request.getRequestDispatcher("WEB-INF/UserManagement.jsp").forward(request, response);*/
		
		if (usu != null) {
	        // Login válido
	        LinkedList<Usuario> usuarios = ctrl.getAll();

	        request.getSession().setAttribute("usuario", usu);
	        request.setAttribute("listaUsuarios", usuarios);

	        request.getRequestDispatcher("WEB-INF/UserManagement.jsp").forward(request, response);
	  
	    } else {
	        // Login inválido
	        request.setAttribute("error", "Email o contraseña incorrectos");
	        request.getRequestDispatcher("login.jsp").forward(request, response);
	    }
		
		
		
		/*usu = ctrl.validate(usu);

		if (usu != null) {
		    System.out.println("Nombre recuperado: " + usu.getNombre());
		    System.out.println("Apellido recuperado: " + usu.getApellido());

		    response.getWriter().append("Bienvenido ").append(usu.getNombre()).append(" ").append(usu.getApellido());
		} else {
		    // Login fallido: usuario no encontrado
		    response.getWriter().append("Email o contraseña incorrectos.");
		}*/

	}

}

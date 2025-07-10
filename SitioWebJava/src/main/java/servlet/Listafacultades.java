package servlet;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.DataFacultad;
import entities.Facultad ;
import entities.Usuario;
import logic.Login;

/**
 * Servlet implementation class Listafacultades
 */
@WebServlet({ "/Listafacultades", "/ListaFacultades", "/LISTAFACULTADES", "/listaFacultades", "/listafacultades" })
public class Listafacultades extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Listafacultades() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Facultad> lista = new DataFacultad().getAllFacultades();
        request.setAttribute("listaFacultades", lista);
        request.getRequestDispatcher("/WEB-INF/UniversityManagement.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usu = new Usuario();
		Login ctrl = new Login();
		
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		// validar email y password 
		
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

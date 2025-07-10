package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.DataFacultad;
import entities.Facultad;

/**
 * Servlet implementation class EditarFacultad
 */
@WebServlet({ "/EditarFacultad", "/EDITARFACULTAD", "/editarfacultad", "/Editarfacultad", "/editarFacultad" })
public class EditarFacultad extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditarFacultad() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		int id = Integer.parseInt(request.getParameter("id"));
	    Facultad facu = new DataFacultad().getById(id);
	    request.setAttribute("facultadEditar", facu);
	    request.getRequestDispatcher("/WEB-INF/EditFacultad.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Facultad f = new Facultad();
	    f.setId(Integer.parseInt(request.getParameter("id")));
	    f.setNombre(request.getParameter("nombre"));
	    f.setDireccion(request.getParameter("direccion"));
	    f.setEmailContacto(request.getParameter("emailContacto"));
	    f.setTelefono(request.getParameter("telefono"));

	    new DataFacultad().editarFacultad(f);

	    response.sendRedirect("listaFacultades");
	    
	}

}

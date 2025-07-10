package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.DataFacultad;

/**
 * Servlet implementation class EliminarFacultad
 */
@WebServlet({ "/EliminarFacultad", "/eliminarfacultad", "/ELIMINARFACULTAD", "/Eliminarfacultad", "/eliminarFacultad" })
public class EliminarFacultad extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EliminarFacultad() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int idFacultad = Integer.parseInt(request.getParameter("idFac"));
		DataFacultad df = new DataFacultad();
		df.eliminarFacultad(idFacultad);
		response.sendRedirect("listaFacultades");
		

		
	}

}

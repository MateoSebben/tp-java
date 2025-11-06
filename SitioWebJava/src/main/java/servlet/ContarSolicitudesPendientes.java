package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.DataSolicitudMateria;


/**
 * Servlet implementation class ContarSolicitudesPendientes
 */
@WebServlet({ "/ContarSolicitudesPendientes", "/contarSolicitudesPendientes", "/contarsolicitudespendientes", "/CONTARSOLICITUDESPENDIENTES" })
public class ContarSolicitudesPendientes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContarSolicitudesPendientes() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 response.setContentType("application/json");
	     response.setCharacterEncoding("UTF-8");
	        
	        try {
	            DataSolicitudMateria dataSolicitud = new DataSolicitudMateria();
	            int total = dataSolicitud.contarSolicitudesPendientes();
	            
	            response.getWriter().print("{\"total\": " + total + "}");
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	            response.getWriter().print("{\"total\": 0}");
	        }
	    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}

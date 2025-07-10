package servlet;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.DataFacultad;
import entities.Facultad;

/**
 * Servlet implementation class AgregarFacultad
 */
@WebServlet({ "/AgregarFacultad", "/agregarfacultad", "/Agregarfacultad", "/agregarFacultad", "/AGREGARFACULTAD" })
public class AgregarFacultad extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AgregarFacultad() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getRequestDispatcher("/WEB-INF/AgregFacultad.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Recibir parametros del formulario
		
		String nombre = request.getParameter("nombre");
		String direccion = request.getParameter("direccion");
		String email = request.getParameter("emailContacto");
		String telefono = request.getParameter("telefono");
		
		// Creamos objeto facultad 
		
		Facultad f = new Facultad();
		f.setNombre(nombre);
		f.setDireccion(direccion);
		f.setEmailContacto(email);
		f.setTelefono(telefono);
		f.setFechaAlta(LocalDate.now());
		
		// Guardar en la Base de Datos 
		
		DataFacultad df = new DataFacultad();
		df.agregarFacultad(f);
		
		// Redirigimos a la lista actualizada
		
		response.sendRedirect("listaFacultades");
		
	}

}

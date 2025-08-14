package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.DataArchivo;
import entities.Archivo;

/**
 * Servlet implementation class ListaArchivos
 */
@WebServlet({ "/ListaArchivos", "/listaarchivos", "/Listaarchivos", "/listaArchivos", "/LISTAARCHIVOS" })
public class ListaArchivos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListaArchivos() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
            // Instancia de la capa de datos
            DataArchivo da = new DataArchivo();

            // Si tenés filtros en SQL, podés adaptarlo, por ahora uso getAll
            List<Archivo> archivos = da.getAllArchivos();

            // Pasar lista al JSP
            request.setAttribute("archivos", archivos);

            // Redirigir al JSP que creamos
            request.getRequestDispatcher("/WEB-INF/FileManagement.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al obtener la lista de archivos: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/FileManagement.jsp").forward(request, response);
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}

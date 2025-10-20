package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import data.DataMateria;
import entities.Materia;

/**
 * Servlet implementation class ObtenerMateriasPorCarrera
 */
@WebServlet({ "/ObtenerMateriasPorCarrera", "/obtenermateriasporcarrera", "/OBTENERMATERIASPORCARRERA" })
public class ObtenerMateriasPorCarrera extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ObtenerMateriasPorCarrera() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Configurar respuesta como JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        
        try {
            // Obtener el ID de la carrera
            String idCarreraParam = request.getParameter("idCarrera");
            
            if (idCarreraParam == null || idCarreraParam.isEmpty()) {
                out.print("[]"); // Retornar array vacío si no hay parámetro
                return;
            }
            
            int idCarrera = Integer.parseInt(idCarreraParam);
            
            // Obtener materias de la BD
            DataMateria dataMateria = new DataMateria();
            LinkedList<Materia> materias = dataMateria.getMateriasByCarrera(idCarrera);
            
            // Convertir a JSON usando Gson
            Gson gson = new Gson();
            String json = gson.toJson(materias);
            
            // Enviar respuesta
            out.print(json);
            
        } catch (NumberFormatException e) {
            out.print("[]"); // Retornar array vacío si el ID no es válido
            e.printStackTrace();
        } catch (Exception e) {
            out.print("[]");
            e.printStackTrace();
        } finally {
            out.flush();
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

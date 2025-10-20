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

import data.DataCarrera;
import entities.Carrera;
/**
 * Servlet implementation class ObtenerCarrerasPorFacultad
 */
@WebServlet({ "/ObtenerCarrerasPorFacultad", "/obtenercarrerasporfacultad", "/OBTENERCARRERASPORFACULTAD" })
public class ObtenerCarrerasPorFacultad extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ObtenerCarrerasPorFacultad() {
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
            // Obtener el ID de la facultad
            String idFacultadParam = request.getParameter("idFacultad");
            
            if (idFacultadParam == null || idFacultadParam.isEmpty()) {
                out.print("[]"); // Retornar array vacío si no hay parámetro
                return;
            }
            
            int idFacultad = Integer.parseInt(idFacultadParam);
            
            // Obtener carreras de la BD
            DataCarrera dataCarrera = new DataCarrera();
            LinkedList<Carrera> carreras = dataCarrera.getCarrerasByFacultad(idFacultad);
            
            // Convertir a JSON usando Gson
            Gson gson = new Gson();
            String json = gson.toJson(carreras);
            
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

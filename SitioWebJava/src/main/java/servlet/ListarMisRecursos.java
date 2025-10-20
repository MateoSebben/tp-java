package servlet;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.DataArchivo;
import entities.Archivo;
import entities.Usuario;

/**
 * Servlet implementation class ListarMisRecursos
 */
@WebServlet({ "/ListarMisRecursos", "/listarMisRecursos", "/listarmisrecursos", "/LISTARMISRECURSOS" })
public class ListarMisRecursos extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListarMisRecursos() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Obtener el usuario de la sesión
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("usuario") == null) {
            // Si no hay sesión, redirigir al login
            response.sendRedirect("Signin");
            return;
        }
        
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuario");
        
        // Obtener los archivos del usuario desde la base de datos
        DataArchivo da = new DataArchivo();
        LinkedList<Archivo> archivosDelUsuario = da.getArchivosPorUsuario(usuarioLogueado.getId());
        
        // Pasar los archivos al JSP
        request.setAttribute("archivos", archivosDelUsuario);
        
        request.getRequestDispatcher("/WEB-INF/MisRecursos.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}

package servlet;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.DataSolicitudMateria;
import entities.SolicitudMateria;
import entities.Usuario;

/**
 * Servlet implementation class GestionarSolicitudes
 */
@WebServlet({ "/GestionarSolicitudes", "/gestionarSolicitudes", "/GESTIONARSOLICITUDES", "/gestionarsolicitudes" })
public class GestionarSolicitudes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionarSolicitudes() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Verificar que el usuario esté logueado
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("signin");
            return;
        }
        
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        System.out.println("Rol del usuario: " + usuario.getRol());

        
        // Verificar que sea ADMINISTRADOR
        if (!"administrador".equalsIgnoreCase(usuario.getRol())) {
            request.setAttribute("error", "No tienes permisos para acceder a esta página");
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
            return;
        }
        
        // Obtener solicitudes pendientes
        DataSolicitudMateria dataSolicitud = new DataSolicitudMateria();
        LinkedList<SolicitudMateria> solicitudesPendientes = dataSolicitud.getSolicitudesPendientes();
        
        // Contar total de solicitudes
        int totalPendientes = solicitudesPendientes.size();
        
        // Pasar datos al JSP
        request.setAttribute("solicitudes", solicitudesPendientes);
        request.setAttribute("totalPendientes", totalPendientes);
        
        // Forward al JSP
        request.getRequestDispatcher("/WEB-INF/SolicitudesPendientes.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}

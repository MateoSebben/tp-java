package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.DataSolicitudMateria;
import entities.Usuario;

/**
 * Servlet implementation class ProcesarSolicitud
 */
@WebServlet({ "/ProcesarSolicitud", "/procesarSolicitud", "/procesarsolicitud", "/PROCESARSOLICITUD" })
public class ProcesarSolicitud extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProcesarSolicitud() {
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
		
		// Configurar encoding y respuesta JSON
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        
        try {
            // Verificar sesión y permisos
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("usuario") == null) {
                out.print("{\"success\": false, \"message\": \"Sesión expirada\"}");
                return;
            }
            
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            
            if (!"administrador".equalsIgnoreCase(usuario.getRol())) {
                out.print("{\"success\": false, \"message\": \"No tienes permisos\"}");
                return;
            }
            
            // Obtener parámetros
            String accion = request.getParameter("accion"); // "aprobar" o "rechazar"
            String idSolicitudStr = request.getParameter("idSolicitud");
            String motivoRechazo = request.getParameter("motivoRechazo");
            
            // Validaciones
            if (idSolicitudStr == null || idSolicitudStr.trim().isEmpty()) {
                out.print("{\"success\": false, \"message\": \"ID de solicitud inválido\"}");
                return;
            }
            
            if (accion == null || (!accion.equals("aprobar") && !accion.equals("rechazar"))) {
                out.print("{\"success\": false, \"message\": \"Acción inválida\"}");
                return;
            }
            
            int idSolicitud = Integer.parseInt(idSolicitudStr);
            int idAdministrador = usuario.getId();
            
            DataSolicitudMateria dataSolicitud = new DataSolicitudMateria();
            boolean resultado = false;
            String mensaje = "";
            
            if ("aprobar".equals(accion)) {
                // APROBAR - Crea la materia y la asocia
                resultado = dataSolicitud.aprobarSolicitud(idSolicitud, idAdministrador);
                
                if (resultado) {
                    mensaje = "Materia aprobada y creada exitosamente";
                    System.out.println("✅ Solicitud " + idSolicitud + " APROBADA por admin " + idAdministrador);
                } else {
                    mensaje = "Error al aprobar la solicitud";
                    System.out.println("❌ Error al aprobar solicitud " + idSolicitud);
                }
                
            } else if ("rechazar".equals(accion)) {
                // RECHAZAR
                if (motivoRechazo == null || motivoRechazo.trim().isEmpty()) {
                    out.print("{\"success\": false, \"message\": \"Debes especificar el motivo del rechazo\"}");
                    return;
                }
                
                resultado = dataSolicitud.rechazarSolicitud(idSolicitud, idAdministrador, motivoRechazo);
                
                if (resultado) {
                    mensaje = "Solicitud rechazada correctamente";
                    System.out.println("❌ Solicitud " + idSolicitud + " RECHAZADA por admin " + idAdministrador);
                } else {
                    mensaje = "Error al rechazar la solicitud";
                }
            }
            
            // Respuesta
            if (resultado) {
                out.print("{\"success\": true, \"message\": \"" + mensaje + "\"}");
            } else {
                out.print("{\"success\": false, \"message\": \"" + mensaje + "\"}");
            }
            
        } catch (NumberFormatException e) {
            e.printStackTrace();
            out.print("{\"success\": false, \"message\": \"Datos inválidos\"}");
        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"success\": false, \"message\": \"Error: " + e.getMessage() + "\"}");
        } finally {
            out.flush();
        }
    }
	}



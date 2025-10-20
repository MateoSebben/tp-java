package servlet;

import java.io.File;
import java.io.IOException;

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
 * Servlet implementation class EliminarArchivo
 */
@WebServlet({ "/EliminarArchivo", "/eliminarArchivo", "/eliminararchivo", "/ELIMINARARCHIVO" })
public class EliminarArchivo extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EliminarArchivo() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Verificar sesión
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("Signin");
            return;
        }
        
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuario");
        
        // Obtener el ID del archivo a eliminar
        String idParam = request.getParameter("id");
        
        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect("ListarMisRecursos");
            return;
        }
        
        try {
            int idArchivo = Integer.parseInt(idParam);
            
            // Obtener el archivo de la base de datos
            DataArchivo da = new DataArchivo();
            Archivo archivo = da.getArchivoById(idArchivo);
            
            // Verificar que el archivo existe y pertenece al usuario
            if (archivo == null || archivo.getIdUsuario() != usuarioLogueado.getId()) {
                request.setAttribute("error", "No tienes permiso para eliminar este archivo");
                response.sendRedirect("ListarMisRecursos");
                return;
            }
            
            // Eliminar el archivo físico del servidor si existe
            if (archivo.isEsFisico() && archivo.getNombreFisico() != null) {
                String rutaArchivo = getServletContext().getRealPath("/uploads/") + archivo.getNombreFisico();
                File archivoFisico = new File(rutaArchivo);
                
                if (archivoFisico.exists()) {
                    archivoFisico.delete();
                }
            }
            
            // Eliminar el registro de la base de datos
            da.eliminarArchivo(idArchivo);
            
            // Redirigir a la lista de recursos
            response.sendRedirect("ListarMisRecursos");
            
        } catch (NumberFormatException e) {
            response.sendRedirect("ListarMisRecursos");
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}

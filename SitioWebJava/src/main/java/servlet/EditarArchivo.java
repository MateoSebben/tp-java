package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.DataArchivo;
import data.DataMateria;
import entities.Archivo;
import entities.Usuario;
import java.util.LinkedList;
import entities.Materia;

/**
 * Servlet implementation class EditarArchivo
 */
@WebServlet({ "/EditarArchivo", "/editarArchivo", "/editararchivo", "/EDITARARCHIVO" })
public class EditarArchivo extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditarArchivo() {
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
        
        // Obtener el ID del archivo a editar
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
                request.setAttribute("error", "No tienes permiso para editar este archivo");
                response.sendRedirect("ListarMisRecursos");
                return;
            }
            
            // Obtener lista de materias para el formulario
            DataMateria dm = new DataMateria();
            LinkedList<Materia> materias = dm.getMateriasForDropdown();
            
            // Pasar datos al JSP
            request.setAttribute("archivo", archivo);
            request.setAttribute("materias", materias);
            
            // Forward al JSP de edición
            request.getRequestDispatcher("/WEB-INF/EditarArchivo.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendRedirect("ListarMisRecursos");
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Verificar sesión
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("Signin");
            return;
        }
        
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuario");
        
        // Recibir parámetros del formulario
        String idParam = request.getParameter("idArchivo");
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String tipoArchivo = request.getParameter("tipoArchivo");
        String idMateriaParam = request.getParameter("idMateria");
        
        try {
            int idArchivo = Integer.parseInt(idParam);
            int idMateria = Integer.parseInt(idMateriaParam);
            
            // Obtener el archivo actual
            DataArchivo da = new DataArchivo();
            Archivo archivo = da.getArchivoById(idArchivo);
            
            // Verificar que el archivo pertenece al usuario
            if (archivo == null || archivo.getIdUsuario() != usuarioLogueado.getId()) {
                request.setAttribute("error", "No tienes permiso para editar este archivo");
                response.sendRedirect("ListarMisRecursos");
                return;
            }
            
            archivo.setNombre(nombre);
            archivo.setDescripcion(descripcion);
            
            // Crear objeto Materia con el ID seleccionado
            Materia materia = new Materia();
            materia.setIdMateria(idMateria);
            archivo.setMateria(materia);
            
            // Guardar cambios en la base de datos
            da.actualizarArchivo(archivo);
            
            // Redirigir a la lista de recursos
            response.sendRedirect("ListarMisRecursos");
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Datos inválidos");
            response.sendRedirect("ListarMisRecursos");
        }
    }
}

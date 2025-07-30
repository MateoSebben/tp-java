package servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import entities.*;
//import logic.UploadHandler; POSIBLEMENTE DataArchivo
/**
 * Servlet implementation class Upload
 */
@WebServlet({ "/Upload", "/upload", "/UPLOAD" })
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024 * 5,     // 5MB
	    maxFileSize = 1024 * 1024 * 50,          // 50MB
	    maxRequestSize = 1024 * 1024 * 100       // 100MB
	)
public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String UPLOAD_DIR = "uploads";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Upload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirige a la página JSP del formulario de subida
        request.getRequestDispatcher("/WEB-INF/UploadMaterial.jsp").forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   /*    try {
            // Obtener parámetros
            String facultad = request.getParameter("facultad");
            String carrera = request.getParameter("carrera");
            String materia = request.getParameter("materia");
            String año = request.getParameter("año");
            String tipoArchivo = request.getParameter("tipoArchivo");
            String titulo = request.getParameter("titulo");
            String descripcion = request.getParameter("descripcion");
            String tags = request.getParameter("tags");

            // Archivo
            Part filePart = request.getPart("archivo");
            String fileName = new File(filePart.getSubmittedFileName()).getName();
            String appPath = request.getServletContext().getRealPath("");
            String uploadPath = appPath + File.separator + UPLOAD_DIR;

            // Crear carpeta si no existe
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();

            // Guardar archivo en disco
            String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
            filePart.write(uploadPath + File.separator + uniqueFileName);

            // Armar objeto (idealmente se hace en una clase lógica, pero lo muestro acá por claridad)
            RecursoAcademico recurso = new RecursoAcademico();
            recurso.setFacultad(facultad);
            recurso.setCarrera(carrera);
            recurso.setMateria(materia);
            recurso.setAnio(año);
            recurso.setTipoArchivo(tipoArchivo);
            recurso.setTitulo(titulo);
            recurso.setDescripcion(descripcion);
            recurso.setTags(tags);
            recurso.setArchivoOriginal(fileName);
            recurso.setArchivoNombreUnico(uniqueFileName);
            recurso.setRutaArchivo(uploadPath);

            // Guardar en base de datos (delegado a lógica)
            UploadHandler.guardarRecurso(recurso);

            // Feedback al usuario
            request.setAttribute("success", "El recurso fue subido exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al subir el archivo: " + e.getMessage());
        }

        // Volver a mostrar el formulario con mensaje
        request.getRequestDispatcher("upload.jsp").forward(request, response);
        */
    }
}
    

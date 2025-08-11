package servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import data.DataArchivo;
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
   
            try {
				//Verificacion si el usuario esta logueado
            	HttpSession session = request.getSession(false);
            	if (session == null || session.getAttribute("usuario") == null) {
            	    request.setAttribute("error", "Debes iniciar sesion para subir material.");
            	    request.getRequestDispatcher("/WEB-INF/UploadMaterial.jsp").forward(request, response);
            	    return;
            	}
            	Usuario usuario = (Usuario) session.getAttribute("usuario");

            	
            	//Obtener parametros del formulario
                String facultad = request.getParameter("facultad");
                String carrera = request.getParameter("carrera");
                String materia = request.getParameter("materia");
                String añoCursada = request.getParameter("año");
                String tipoMaterial = request.getParameter("tipoMaterial");
                String titulo = request.getParameter("titulo");
                String descripcion = request.getParameter("descripcion");
                //String tags = request.getParameter("tags");
                
                //Parte del archivo
                Part filePart = request.getPart("archivo");
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                long fileSize = filePart.getSize();
                String fileExt = fileName.substring(fileName.lastIndexOf('.') + 1);
                
                //Carpeta de destino en el servidor
                String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
                Files.createDirectories(Paths.get(uploadPath));
                
                //Guardar archivo fisicamente
                String storedFileName = System.currentTimeMillis() + "_" + fileName;
                filePart.write(uploadPath + File.separator + storedFileName);
                
                // Verificar datos recibidos
                System.out.println("===== DATOS RECIBIDOS DEL FORMULARIO =====");
                System.out.println("Facultad: " + facultad);
                System.out.println("Carrera: " + carrera);
                System.out.println("Materia: " + materia);
                System.out.println("Año de cursada: " + añoCursada);
                System.out.println("Tipo de material: " + tipoMaterial);
                System.out.println("Título: " + titulo);
                System.out.println("Descripción: " + descripcion);
                System.out.println("Archivo original: " + fileName);
                System.out.println("Tamaño archivo (bytes): " + fileSize);
                System.out.println("Extensión archivo: " + fileExt);
                System.out.println("Usuario logueado ID: " + usuario.getId());
                System.out.println("==========================================");

                
                //Crear objeto archivo
                Archivo nuevoArchivo = new Archivo();
                nuevoArchivo.setIdUsuario(usuario.getId());
                nuevoArchivo.setNombre(titulo);
                nuevoArchivo.setDescripcion(descripcion);
                nuevoArchivo.setPeso(fileSize / 1024.0 / 1024.0); //en Mb
                nuevoArchivo.setTipoArchivo(tipoMaterial);
                nuevoArchivo.setEsFisico(true);
                nuevoArchivo.setFechaSubida(new Timestamp(System.currentTimeMillis()));
                //nuevoArchivo.setRutaArchivo("uploads/" + storedFileName);
                //nuevoArchivo.setNombreOriginal(fileName);
                //nuevoArchivo.setTags(tags);
                
                //Guardamos en la BD 
                DataArchivo da = new DataArchivo();
                da.agregarArchivo(nuevoArchivo, materia);
                
                //Mensajes de exito
                request.setAttribute("success", "Archivo subido correctamente.");
                request.getRequestDispatcher("/WEB-INF/UploadMaterial.jsp").forward(request, response);
                 
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("error", "Ocurrio un error al subir el archivo: " + e.getMessage());
				request.getRequestDispatcher("/WEB-INF/UploadMaterial.jsp").forward(request, response);
			}
    }
            
}
    

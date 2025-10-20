package servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import data.DataArchivo;
import data.DataFacultad;
import data.DataMateria;
import entities.*;

@WebServlet({ "/Upload", "/upload", "/UPLOAD" })
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 5,        // 5MB
    maxFileSize = 1024 * 1024 * 50,             // 50MB
    maxRequestSize = 1024 * 1024 * 100          // 100MB
)
public class Upload extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String UPLOAD_DIR = "uploads";

    public Upload() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)    
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false); // No crear si no existe
        
        if (session == null || session.getAttribute("usuario") == null) {
            // Si el usuario NO está logueado, redirige a la página principal o de login
            // Agregamos un mensaje para que sepa por qué fue redirigido
            request.getSession().setAttribute("infoMessage", "Debes iniciar sesión para subir material.");
            response.sendRedirect(request.getContextPath() + "/Signin"); 
            return;
        }
        // ==========================================================
        
        // Cargar facultades para el dropdown
        DataFacultad df = new DataFacultad();
        LinkedList<Facultad> facultades = df.getAllFacultades();
        request.setAttribute("facultades", facultades);
        
        // Ahora cargamos la JSP SOLAMENTE si el usuario está logueado
        request.getRequestDispatcher("/WEB-INF/UploadMaterial.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)      
            throws ServletException, IOException {
        
        // Recargar facultades para el formulario (si el POST falla)
        DataFacultad df = new DataFacultad();
        LinkedList<Facultad> facultades = df.getAllFacultades();
        request.setAttribute("facultades", facultades);
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            // Si el usuario NO está logueado, redirige a la página principal o de login
            // Usamos un mensaje flash para notificar al usuario.
            request.getSession().setAttribute("infoMessage", "La sesión expiró o no iniciaste sesión. Por favor, vuelve a iniciar sesión.");
            response.sendRedirect(request.getContextPath() + "/WEB-INF/Signin.jsp"); 
            return;
        }
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        // ==========================================================
        
        try {
            // Obtener parámetros del formulario
            int idFacultad = Integer.parseInt(request.getParameter("idFacultad"));
            int idCarrera = Integer.parseInt(request.getParameter("idCarrera"));
            int idMateria = Integer.parseInt(request.getParameter("idMateria")); 
            String añoCursada = request.getParameter("año");
            String tipoMaterial = request.getParameter("tipoMaterial");
            String titulo = request.getParameter("titulo");
            String descripcion = request.getParameter("descripcion");
            
            // Parte del archivo
            Part filePart = request.getPart("archivo");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            long fileSize = filePart.getSize();
            String fileExt = fileName.substring(fileName.lastIndexOf('.') + 1);
            
            // Carpeta de destino en el servidor
            String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
            Files.createDirectories(Paths.get(uploadPath));
            
            // Guardar archivo físicamente
            String storedFileName = System.currentTimeMillis() + "_" + fileName;
            filePart.write(uploadPath + File.separator + storedFileName);
            
            // Verificar datos recibidos
            System.out.println("===== DATOS RECIBIDOS DEL FORMULARIO =====");
            System.out.println("Facultad ID: " + idFacultad);
            System.out.println("Carrera ID: " + idCarrera);
            System.out.println("Materia ID: " + idMateria);
            System.out.println("Año de cursada: " + añoCursada);
            System.out.println("Tipo de material: " + tipoMaterial);
            System.out.println("Título: " + titulo);
            System.out.println("Descripción: " + descripcion);
            System.out.println("Archivo original: " + fileName);
            System.out.println("Tamaño archivo (bytes): " + fileSize);
            System.out.println("Extensión archivo: " + fileExt);
            System.out.println("Usuario logueado ID: " + usuario.getId());
            System.out.println("==========================================");

            // Crear objeto archivo
            Archivo nuevoArchivo = new Archivo();
            nuevoArchivo.setIdUsuario(usuario.getId());
            nuevoArchivo.setNombre(titulo);
            nuevoArchivo.setExtension(fileExt);
            nuevoArchivo.setDescripcion(descripcion);
            nuevoArchivo.setPeso(fileSize / 1024.0 / 1024.0); //en MB
            nuevoArchivo.setTipoArchivo(tipoMaterial);
            nuevoArchivo.setEsFisico(true);
            nuevoArchivo.setFechaSubida(new Timestamp(System.currentTimeMillis()));
            nuevoArchivo.setNombreFisico(storedFileName);
            
            // Guardamos en la BD 
            DataArchivo da = new DataArchivo();
            da.agregarArchivo(nuevoArchivo, idMateria);
            
            // Mensaje de éxito
            request.setAttribute("success", "Archivo subido correctamente.");
            request.getRequestDispatcher("/WEB-INF/UploadMaterial.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Ocurrió un error al subir el archivo: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/UploadMaterial.jsp").forward(request, response);
        }
    }
}
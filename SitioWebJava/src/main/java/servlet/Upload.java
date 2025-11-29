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
        
        // Cargar facultades para el dropdown
        DataFacultad df = new DataFacultad();
        LinkedList<Facultad> facultades = df.getAllFacultades();
        request.setAttribute("facultades", facultades);
        
        // Cargamos JSP si el usuario esta logueado
        request.getRequestDispatcher("/WEB-INF/UploadMaterial.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)      
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            // Si el usuario NO está logueado, redirige a la página principal o de login
            // Usamos un mensaje flash para notificar al usuario.
            request.getSession().setAttribute("infoMessage", "La sesión expiró o no iniciaste sesión. Por favor, vuelve a iniciar sesión.");
            response.sendRedirect(request.getContextPath() + "/Signin"); 
            return;
        }
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        // ==========================================================
        
        request.setCharacterEncoding("UTF-8");
        
        try {
            // Obtener parámetros del formulario
            int idFacultad = Integer.parseInt(request.getParameter("idFacultad"));
            int idCarrera = Integer.parseInt(request.getParameter("idCarrera"));
            int idMateria = Integer.parseInt(request.getParameter("idMateria")); 
            String anioCursada = request.getParameter("anioCursada");
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
            System.out.println("Año de cursada: " + anioCursada);
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
            
            // Guardar ID de la carrera específica
            nuevoArchivo.setIdCarrera(idCarrera);
            
            // Guardar año de cursada
            if (anioCursada != null && !anioCursada.trim().isEmpty()) {
                try {
                    nuevoArchivo.setAnioCursada(Integer.parseInt(anioCursada));
                } catch (NumberFormatException e) {
                    System.err.println("Error al parsear año de cursada: " + anioCursada);
                }
            }
            
            // Guardamos en la BD 
            DataArchivo da = new DataArchivo();
            da.agregarArchivo(nuevoArchivo, idMateria);
            
            session.setAttribute("successMessage", "Archivo subido correctamente.");
            
            // Redirigir en lugar de forward para evitar reenvío de formulario
            response.sendRedirect(request.getContextPath() + "/upload");
            // ========================================================
            
        } catch (Exception e) {
            e.printStackTrace();
            
            session.setAttribute("errorMessage", "Ocurrió un error al subir el archivo: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/upload");
            // ====================================================
        }
    }
}
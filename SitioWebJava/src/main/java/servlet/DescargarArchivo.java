package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.DataArchivo;
import entities.Archivo;

/**
 * Servlet implementation class DescargarArchivo
 */
@WebServlet({ "/DescargarArchivo", "/descargarArchivo", "/Descargararchivo", "/DESCARGARARCHIVO", "/descargararchivo" })
public class DescargarArchivo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DescargarArchivo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(">>> Entró a DescargarArchivo");
		System.out.println("Parametro id: " + request.getParameter("id"));

		try {
            int idArchivo = Integer.parseInt(request.getParameter("id"));
            DataArchivo da = new DataArchivo();
            Archivo archivo = da.getArchivoById(idArchivo);

            if (archivo == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Archivo no encontrado en la base de datos.");
                return;
            }

            String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
            String filePath = uploadPath + File.separator + archivo.getNombreFisico();

            File file = new File(filePath);
            if (!file.exists()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "El archivo no existe en el servidor.");
                return;
            }

            response.setContentType(getServletContext().getMimeType(file.getName()));
            response.setHeader("Content-Disposition",
                    "attachment; filename=\"" + archivo.getNombre() + "." + archivo.getExtension() + "\"");
            response.setContentLengthLong(file.length());
            
            try (FileInputStream in = new FileInputStream(file);
                 var out = response.getOutputStream()) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al descargar: " + e.getMessage());
        }
    }
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

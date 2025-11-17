package servlet;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import data.DataArchivo;
import entities.Archivo;
import entities.Carrera;

/**
 * Servlet implementation class ListaArchivos con sistema de filtros
 */
@WebServlet({ "/ListaArchivos", "/listaarchivos", "/Listaarchivos", "/listaArchivos", "/LISTAARCHIVOS" })
public class ListaArchivos extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    public ListaArchivos() {
        super();
    }

    
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	request.setCharacterEncoding("UTF-8");
    	
    	try {
            // Instancia de la capa de datos
            DataArchivo da = new DataArchivo();
            List<Archivo> archivos = da.getAllArchivos();
            
            // Obtener parámetros de filtro
            String tipoArchivo = request.getParameter("tipoArchivo");
            String materia = request.getParameter("materia");
            String carrera = request.getParameter("carrera");
            String extension = request.getParameter("extension");
            String fechaDesde = request.getParameter("fechaDesde");
            String fechaHasta = request.getParameter("fechaHasta");
            String busqueda = request.getParameter("busqueda");
            
            // Aplicar filtros
            List<Archivo> archivosFiltrados = aplicarFiltros(archivos, tipoArchivo, materia, 
                                                              carrera, extension, fechaDesde, 
                                                              fechaHasta, busqueda);
            
            // Obtener opciones únicas para los selectores de filtros
            Set<String> tiposUnicos = obtenerTiposUnicos(archivos);
            Set<String> materiasUnicas = obtenerMateriasUnicas(archivos);
            Set<String> carrerasUnicas = obtenerCarrerasUnicas(archivos);
            Set<String> extensionesUnicas = obtenerExtensionesUnicas(archivos);
            
            // Pasar datos al JSP
            request.setAttribute("archivos", archivosFiltrados);
            request.setAttribute("tiposUnicos", tiposUnicos);
            request.setAttribute("materiasUnicas", materiasUnicas);
            request.setAttribute("carrerasUnicas", carrerasUnicas);
            request.setAttribute("extensionesUnicas", extensionesUnicas);
            
            // Mantener filtros seleccionados para la vista
            request.setAttribute("filtroTipo", tipoArchivo);
            request.setAttribute("filtroMateria", materia);
            request.setAttribute("filtroCarrera", carrera);
            request.setAttribute("filtroExtension", extension);
            request.setAttribute("filtroFechaDesde", fechaDesde);
            request.setAttribute("filtroFechaHasta", fechaHasta);
            request.setAttribute("filtroBusqueda", busqueda);
            
            // Redirigir al JSP
            request.getRequestDispatcher("/WEB-INF/FileManagement.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al obtener la lista de archivos: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/FileManagement.jsp").forward(request, response);
        }
    }
    
    /**
     * Aplica los filtros seleccionados a la lista de archivos
     */
    private List<Archivo> aplicarFiltros(List<Archivo> archivos, String tipoArchivo, 
                                          String materia, String carrera, String extension,
                                          String fechaDesde, String fechaHasta, String busqueda) {
        
        return archivos.stream()
            .filter(a -> filtrarPorTipo(a, tipoArchivo))
            .filter(a -> filtrarPorMateria(a, materia))
            .filter(a -> filtrarPorCarrera(a, carrera))
            .filter(a -> filtrarPorExtension(a, extension))
            .filter(a -> filtrarPorFecha(a, fechaDesde, fechaHasta))
            .filter(a -> filtrarPorBusqueda(a, busqueda))
            .toList();
    }
    
    private boolean filtrarPorTipo(Archivo a, String tipo) {
        if (tipo == null || tipo.isEmpty() || tipo.equals("todos")) return true;
        return a.getTipoArchivo() != null && a.getTipoArchivo().equalsIgnoreCase(tipo);
    }
    
    private boolean filtrarPorMateria(Archivo a, String materia) {
        if (materia == null || materia.isEmpty() || materia.equals("todas")) return true;
        return a.getMateria() != null && 
               a.getMateria().getNombreMateria() != null && 
               a.getMateria().getNombreMateria().equalsIgnoreCase(materia);
    }
    
    private boolean filtrarPorCarrera(Archivo a, String carrera) {
        if (carrera == null || carrera.isEmpty() || carrera.equals("todas")) return true;
        if (a.getMateria() == null || a.getMateria().getCarreras() == null) return false;
        
        return a.getMateria().getCarreras().stream()
            .anyMatch(c -> c != null && 
                          c.getNombreCarrera() != null && 
                          c.getNombreCarrera().equalsIgnoreCase(carrera));
    }
    
    private boolean filtrarPorExtension(Archivo a, String extension) {
        if (extension == null || extension.isEmpty() || extension.equals("todas")) return true;
        return a.getExtension() != null && a.getExtension().equalsIgnoreCase(extension);
    }
    
    private boolean filtrarPorFecha(Archivo a, String fechaDesde, String fechaHasta) {
        if ((fechaDesde == null || fechaDesde.isEmpty()) && 
            (fechaHasta == null || fechaHasta.isEmpty())) return true;
        
        if (a.getFechaSubida() == null) return false;
        
        try {
            long fechaArchivo = a.getFechaSubida().getTime();
            
            if (fechaDesde != null && !fechaDesde.isEmpty()) {
                long desde = java.sql.Date.valueOf(fechaDesde).getTime();
                if (fechaArchivo < desde) return false;
            }
            
            if (fechaHasta != null && !fechaHasta.isEmpty()) {
                long hasta = java.sql.Date.valueOf(fechaHasta).getTime();
                if (fechaArchivo > hasta) return false;
            }
            
            return true;
        } catch (Exception e) {
            return true;
        }
    }
    
    private boolean filtrarPorBusqueda(Archivo a, String busqueda) {
        if (busqueda == null || busqueda.trim().isEmpty()) return true;
        
        String termino = busqueda.toLowerCase();
        
        // Buscar en nombre
        if (a.getNombre() != null && a.getNombre().toLowerCase().contains(termino)) 
            return true;
        
        // Buscar en descripción
        if (a.getDescripcion() != null && a.getDescripcion().toLowerCase().contains(termino)) 
            return true;
        
        // Buscar en materia
        if (a.getMateria() != null && a.getMateria().getNombreMateria() != null && 
            a.getMateria().getNombreMateria().toLowerCase().contains(termino)) 
            return true;
        
        // Buscar en usuario
        if (a.getUsuario() != null) {
            String nombreCompleto = (a.getUsuario().getNombre() + " " + 
                                    a.getUsuario().getApellido()).toLowerCase();
            if (nombreCompleto.contains(termino)) return true;
        }
        
        return false;
    }
    
    // Métodos para obtener valores únicos para los filtros
    private Set<String> obtenerTiposUnicos(List<Archivo> archivos) {
        Set<String> tipos = new TreeSet<>();
        for (Archivo a : archivos) {
            if (a.getTipoArchivo() != null && !a.getTipoArchivo().isEmpty()) {
                tipos.add(a.getTipoArchivo());
            }
        }
        return tipos;
    }
    
    private Set<String> obtenerMateriasUnicas(List<Archivo> archivos) {
        Set<String> materias = new TreeSet<>();
        for (Archivo a : archivos) {
            if (a.getMateria() != null && a.getMateria().getNombreMateria() != null) {
                materias.add(a.getMateria().getNombreMateria());
            }
        }
        return materias;
    }
    
    private Set<String> obtenerCarrerasUnicas(List<Archivo> archivos) {
        Set<String> carreras = new TreeSet<>();
        for (Archivo a : archivos) {
            if (a.getMateria() != null && a.getMateria().getCarreras() != null) {
                for (Carrera c : a.getMateria().getCarreras()) {
                    if (c != null && c.getNombreCarrera() != null) {
                        carreras.add(c.getNombreCarrera());
                    }
                }
            }
        }
        return carreras;
    }
    
    private Set<String> obtenerExtensionesUnicas(List<Archivo> archivos) {
        Set<String> extensiones = new TreeSet<>();
        for (Archivo a : archivos) {
            if (a.getExtension() != null && !a.getExtension().isEmpty()) {
                extensiones.add(a.getExtension().toUpperCase());
            }
        }
        return extensiones;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
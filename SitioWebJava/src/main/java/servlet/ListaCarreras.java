package servlet;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.DataCarrera;
import entities.Usuario;

/**
 * Servlet para listar todas las carreras del sistema con filtros
 * Solo accesible para administradores
 */
@WebServlet({ "/ListaCarreras", "/listaCarreras", "/listacarreras", "/LISTACARRERAS" })
public class ListaCarreras extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    public ListaCarreras() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Verificar sesión
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("Signin");
            return;
        }
        
        // Verificar que el usuario sea administrador
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (!"administrador".equalsIgnoreCase(usuario.getRol())) {
            response.sendRedirect("bienvenida.jsp");
            return;
        }
        
        // Obtener todas las carreras
        DataCarrera dataCarrera = new DataCarrera();
        LinkedList<String[]> todasLasCarreras = dataCarrera.getAllCarrerasConFacultadArray();
        
        // Obtener parámetros de filtro
        String filtroFacultad = request.getParameter("facultad");
        String filtroBusqueda = request.getParameter("busqueda");
        
        // Aplicar filtros
        LinkedList<String[]> carrerasFiltradas = aplicarFiltros(
            todasLasCarreras, 
            filtroFacultad, 
            filtroBusqueda
        );
        
        // Obtener lista de facultades únicas para el selector
        Set<String> facultadesUnicas = obtenerFacultadesUnicas(todasLasCarreras);
        
        // Pasar datos al JSP
        request.setAttribute("carreras", carrerasFiltradas);
        request.setAttribute("facultadesUnicas", facultadesUnicas);
        request.setAttribute("filtroFacultad", filtroFacultad);
        request.setAttribute("filtroBusqueda", filtroBusqueda);
        
        // Forward al JSP
        request.getRequestDispatcher("/WEB-INF/ListaCarreras.jsp").forward(request, response);
    }
    
    /**
     * Aplica los filtros a la lista de carreras
     */
    private LinkedList<String[]> aplicarFiltros(LinkedList<String[]> carreras, 
                                                 String facultad, 
                                                 String busqueda) {
        
        return carreras.stream()
            .filter(carrera -> filtrarPorFacultad(carrera, facultad))
            .filter(carrera -> filtrarPorBusqueda(carrera, busqueda))
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    /**
     * Filtra por facultad
     * carrera[0] = idCarrera
     * carrera[1] = nombreCarrera
     * carrera[2] = nombreFacultad
     */
    private boolean filtrarPorFacultad(String[] carrera, String facultad) {
        if (facultad == null || facultad.isEmpty() || facultad.equals("todas")) {
            return true;
        }
        return carrera[2] != null && carrera[2].equalsIgnoreCase(facultad);
    }
    
    /**
     * Filtra por búsqueda de texto en nombre de carrera o facultad
     */
    private boolean filtrarPorBusqueda(String[] carrera, String busqueda) {
        if (busqueda == null || busqueda.trim().isEmpty()) {
            return true;
        }
        
        String termino = busqueda.toLowerCase().trim();
        
        // Buscar en nombre de carrera
        if (carrera[1] != null && carrera[1].toLowerCase().contains(termino)) {
            return true;
        }
        
        // Buscar en nombre de facultad
        if (carrera[2] != null && carrera[2].toLowerCase().contains(termino)) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Obtiene la lista de facultades únicas
     */
    private Set<String> obtenerFacultadesUnicas(LinkedList<String[]> carreras) {
        Set<String> facultades = new TreeSet<>();
        for (String[] carrera : carreras) {
            if (carrera[2] != null && !carrera[2].isEmpty()) {
                facultades.add(carrera[2]);
            }
        }
        return facultades;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
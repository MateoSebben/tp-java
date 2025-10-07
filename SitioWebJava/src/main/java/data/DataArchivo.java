package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import entities.Archivo;
import entities.Carrera;
import entities.Materia;
import entities.Usuario;

public class DataArchivo {
	
	public void agregarArchivo(Archivo arc, int idMateria) {
	    PreparedStatement stmt = null;
	    Connection conn = null;

	    try {
	        conn = DbConnector.getInstancia().getConn();

	        // Insertar directamente el archivo, ya tenemos idMateria válido
	        stmt = conn.prepareStatement(
	            "INSERT INTO archivo " +
	            "(idUsuario, idMateria, nombre, nombreFisico, extension, descripcion, peso, tipoArchivo, esFisico, fechaSubida) " +
	            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
	        );

	        stmt.setInt(1, arc.getIdUsuario());
	        stmt.setInt(2, idMateria);
	        stmt.setString(3, arc.getNombre());
	        stmt.setString(4, arc.getNombreFisico());
	        stmt.setString(5, arc.getExtension());
	        stmt.setString(6, arc.getDescripcion());
	        stmt.setDouble(7, arc.getPeso());
	        stmt.setString(8, arc.getTipoArchivo());
	        stmt.setBoolean(9, arc.isEsFisico());
	        stmt.setTimestamp(10, arc.getFechaSubida());

	        stmt.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (stmt != null) stmt.close();
	            if (conn != null) DbConnector.getInstancia().releaseConn();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}



/*public LinkedList<Archivo> getAllArchivos() {
    LinkedList<Archivo> archivos = new LinkedList<>();
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        Connection conn = DbConnector.getInstancia().getConn();
        String sql = 
        	    "SELECT a.idArchivo, a.nombre, a.extension, a.descripcion, a.peso, a.tipoArchivo, a.esFisico, a.fechaSubida, " +
        	    "       u.id AS idUsuario, u.nombre AS nombreUsuario, u.apellido AS apellidoUsuario, " +
        	    "       m.idMateria, m.nombre AS nombreMateria, " +
        	    "       c.idCarrera, c.nombre AS nombreCarrera " +
        	    "FROM archivo a " +
        	    "INNER JOIN usuario u ON a.idUsuario = u.id " +
        	    "INNER JOIN materia m ON a.idMateria = m.idMateria " +
        	    "LEFT JOIN carrera_materia cm ON m.idMateria = cm.idMateria " +
        	    "LEFT JOIN carrera c ON cm.idCarrera = c.idCarrera";
        
        stmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery();

        while (rs.next()) {
            Archivo archivo = new Archivo();

            archivo.setIdArchivo(rs.getInt("idArchivo"));
            archivo.setNombre(rs.getString("nombre"));
            archivo.setExtension(rs.getString("extension"));
            archivo.setDescripcion(rs.getString("descripcion"));
            archivo.setPeso(rs.getDouble("peso"));
            archivo.setTipoArchivo(rs.getString("tipoArchivo"));
            archivo.setEsFisico(rs.getBoolean("esFisico"));
            archivo.setFechaSubida(rs.getTimestamp("fechaSubida"));

            // Usuario
            Usuario usuario = new Usuario();
            usuario.setId(rs.getInt("idUsuario"));
            usuario.setNombre(rs.getString("nombreUsuario"));
            usuario.setApellido(rs.getString("apellidoUsuario"));
            archivo.setUsuario(usuario);

            // Materia
            Materia materia = new Materia();
            materia.setIdMateria(rs.getInt("idMateria"));
            materia.setNombreMateria(rs.getString("nombreMateria"));


            // Carrera asociada
            Carrera carrera = new Carrera();
            carrera.setIdCarrera(rs.getInt("idCarrera"));
            carrera.setNombreCarrera(rs.getString("nombreCarrera"));

            // Vinculamos materia con carrera
            materia.setCarreras(carrera);
            archivo.setMateria(materia);

            archivos.add(archivo);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DbConnector.getInstancia().releaseConn();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return archivos;
}*/
	
	public LinkedList<Archivo> getAllArchivos() {
	    LinkedList<Archivo> archivos = new LinkedList<>();
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    try {
	        Connection conn = DbConnector.getInstancia().getConn();
	        String sql = 
	            "SELECT a.idArchivo, a.nombre, a.extension, a.descripcion, a.peso, a.tipoArchivo, a.esFisico, a.fechaSubida, " +
	            "       u.id AS idUsuario, u.nombre AS nombreUsuario, u.apellido AS apellidoUsuario, " +
	            "       m.idMateria, m.nombre AS nombreMateria, " +
	            "       c.idCarrera, c.nombre AS nombreCarrera " +
	            "FROM archivo a " +
	            "INNER JOIN usuario u ON a.idUsuario = u.id " +
	            "INNER JOIN materia m ON a.idMateria = m.idMateria " +
	            "LEFT JOIN carrera_materia cm ON m.idMateria = cm.idMateria " +
	            "LEFT JOIN carrera c ON cm.idCarrera = c.idCarrera";

	        stmt = conn.prepareStatement(sql);
	        rs = stmt.executeQuery();

	        // Map para evitar duplicados de archivos
	        Map<Integer, Archivo> mapArchivos = new HashMap<>();

	        while (rs.next()) {
	            int idArchivo = rs.getInt("idArchivo");

	            // Si el archivo aún no fue agregado al mapa, lo creamos
	            Archivo archivo = mapArchivos.get(idArchivo);
	            if (archivo == null) {
	                archivo = new Archivo();
	                archivo.setIdArchivo(idArchivo);
	                archivo.setNombre(rs.getString("nombre"));
	                archivo.setExtension(rs.getString("extension"));
	                archivo.setDescripcion(rs.getString("descripcion"));
	                archivo.setPeso(rs.getDouble("peso"));
	                archivo.setTipoArchivo(rs.getString("tipoArchivo"));
	                archivo.setEsFisico(rs.getBoolean("esFisico"));
	                archivo.setFechaSubida(rs.getTimestamp("fechaSubida"));

	                // Usuario
	                Usuario usuario = new Usuario();
	                usuario.setId(rs.getInt("idUsuario"));
	                usuario.setNombre(rs.getString("nombreUsuario"));
	                usuario.setApellido(rs.getString("apellidoUsuario"));
	                archivo.setUsuario(usuario);

	                // Materia
	                Materia materia = new Materia();
	                materia.setIdMateria(rs.getInt("idMateria"));
	                materia.setNombreMateria(rs.getString("nombreMateria"));
	                materia.setCarreras(new LinkedList<>());
	                archivo.setMateria(materia);

	                mapArchivos.put(idArchivo, archivo);
	            }

	            // Carrera asociada
	            int idCarrera = rs.getInt("idCarrera");
	            if (idCarrera != 0) { // si tiene carrera asociada
	                Carrera carrera = new Carrera();
	                carrera.setIdCarrera(idCarrera);
	                carrera.setNombreCarrera(rs.getString("nombreCarrera"));

	                // Evitamos duplicados de carrera dentro de la materia
	                if (!archivo.getMateria().getCarreras().stream()
	                        .anyMatch(c -> c.getIdCarrera() == idCarrera)) {
	                    archivo.getMateria().getCarreras().add(carrera);
	                }
	            }
	        }

	        archivos.addAll(mapArchivos.values());

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            DbConnector.getInstancia().releaseConn();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return archivos;
	}
	
	public Archivo getArchivoById(int idArchivo) {
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    Archivo archivo = null;

	    try {
	        Connection conn = DbConnector.getInstancia().getConn();
	        String sql = "SELECT idArchivo, idUsuario, idMateria, nombre, nombreFisico, extension, descripcion, peso, tipoArchivo, esFisico, fechaSubida " +
	                     "FROM archivo WHERE idArchivo = ?";
	        stmt = conn.prepareStatement(sql);
	        stmt.setInt(1, idArchivo);
	        rs = stmt.executeQuery();

	        if (rs.next()) {
	            archivo = new Archivo();
	            archivo.setIdArchivo(rs.getInt("idArchivo"));
	            archivo.setIdUsuario(rs.getInt("idUsuario"));
	            archivo.setNombre(rs.getString("nombre"));
	            archivo.setNombreFisico(rs.getString("nombreFisico"));
	            archivo.setExtension(rs.getString("extension"));
	            archivo.setDescripcion(rs.getString("descripcion"));
	            archivo.setPeso(rs.getDouble("peso"));
	            archivo.setTipoArchivo(rs.getString("tipoArchivo"));
	            archivo.setEsFisico(rs.getBoolean("esFisico"));
	            archivo.setFechaSubida(rs.getTimestamp("fechaSubida"));
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            DbConnector.getInstancia().releaseConn();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return archivo;
	}

	
}
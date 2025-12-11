package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import entities.*;

public class DataArchivo {
	
	public void agregarArchivo(Archivo arc, int idMateria) {
	    PreparedStatement stmt = null;
	    Connection conn = null;

	    try {
	        conn = DbConnector.getInstancia().getConn();

	        stmt = conn.prepareStatement(
	            "INSERT INTO archivo " +
	            "(idUsuario, idMateria, idCarrera, anioCursada, nombre, nombreFisico, extension, descripcion, peso, tipoArchivo, esFisico, fechaSubida) " +
	            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
	        );

	        stmt.setInt(1, arc.getIdUsuario());
	        stmt.setInt(2, idMateria);
	        
	        // Guardar idCarrera específica
	        if (arc.getIdCarrera() != null) {
	            stmt.setInt(3, arc.getIdCarrera());
	        } else {
	            stmt.setNull(3, java.sql.Types.INTEGER);
	        }
	        
	        // Año de cursada
	        if (arc.getAnioCursada() != null) {
	            stmt.setInt(4, arc.getAnioCursada());
	        } else {
	            stmt.setNull(4, java.sql.Types.INTEGER);
	        }
	        
	        stmt.setString(5, arc.getNombre());
	        stmt.setString(6, arc.getNombreFisico());
	        stmt.setString(7, arc.getExtension());
	        stmt.setString(8, arc.getDescripcion());
	        stmt.setDouble(9, arc.getPeso());
	        stmt.setString(10, arc.getTipoArchivo());
	        stmt.setBoolean(11, arc.isEsFisico());
	        stmt.setTimestamp(12, arc.getFechaSubida());

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

	public LinkedList<Archivo> getArchivosPorUsuario(int idUsuario) {
	    LinkedList<Archivo> archivos = new LinkedList<>();
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    try {
	        Connection conn = DbConnector.getInstancia().getConn();
	        
	        String sql = 
	            "SELECT a.idArchivo, a.nombre, a.extension, a.descripcion, a.peso, a.tipoArchivo, " +
	            "       a.esFisico, a.fechaSubida, a.anioCursada, a.idCarrera, " +
	            "       u.id AS idUsuario, u.nombre AS nombreUsuario, u.apellido AS apellidoUsuario, " +
	            "       m.idMateria, m.nombre AS nombreMateria, " +
	            "       c.idCarrera, c.nombreCarrera AS nombreCarrera " +
	            "FROM archivo a " +
	            "INNER JOIN usuario u ON a.idUsuario = u.id " +
	            "INNER JOIN materia m ON a.idMateria = m.idMateria " +
	            "LEFT JOIN carrera c ON a.idCarrera = c.idCarrera " + 
	            "WHERE a.idUsuario = ?";

	        stmt = conn.prepareStatement(sql);
	        stmt.setInt(1, idUsuario);
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
	            
	            // Año de cursada
	            int anioCursada = rs.getInt("anioCursada");
	            if (!rs.wasNull()) {
	                archivo.setAnioCursada(anioCursada);
	            }
	            
	            // ID de carrera del archivo
	            int idCarrera = rs.getInt("idCarrera");
	            if (!rs.wasNull()) {
	                archivo.setIdCarrera(idCarrera);
	            }

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
	            archivo.setMateria(materia);
	            
	            // Solo la carrera específica
	            if (idCarrera != 0) {
	                Carrera carrera = new Carrera();
	                carrera.setIdCarrera(idCarrera);
	                carrera.setNombreCarrera(rs.getString("nombreCarrera"));
	                
	                LinkedList<Carrera> carreras = new LinkedList<>();
	                carreras.add(carrera);
	                materia.setCarreras(carreras);
	            } else {
	                materia.setCarreras(new LinkedList<>());
	            }

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
	}
	
    public void actualizarArchivo(Archivo archivo) {
        PreparedStatement stmt = null;
        Connection conn = null;

        try {
            conn = DbConnector.getInstancia().getConn();
            stmt = conn.prepareStatement(
                "UPDATE archivo SET nombre = ?, descripcion = ?, tipoArchivo = ?, idMateria = ? WHERE idArchivo = ?"
            );

            stmt.setString(1, archivo.getNombre());
            stmt.setString(2, archivo.getDescripcion());
            stmt.setString(3, archivo.getTipoArchivo());
            stmt.setInt(4, archivo.getMateria().getIdMateria());
            stmt.setInt(5, archivo.getIdArchivo());

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

    public void eliminarArchivo(int idArchivo) {
        PreparedStatement stmt = null;
        Connection conn = null;

        try {
            conn = DbConnector.getInstancia().getConn();
            stmt = conn.prepareStatement("DELETE FROM archivo WHERE idArchivo = ?");
            stmt.setInt(1, idArchivo);
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

    
    public LinkedList<Archivo> getAllArchivos() {
        LinkedList<Archivo> archivos = new LinkedList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Connection conn = DbConnector.getInstancia().getConn();
            
            String sql = 
                "SELECT a.idArchivo, a.nombre, a.extension, a.descripcion, a.peso, a.tipoArchivo, " +
                "       a.esFisico, a.fechaSubida, a.anioCursada, a.idCarrera, " +
                "       u.id AS idUsuario, u.nombre AS nombreUsuario, u.apellido AS apellidoUsuario, " +
                "       m.idMateria, m.nombre AS nombreMateria, " +
                "       c.idCarrera, c.nombreCarrera AS nombreCarrera " +
                "FROM archivo a " +
                "INNER JOIN usuario u ON a.idUsuario = u.id " +
                "INNER JOIN materia m ON a.idMateria = m.idMateria " +
                "LEFT JOIN carrera c ON a.idCarrera = c.idCarrera"; 

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
                
                // Año de cursada
                int anioCursada = rs.getInt("anioCursada");
                if (!rs.wasNull()) {
                    archivo.setAnioCursada(anioCursada);
                }
                
                // ID de carrera específica del archivo
                int idCarrera = rs.getInt("idCarrera");
                if (!rs.wasNull()) {
                    archivo.setIdCarrera(idCarrera);
                }

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
                archivo.setMateria(materia);
                
                // Solo mostrar la carrera específica del archivo
                if (idCarrera != 0) {
                    Carrera carrera = new Carrera();
                    carrera.setIdCarrera(idCarrera);
                    carrera.setNombreCarrera(rs.getString("nombreCarrera"));
                    
                    // Crear lista con solo esta carrera
                    LinkedList<Carrera> carreras = new LinkedList<>();
                    carreras.add(carrera);
                    materia.setCarreras(carreras);
                } else {
                    materia.setCarreras(new LinkedList<>());
                }

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
                
                // Cargar la materia asociada
                int idMateria = rs.getInt("idMateria");
                if (idMateria > 0) {
                    Materia materia = new Materia();
                    materia.setIdMateria(idMateria);
                    archivo.setMateria(materia);
                }
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

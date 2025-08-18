package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import entities.Archivo;
import entities.Carrera;
import entities.Materia;
import entities.Usuario;

public class DataArchivo {
	
	public void agregarArchivo(Archivo arc, String nombre) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = DbConnector.getInstancia().getConn();
            
            //Buscar si la materia existe
            int idMateria = -1;
            stmt = conn.prepareStatement("SELECT idMateria FROM materia WHERE nombre = ?");
            stmt.setString(1, nombre);
            rs = stmt.executeQuery();

            if (rs.next()) {
                idMateria = rs.getInt("idMateria");
            }
            rs.close();
            stmt.close();

            //Si no existe, crearla
            if (idMateria == -1) {
                stmt = conn.prepareStatement(
                    "INSERT INTO materia (nombre) VALUES (?)",
                    PreparedStatement.RETURN_GENERATED_KEYS
                );
                stmt.setString(1, nombre);
                stmt.executeUpdate();

                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    idMateria = rs.getInt(1);
                }
                rs.close();
                stmt.close();
            }

            //Insertar el archivo con el idMateria obtenido
            stmt = conn.prepareStatement(
                "INSERT INTO archivo " +
                "(idUsuario, idMateria, nombre, extension, descripcion, peso, tipoArchivo, esFisico, fechaSubida) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );

            stmt.setInt(1, arc.getIdUsuario());
            stmt.setInt(2, idMateria);
            stmt.setString(3, arc.getNombre());
            stmt.setString(4, arc.getExtension());
            stmt.setString(5, arc.getDescripcion());
            stmt.setDouble(6, arc.getPeso());
            stmt.setString(7, arc.getTipoArchivo());
            stmt.setBoolean(8, arc.isEsFisico());
            stmt.setTimestamp(9, arc.getFechaSubida());

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
            materia.setCarrera(carrera);
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
}
}
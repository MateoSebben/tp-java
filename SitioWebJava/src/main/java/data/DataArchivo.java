package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Archivo;

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
                "(idUsuario, idMateria, nombre, descripcion, peso, tipoArchivo, esFisico, fechaSubida) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
            );

            stmt.setInt(1, arc.getIdUsuario());
            stmt.setInt(2, idMateria);
            stmt.setString(3, arc.getNombre());
            stmt.setString(4, arc.getDescripcion());
            stmt.setDouble(5, arc.getPeso());
            stmt.setString(6, arc.getTipoArchivo());
            stmt.setBoolean(7, arc.isEsFisico());
            stmt.setTimestamp(8, arc.getFechaSubida());

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
}


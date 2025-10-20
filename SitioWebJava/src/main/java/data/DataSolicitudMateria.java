package data;

import entities.SolicitudMateria;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class DataSolicitudMateria {
	
	// Metodo para crear una solicitud de materia 
	
	public void crearSolicitud(SolicitudMateria solicitud) {
        String sql = "INSERT INTO solicitud_materia (nombreMateria, descripcion, idCarrera, " +
                     "idUsuarioSolicitante, fechaSolicitud, estado) " +
                     "VALUES (?, ?, ?, ?, ?, 'PENDIENTE')";
        
        try (PreparedStatement stmt = DbConnector.getInstancia().getConn().prepareStatement(sql, 
                Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, solicitud.getNombreMateria());
            stmt.setString(2, solicitud.getDescripcion());
            stmt.setInt(3, solicitud.getIdCarrera());
            stmt.setInt(4, solicitud.getIdUsuarioSolicitante());
            stmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            
            stmt.executeUpdate();
            
            // Obtener el ID generado
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                solicitud.setIdSolicitud(rs.getInt(1));
            }
            rs.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbConnector.getInstancia().releaseConn();
        }
    }
	
	// Metodo para obtener todas las solicitudes pendientes para el admin 
	
	public LinkedList<SolicitudMateria> getSolicitudesPendientes() {
        LinkedList<SolicitudMateria> solicitudes = new LinkedList<>();
        
        String sql = "SELECT sm.*, " +
                     "c.nombreCarrera, " +
                     "u.nombre as nombreUsuario, u.apellido as apellidoUsuario, u.email " +
                     "FROM solicitud_materia sm " +
                     "INNER JOIN carrera c ON sm.idCarrera = c.idCarrera " +
                     "INNER JOIN usuario u ON sm.idUsuarioSolicitante = u.id " +
                     "WHERE sm.estado = 'PENDIENTE' " +
                     "ORDER BY sm.fechaSolicitud DESC";
        
        try (PreparedStatement stmt = DbConnector.getInstancia().getConn().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                SolicitudMateria sm = new SolicitudMateria();
                sm.setIdSolicitud(rs.getInt("idSolicitud"));
                sm.setNombreMateria(rs.getString("nombreMateria"));
                sm.setDescripcion(rs.getString("descripcion"));
                sm.setIdCarrera(rs.getInt("idCarrera"));
                sm.setIdUsuarioSolicitante(rs.getInt("idUsuarioSolicitante"));
                sm.setFechaSolicitud(rs.getTimestamp("fechaSolicitud").toLocalDateTime());
                sm.setEstado(rs.getString("estado"));
                
             // Datos relacionados
                sm.setNombreCarrera(rs.getString("nombreCarrera"));
                sm.setNombreUsuarioSolicitante(rs.getString("nombreUsuario") + " " + 
                                               rs.getString("apellidoUsuario"));
                sm.setEmailUsuarioSolicitante(rs.getString("email"));
                
                solicitudes.add(sm);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbConnector.getInstancia().releaseConn();
        }
        
        return solicitudes;
    }
	
	// Metodo para obtener solicitudes por ID 
	
	public SolicitudMateria getById(int idSolicitud) {
        SolicitudMateria sm = null;
        
        String sql = "SELECT sm.*, c.nombreCarrera, " +
                     "u.nombre as nombreUsuario, u.apellido as apellidoUsuario, u.email " +
                     "FROM solicitud_materia sm " +
                     "INNER JOIN carrera c ON sm.idCarrera = c.idCarrera " +
                     "INNER JOIN usuario u ON sm.idUsuarioSolicitante = u.id " +
                     "WHERE sm.idSolicitud = ?";
        
        try (PreparedStatement stmt = DbConnector.getInstancia().getConn().prepareStatement(sql)) {
            stmt.setInt(1, idSolicitud);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                sm = new SolicitudMateria();
                sm.setIdSolicitud(rs.getInt("idSolicitud"));
                sm.setNombreMateria(rs.getString("nombreMateria"));
                sm.setDescripcion(rs.getString("descripcion"));
                sm.setIdCarrera(rs.getInt("idCarrera"));
                sm.setIdUsuarioSolicitante(rs.getInt("idUsuarioSolicitante"));
                sm.setFechaSolicitud(rs.getTimestamp("fechaSolicitud").toLocalDateTime());
                sm.setEstado(rs.getString("estado"));
                sm.setNombreCarrera(rs.getString("nombreCarrera"));
                sm.setNombreUsuarioSolicitante(rs.getString("nombreUsuario") + " " + 
                                               rs.getString("apellidoUsuario"));
                sm.setEmailUsuarioSolicitante(rs.getString("email"));
            }
            
            rs.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbConnector.getInstancia().releaseConn();
        }
        
        return sm;
    }
	
	// Metodo para aprobar la solicitud, crear la materia y asociarla con la carrera 
	
	public boolean aprobarSolicitud(int idSolicitud, int idAdministrador) {
        Connection conn = null;
        
        try {
            conn = DbConnector.getInstancia().getConn();
            conn.setAutoCommit(false); // Iniciar transacción
            
            // 1. Obtener datos de la solicitud
            SolicitudMateria solicitud = getById(idSolicitud);
            
            if (solicitud == null) {
                return false;
            }
            
            // 2. Crear la materia
            String sqlMateria = "INSERT INTO materia (nombre) VALUES (?)";
            PreparedStatement stmtMateria = conn.prepareStatement(sqlMateria, 
                    Statement.RETURN_GENERATED_KEYS);
            stmtMateria.setString(1, solicitud.getNombreMateria());
            stmtMateria.executeUpdate();
            
            // Obtener ID de la materia creada
            ResultSet rsMateria = stmtMateria.getGeneratedKeys();
            int idMateria = 0;
            if (rsMateria.next()) {
                idMateria = rsMateria.getInt(1);
            }
            rsMateria.close();
            stmtMateria.close();
            
            // 3. Asociar materia con carrera en tabla intermedia
            String sqlCarreraMateria = "INSERT INTO carrera_materia (idCarrera, idMateria, fecha) VALUES (?, ?, ?)";
            PreparedStatement stmtCarreraMateria = conn.prepareStatement(sqlCarreraMateria);
            stmtCarreraMateria.setInt(1, solicitud.getIdCarrera());
            stmtCarreraMateria.setInt(2, idMateria);
            stmtCarreraMateria.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now())); // <-- fecha actual
            stmtCarreraMateria.executeUpdate();
            stmtCarreraMateria.close();
            
            // 4. Actualizar estado de la solicitud
            String sqlUpdate = "UPDATE solicitud_materia SET estado = 'APROBADA', " +
                             "idAdministrador = ?, fechaResolucion = ? WHERE idSolicitud = ?";
            PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate);
            stmtUpdate.setInt(1, idAdministrador);
            stmtUpdate.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            stmtUpdate.setInt(3, idSolicitud);
            stmtUpdate.executeUpdate();
            stmtUpdate.close();
            
            // Confirmar transacción
            conn.commit();
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback(); // Revertir cambios si hay error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            DbConnector.getInstancia().releaseConn();
        }
    }
	
	// Metodo para rechazar solicitud 
	
	public boolean rechazarSolicitud(int idSolicitud, int idAdministrador, String motivoRechazo) {
        String sql = "UPDATE solicitud_materia SET estado = 'RECHAZADA', " +
                     "idAdministrador = ?, motivoRechazo = ?, fechaResolucion = ? " +
                     "WHERE idSolicitud = ?";
        
        try (PreparedStatement stmt = DbConnector.getInstancia().getConn().prepareStatement(sql)) {
            stmt.setInt(1, idAdministrador);
            stmt.setString(2, motivoRechazo);
            stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(4, idSolicitud);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DbConnector.getInstancia().releaseConn();
        }
    }
	
	// Metodo para contar solicitudes pendientes 
	
	public int contarSolicitudesPendientes() {
        String sql = "SELECT COUNT(*) as total FROM solicitud_materia WHERE estado = 'PENDIENTE'";
        int total = 0;
        
        try (PreparedStatement stmt = DbConnector.getInstancia().getConn().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                total = rs.getInt("total");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbConnector.getInstancia().releaseConn();
        }
        
        return total;
    }


}

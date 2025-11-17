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
	
	/**
	 * NUEVO: Verifica si una materia ya existe en una carrera específica
	 * @param nombreMateria Nombre de la materia a verificar
	 * @param idCarrera ID de la carrera
	 * @return true si la materia ya existe, false en caso contrario
	 */
	private boolean existeMateriaEnCarrera(String nombreMateria, int idCarrera) {
	    String sql = "SELECT COUNT(*) as total " +
	                 "FROM materia m " +
	                 "INNER JOIN carrera_materia cm ON m.idMateria = cm.idMateria " +
	                 "WHERE LOWER(TRIM(m.nombre)) = LOWER(TRIM(?)) AND cm.idCarrera = ?";
	    
	    try (PreparedStatement stmt = DbConnector.getInstancia().getConn().prepareStatement(sql)) {
	        stmt.setString(1, nombreMateria);
	        stmt.setInt(2, idCarrera);
	        
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            boolean existe = rs.getInt("total") > 0;
	            rs.close();
	            return existe;
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        DbConnector.getInstancia().releaseConn();
	    }
	    
	    return false;
	}
	
	// Metodo para aprobar la solicitud, crear la materia y asociarla con la carrera 
	// ACTUALIZADO: Ahora valida duplicados antes de crear
	
	public boolean aprobarSolicitud(int idSolicitud, int idAdministrador) {
	    Connection conn = null;
	    
	    try {
	        conn = DbConnector.getInstancia().getConn();
	        conn.setAutoCommit(false); // Iniciar transacción
	        
	        // 1. Obtener datos de la solicitud
	        SolicitudMateria solicitud = getSolicitudById(idSolicitud);
	        
	        if (solicitud == null) {
	            System.err.println("Error: Solicitud no encontrada (ID: " + idSolicitud + ")");
	            return false;
	        }
	        
	        // 2. Verificar si la materia ya existe en ESA MISMA carrera
	        if (existeMateriaEnCarrera(solicitud.getNombreMateria(), solicitud.getIdCarrera())) {
	            System.out.println("La materia '" + solicitud.getNombreMateria() + 
	                             "' ya existe en " + solicitud.getNombreCarrera());
	            
	            // Rechazar automáticamente con motivo específico
	            conn.rollback();
	            conn.setAutoCommit(true);
	            DbConnector.getInstancia().releaseConn();
	            
	            String motivoRechazo = "La materia '" + solicitud.getNombreMateria() + 
	                                 "' ya existe en el sistema para la carrera de " + 
	                                 solicitud.getNombreCarrera() + ".";
	            
	            boolean rechazada = rechazarSolicitud(idSolicitud, idAdministrador, motivoRechazo);
	            
	            if (rechazada) {
	                System.out.println("Solicitud rechazada automáticamente por duplicado");
	            }
	            
	            return rechazada;
	        }
	        
	        // 3. NUEVO: Buscar si la materia existe en OTRA carrera
	        Integer idMateriaExistente = buscarIdMateriaPorNombre(solicitud.getNombreMateria());
	        int idMateria;
	        
	        if (idMateriaExistente != null) {
	            // ✅ La materia existe en otra carrera - REUTILIZAR el ID
	            idMateria = idMateriaExistente;
	            System.out.println("Materia '" + solicitud.getNombreMateria() + 
	                             "' ya existe (ID: " + idMateria + ") - Reutilizando para nueva carrera");
	        } else {
	            // ❌ La materia NO existe en ninguna carrera - CREAR NUEVA
	            String sqlMateria = "INSERT INTO materia (nombre) VALUES (?)";
	            PreparedStatement stmtMateria = conn.prepareStatement(sqlMateria, 
	                    Statement.RETURN_GENERATED_KEYS);
	            stmtMateria.setString(1, solicitud.getNombreMateria());
	            stmtMateria.executeUpdate();
	            
	            // Obtener ID de la materia creada
	            ResultSet rsMateria = stmtMateria.getGeneratedKeys();
	            if (rsMateria.next()) {
	                idMateria = rsMateria.getInt(1);
	            } else {
	                throw new SQLException("Error al crear materia: no se generó ID");
	            }
	            rsMateria.close();
	            stmtMateria.close();
	            
	            System.out.println("Materia CREADA: '" + solicitud.getNombreMateria() + "' (ID: " + idMateria + ")");
	        }
	        
	        // 4. Asociar materia con carrera en tabla intermedia
	        String sqlCarreraMateria = "INSERT INTO carrera_materia (idCarrera, idMateria, fecha) VALUES (?, ?, ?)";
	        PreparedStatement stmtCarreraMateria = conn.prepareStatement(sqlCarreraMateria);
	        stmtCarreraMateria.setInt(1, solicitud.getIdCarrera());
	        stmtCarreraMateria.setInt(2, idMateria);
	        stmtCarreraMateria.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
	        stmtCarreraMateria.executeUpdate();
	        stmtCarreraMateria.close();
	        
	        System.out.println("Materia (ID: " + idMateria + ") asociada a carrera: " + 
	                         solicitud.getNombreCarrera() + " (ID: " + solicitud.getIdCarrera() + ")");
	        
	        // 5. Actualizar estado de la solicitud
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
	        System.out.println("✓ Solicitud " + idSolicitud + " APROBADA correctamente");
	        return true;
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.err.println("Error al aprobar solicitud: " + e.getMessage());
	        if (conn != null) {
	            try {
	                conn.rollback(); // Revertir cambios si hay error
	                System.out.println("Transacción revertida");
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
	
	// Metodo para obtener una solicitud por su ID con toda la información necesaria incluyendo el email del usuario solicitante 
	
	public SolicitudMateria getSolicitudById(int idSolicitud) {
	    SolicitudMateria solicitud = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    
	    String sql = "SELECT s.idSolicitud, s.nombreMateria, s.descripcion, s.idCarrera, " +
	                 "       s.idUsuarioSolicitante, s.fechaSolicitud, s.estado, " +
	                 "       s.motivoRechazo, s.idAdministrador, s.fechaResolucion, " +
	                 "       c.nombreCarrera, " +
	                 "       u.nombre AS nombreUsuario, u.apellido AS apellidoUsuario, u.email AS emailUsuario " +
	                 "FROM solicitud_materia s " +
	                 "INNER JOIN carrera c ON s.idCarrera = c.idCarrera " +
	                 "INNER JOIN usuario u ON s.idUsuarioSolicitante = u.id " +
	                 "WHERE s.idSolicitud = ?";
	    
	    try {
	        stmt = DbConnector.getInstancia().getConn().prepareStatement(sql);
	        stmt.setInt(1, idSolicitud);
	        rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            solicitud = new SolicitudMateria();
	            solicitud.setIdSolicitud(rs.getInt("idSolicitud"));
	            solicitud.setNombreMateria(rs.getString("nombreMateria"));
	            solicitud.setDescripcion(rs.getString("descripcion"));
	            solicitud.setIdCarrera(rs.getInt("idCarrera"));
	            solicitud.setIdUsuarioSolicitante(rs.getInt("idUsuarioSolicitante"));
	            
	            // Convertir Timestamp a LocalDateTime
	            if (rs.getTimestamp("fechaSolicitud") != null) {
	                solicitud.setFechaSolicitud(rs.getTimestamp("fechaSolicitud").toLocalDateTime());
	            }
	            
	            solicitud.setEstado(rs.getString("estado"));
	            solicitud.setMotivoRechazo(rs.getString("motivoRechazo"));
	            solicitud.setIdAdministrador((Integer) rs.getObject("idAdministrador"));
	            
	            if (rs.getTimestamp("fechaResolucion") != null) {
	                solicitud.setFechaResolucion(rs.getTimestamp("fechaResolucion").toLocalDateTime());
	            }
	            
	            // Datos auxiliares
	            solicitud.setNombreCarrera(rs.getString("nombreCarrera"));
	            String nombreCompleto = rs.getString("nombreUsuario") + " " + rs.getString("apellidoUsuario");
	            solicitud.setNombreUsuarioSolicitante(nombreCompleto);
	            solicitud.setEmailUsuarioSolicitante(rs.getString("emailUsuario"));
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
	    
	    return solicitud;
	}
	

/* Metodo para buscar si una materia ya existe en el sistema (en cualquier carrera) y devuelve su ID si existe, o null si no existe */
	
	private Integer buscarIdMateriaPorNombre(String nombreMateria) {
	    String sql = "SELECT idMateria FROM materia WHERE LOWER(TRIM(nombre)) = LOWER(TRIM(?))";
	    
	    try (PreparedStatement stmt = DbConnector.getInstancia().getConn().prepareStatement(sql)) {
	        stmt.setString(1, nombreMateria);
	        
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            int idMateria = rs.getInt("idMateria");
	            rs.close();
	            return idMateria;
	        }
	        rs.close();
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        DbConnector.getInstancia().releaseConn();
	    }
	    
	    return null;
	}
}
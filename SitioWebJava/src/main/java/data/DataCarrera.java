package data;

import entities.Carrera;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class DataCarrera {
	
	/* Metodo para obtener todas las carreras */
	
	public LinkedList<Carrera> getAllCarreras(){
		LinkedList<Carrera> carreras = new LinkedList<>();
		String sql = "SELECT idCarrera, nombreCarrera FROM carrera";
		
		try (PreparedStatement stmt = DbConnector.getInstancia().getConn().prepareStatement(sql);
	             ResultSet rs = stmt.executeQuery()) {
	            
	            while (rs.next()) {
	                Carrera c = new Carrera();
	                c.setIdCarrera(rs.getInt("idCarrera"));
	                c.setNombreCarrera(rs.getString("nombreCarrera"));
	                carreras.add(c);
	            }
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            DbConnector.getInstancia().releaseConn();
	        }
	        
	        return carreras;
	}
	
	// Metodo para obtener las carreras de una facultad

	public LinkedList<Carrera> getCarrerasByFacultad(int idFacultad) {
	    LinkedList<Carrera> carreras = new LinkedList<>();
	    java.sql.ResultSet rs = null; // Declaramos el ResultSet fuera del try-with-resources
	    
	    String sql = "SELECT c.idCarrera, c.nombreCarrera " + 
	                 "FROM carrera c " + 
	                 "INNER JOIN carrera_facultad cf ON c.idCarrera = cf.idCarrera " + 
	                 "WHERE cf.idFacultad = ?";
	    
	    try (java.sql.PreparedStatement stmt = data.DbConnector.getInstancia().getConn().prepareStatement(sql)) {
	        
	        stmt.setInt(1, idFacultad);
	        rs = stmt.executeQuery(); 
	        
	        while (rs.next()) {
	            Carrera c = new Carrera();
	            c.setIdCarrera(rs.getInt("idCarrera"));
	            c.setNombreCarrera(rs.getString("nombreCarrera"));
	            carreras.add(c);
	        }
	        
	    } catch (java.sql.SQLException e) {
	        e.printStackTrace(); 
	    } finally {
	        try { 
	        	if (rs != null) rs.close(); 
	        	}
	        catch (SQLException e) { 
	        	e.printStackTrace(); 
	        	}
	        data.DbConnector.getInstancia().releaseConn();
	    }
	    
	    return carreras;
	} 
		
	
	/* Metodo para obtener una carrera por su ID */
	
	public Carrera getById(int idCarrera) {
        Carrera c = null;
        String sql = "SELECT idCarrera, nombreCarrera FROM carrera WHERE idCarrera = ?";
        
        try (PreparedStatement stmt = DbConnector.getInstancia().getConn().prepareStatement(sql)) {
            stmt.setInt(1, idCarrera);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                c = new Carrera();
                c.setIdCarrera(rs.getInt("idCarrera"));
                c.setNombreCarrera(rs.getString("nombreCarrera"));
            }
            
            rs.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbConnector.getInstancia().releaseConn();
        }
        
        return c;
    }
	
	
	 /* Método para obtener el idFacultad de una carrera */ 
	 
	public int getFacultadByCarrera(int idCarrera) {
	    int idFacultad = 0;
	    String sql = "SELECT idFacultad FROM carrera_facultad WHERE idCarrera = ? LIMIT 1";
	    
	    try (PreparedStatement stmt = DbConnector.getInstancia().getConn().prepareStatement(sql)) {
	        stmt.setInt(1, idCarrera);
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            idFacultad = rs.getInt("idFacultad");
	        }
	        rs.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        DbConnector.getInstancia().releaseConn();
	    }
	    
	    return idFacultad;
	}


	
	// Método para agregar una nueva carrera y asociarla a una facultad

	public boolean agregarCarrera(Carrera carrera, int idFacultad) {
	    Connection conn = null;
	    PreparedStatement stmtCarrera = null;
	    PreparedStatement stmtAsociacion = null;
	    ResultSet rs = null;
	    
	    try {
	        conn = DbConnector.getInstancia().getConn();
	        conn.setAutoCommit(false); // Iniciar transacción
	        
	        // 1. Verificar si la carrera ya existe
	        if (existeCarrera(carrera.getNombreCarrera())) {
	            System.out.println("La carrera '" + carrera.getNombreCarrera() + "' ya existe en el sistema");
	            conn.rollback();
	            return false;
	        }
	        
	        // 2. Insertar la carrera en la tabla 'carrera'
	        String sqlCarrera = "INSERT INTO carrera (nombreCarrera) VALUES (?)";
	        stmtCarrera = conn.prepareStatement(sqlCarrera, Statement.RETURN_GENERATED_KEYS);
	        stmtCarrera.setString(1, carrera.getNombreCarrera());
	        
	        int rowsAffected = stmtCarrera.executeUpdate();
	        
	        if (rowsAffected == 0) {
	            conn.rollback();
	            return false;
	        }
	        
	        // Obtener el ID generado (autoincremental)
	        rs = stmtCarrera.getGeneratedKeys();
	        int idCarrera = 0;
	        if (rs.next()) {
	            idCarrera = rs.getInt(1);
	            carrera.setIdCarrera(idCarrera);
	        } else {
	            conn.rollback();
	            return false;
	        }
	        
	        // 3. Asociar la carrera con la facultad en 'carrera_facultad' con fecha
	        String sqlAsociacion = "INSERT INTO carrera_facultad (idCarrera, idFacultad, fecha) VALUES (?, ?, ?)";
	        stmtAsociacion = conn.prepareStatement(sqlAsociacion);
	        stmtAsociacion.setInt(1, idCarrera);
	        stmtAsociacion.setInt(2, idFacultad);
	        stmtAsociacion.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
	        
	        int rowsAsoc = stmtAsociacion.executeUpdate();
	        
	        if (rowsAsoc == 0) {
	            conn.rollback();
	            return false;
	        }
	        
	        // Confirmar transacción
	        conn.commit();
	        System.out.println("✓ Carrera '" + carrera.getNombreCarrera() + 
	                         "' agregada exitosamente (ID: " + idCarrera + ")");
	        return true;
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        if (conn != null) {
	            try {
	                conn.rollback();
	                System.out.println("Transacción revertida debido a error");
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }
	        return false;
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmtCarrera != null) stmtCarrera.close();
	            if (stmtAsociacion != null) stmtAsociacion.close();
	            if (conn != null) conn.setAutoCommit(true);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        DbConnector.getInstancia().releaseConn();
	    }
	}


	// Método para verificar si una carrera ya existe (por nombre)

	public boolean existeCarrera(String nombreCarrera) {
	    String sql = "SELECT COUNT(*) as total FROM carrera WHERE LOWER(TRIM(nombreCarrera)) = LOWER(TRIM(?))";
	    
	    try (PreparedStatement stmt = DbConnector.getInstancia().getConn().prepareStatement(sql)) {
	        stmt.setString(1, nombreCarrera);
	        
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

	// Método para obtener todas las carreras con el nombre de su facultad

	public LinkedList<Carrera> getAllCarrerasConFacultad() {
	    LinkedList<Carrera> carreras = new LinkedList<>();
	    
	    String sql = "SELECT c.idCarrera, c.nombreCarrera, f.nombre as nombreFacultad " +
	                 "FROM carrera c " +
	                 "LEFT JOIN carrera_facultad cf ON c.idCarrera = cf.idCarrera " +
	                 "LEFT JOIN facultad f ON cf.idFacultad = f.id " +
	                 "ORDER BY c.nombreCarrera";
	    
	    try (PreparedStatement stmt = DbConnector.getInstancia().getConn().prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {
	        
	        while (rs.next()) {
	            Carrera c = new Carrera();
	            c.setIdCarrera(rs.getInt("idCarrera"));
	            c.setNombreCarrera(rs.getString("nombreCarrera"));
	            
	            // Guardar el nombre de la facultad temporalmente
	            // (aunque no esté en la entidad Carrera, lo pasaremos al JSP)
	            String nombreFacultad = rs.getString("nombreFacultad");
	            
	            // Como Carrera solo tiene idCarrera y nombreCarrera,
	            // crearemos un objeto especial o pasaremos la info al JSP
	            carreras.add(c);
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        DbConnector.getInstancia().releaseConn();
	    }
	    
	    return carreras;
	}

	

	// Método que devuelve un LinkedList de arrays de String con [idCarrera, nombreCarrera, nombreFacultad]

	public LinkedList<String[]> getAllCarrerasConFacultadArray() {
	    LinkedList<String[]> carreras = new LinkedList<>();
	    
	    String sql = "SELECT c.idCarrera, c.nombreCarrera, COALESCE(f.nombre, 'Sin facultad') as nombreFacultad " +
	                 "FROM carrera c " +
	                 "LEFT JOIN carrera_facultad cf ON c.idCarrera = cf.idCarrera " +
	                 "LEFT JOIN facultad f ON cf.idFacultad = f.id " +
	                 "ORDER BY c.nombreCarrera";
	    
	    try (PreparedStatement stmt = DbConnector.getInstancia().getConn().prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {
	        
	        while (rs.next()) {
	            String[] carreraData = new String[3];
	            carreraData[0] = String.valueOf(rs.getInt("idCarrera"));
	            carreraData[1] = rs.getString("nombreCarrera");
	            carreraData[2] = rs.getString("nombreFacultad");
	            
	            carreras.add(carreraData);
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        DbConnector.getInstancia().releaseConn();
	    }
	    
	    return carreras;
	}
	
	// Metodo para verificar si una carrera ya existe para una determinada facultad 
	
	public boolean existeCarreraEnFacultad(String nombreCarrera, int idFacultad) {
	    String sql = "SELECT COUNT(*) as total " +
	                 "FROM carrera c " +
	                 "INNER JOIN carrera_facultad cf ON c.idCarrera = cf.idCarrera " +
	                 "WHERE LOWER(TRIM(c.nombreCarrera)) = LOWER(TRIM(?)) AND cf.idFacultad = ?";
	    
	    try (PreparedStatement stmt = DbConnector.getInstancia().getConn().prepareStatement(sql)) {
	        stmt.setString(1, nombreCarrera);
	        stmt.setInt(2, idFacultad);
	        
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
	
	// Metodo para actualizar una carrera 
	
	public boolean actualizarCarrera(int idCarrera, String nuevoNombre, int nuevaIdFacultad) {
	    Connection conn = null;
	    
	    try {
	        conn = DbConnector.getInstancia().getConn();
	        conn.setAutoCommit(false); // Iniciar transacción
	        
	        // 1. Actualizar el nombre de la carrera
	        String sqlCarrera = "UPDATE carrera SET nombreCarrera = ? WHERE idCarrera = ?";
	        PreparedStatement stmtCarrera = conn.prepareStatement(sqlCarrera);
	        stmtCarrera.setString(1, nuevoNombre);
	        stmtCarrera.setInt(2, idCarrera);
	        stmtCarrera.executeUpdate();
	        stmtCarrera.close();
	        
	        // 2. Actualizar la asociación con facultad
	        // Primero eliminar la asociación actual
	        String sqlDeleteAsoc = "DELETE FROM carrera_facultad WHERE idCarrera = ?";
	        PreparedStatement stmtDelete = conn.prepareStatement(sqlDeleteAsoc);
	        stmtDelete.setInt(1, idCarrera);
	        stmtDelete.executeUpdate();
	        stmtDelete.close();
	        
	        // Luego crear la nueva asociación
	        String sqlInsertAsoc = "INSERT INTO carrera_facultad (idCarrera, idFacultad, fecha) VALUES (?, ?, ?)";
	        PreparedStatement stmtInsert = conn.prepareStatement(sqlInsertAsoc);
	        stmtInsert.setInt(1, idCarrera);
	        stmtInsert.setInt(2, nuevaIdFacultad);
	        stmtInsert.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
	        stmtInsert.executeUpdate();
	        stmtInsert.close();
	        
	        conn.commit();
	        System.out.println("✓ Carrera actualizada exitosamente (ID: " + idCarrera + ")");
	        return true;
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        if (conn != null) {
	            try {
	                conn.rollback();
	                System.out.println("Transacción revertida debido a error");
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }
	        return false;
	    } finally {
	        try {
	            if (conn != null) conn.setAutoCommit(true);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        DbConnector.getInstancia().releaseConn();
	    }
	}
	
	// Metodo para eliminar una carrera
	
	public boolean eliminarCarrera(int idCarrera) {
	    Connection conn = null;
	    
	    try {
	        conn = DbConnector.getInstancia().getConn();
	        conn.setAutoCommit(false); // Iniciar transacción
	        
	        // 1. Eliminar asociaciones en carrera_facultad
	        String sqlAsociacion = "DELETE FROM carrera_facultad WHERE idCarrera = ?";
	        PreparedStatement stmtAsociacion = conn.prepareStatement(sqlAsociacion);
	        stmtAsociacion.setInt(1, idCarrera);
	        stmtAsociacion.executeUpdate();
	        stmtAsociacion.close();
	        
	        // 2. Eliminar la carrera
	        String sqlCarrera = "DELETE FROM carrera WHERE idCarrera = ?";
	        PreparedStatement stmtCarrera = conn.prepareStatement(sqlCarrera);
	        stmtCarrera.setInt(1, idCarrera);
	        int rowsAffected = stmtCarrera.executeUpdate();
	        stmtCarrera.close();
	        
	        if (rowsAffected > 0) {
	            conn.commit();
	            System.out.println("✓ Carrera eliminada exitosamente (ID: " + idCarrera + ")");
	            return true;
	        } else {
	            conn.rollback();
	            return false;
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        if (conn != null) {
	            try {
	                conn.rollback();
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }
	        return false;
	    } finally {
	        try {
	            if (conn != null) conn.setAutoCommit(true);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        DbConnector.getInstancia().releaseConn();
	    }
	}
}

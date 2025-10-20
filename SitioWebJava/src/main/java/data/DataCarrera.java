package data;

import entities.Carrera;
import java.sql.*;
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

}

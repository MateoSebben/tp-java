package data;

import entities.*;

import java.sql.*;
import java.util.LinkedList;

public class DataFacultad {

	public LinkedList<Facultad> getAllFacultades() {
        Statement stmt = null;
        ResultSet rs = null;
        LinkedList<Facultad> facultades = new LinkedList<>();

        try {
            stmt = DbConnector.getInstancia().getConn().createStatement();
            rs = stmt.executeQuery("SELECT * FROM facultad");

            while (rs.next()) {
                Facultad f = new Facultad();
                f.setId(rs.getInt("id"));
                f.setNombre(rs.getString("nombre"));
                f.setDireccion(rs.getString("direccion"));
                f.setEmailContacto(rs.getString("emailContacto"));
                f.setTelefono(rs.getString("telefono"));
                if (rs.getDate("fechaAlta") != null) {
                    f.setFechaAlta(rs.getDate("fechaAlta").toLocalDate());
                }
                facultades.add(f);
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

        return facultades;
    }
	
	public void eliminarFacultad(int id) {
		PreparedStatement stmt = null; 
		try {
			Connection conn = DbConnector.getInstancia().getConn();
			stmt = conn.prepareStatement("DELETE  FROM facultad WHERE id = ?");
			stmt.setInt(1, id);
			stmt.executeUpdate();
			stmt.close();
			DbConnector.getInstancia().releaseConn();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
	}
	
	public void agregarFacultad(Facultad fac) {
		PreparedStatement stmt = null;
		ResultSet keyResultSet = null;
		try {
			Connection conn = DbConnector.getInstancia().getConn();
			stmt = conn.prepareStatement("INSERT INTO facultad(nombre, direccion, emailContacto, telefono) VALUES (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, fac.getNombre());
			stmt.setString(2, fac.getDireccion());
			stmt.setString(3, fac.getEmailContacto());
			stmt.setString(4, fac.getTelefono());
			stmt.executeUpdate();
			
			keyResultSet=stmt.getGeneratedKeys();
			 if(keyResultSet!=null && keyResultSet.next()){
	                fac.setId(keyResultSet.getInt(1));
	            }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(keyResultSet!=null)keyResultSet.close();
                if(stmt!=null)stmt.close();
                DbConnector.getInstancia().releaseConn();
				
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			
		}
	}
	
	public void editarFacultad(Facultad fac) {
		PreparedStatement stmt = null;

		try {
			Connection conn = DbConnector.getInstancia().getConn();
			stmt = conn.prepareStatement("UPDATE facultad " + "set nombre=?, direccion=?, emailContacto=?, telefono=? "+"WHERE id=?" );
			stmt.setString(1, fac.getNombre());
			stmt.setString(2, fac.getDireccion());
			stmt.setString(3, fac.getEmailContacto());
			stmt.setString(4, fac.getTelefono());
			stmt.setInt(5, fac.getId());
			
			stmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(stmt!=null)stmt.close();
				 DbConnector.getInstancia().releaseConn();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	public Facultad getById(int id) {
		Facultad f = null; 
		PreparedStatement stmt = null;
		ResultSet rs = null; 
		
		try {
			Connection conn = DbConnector.getInstancia().getConn();
			stmt = conn.prepareStatement("SELECT * FROM facultad WHERE id=? ");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			if (rs != null && rs.next()) {
	            f = new Facultad();
	            f.setId(rs.getInt("id"));
	            f.setNombre(rs.getString("nombre"));
	            f.setDireccion(rs.getString("direccion"));
	            f.setEmailContacto(rs.getString("emailContacto"));
	            f.setTelefono(rs.getString("telefono"));
	            f.setFechaAlta(rs.getDate("fechaAlta").toLocalDate());
	        }

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            DbConnector.getInstancia().releaseConn();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		return f; 
	}
	

 
}

/*package data;

import entities.*;
import java.sql.*;
import java.util.LinkedList;

public class DataUsuario {
	public LinkedList<Usuario> getAll(){
		//DataRol dr=new DataRol();
		Statement stmt=null;
		ResultSet rs=null;
		LinkedList<Usuario> usua= new LinkedList<>();
		
		try {
			stmt= DbConnector.getInstancia().getConn().createStatement();
			rs= stmt.executeQuery("select id,nombre,apellido,email,rol from usuario");
			//intencionalmente no se recupera la password
			if(rs!=null) {
				while(rs.next()) {
					Usuario u=new Usuario();
					u.setId(rs.getInt("id"));
					u.setNombre(rs.getString("nombre"));
					u.setApellido(rs.getString("apellido"));
					u.setEmail(rs.getString("email"));
					u.setRol(rs.getString("rol"));
					
					//dr.setRoles(p);
					
					usua.add(u);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			try {
				if(rs!=null) {rs.close();}
				if(stmt!=null) {stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return usua;
	}
	
	public Usuario getByUser(Usuario usu) {
		//DataRol dr=new DataRol();
		Usuario u=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			stmt=DbConnector.getInstancia().getConn().prepareStatement(
					"select id,nombre,apellido,email,rol from usuario where email=? and password=?"
					);
			stmt.setString(1, usu.getEmail());
			stmt.setString(2, usu.getPassword());
			rs=stmt.executeQuery();
			if(rs!=null && rs.next()) {
				u=new Usuario();
				u.setId(rs.getInt("id"));
				u.setNombre(rs.getString("nombre"));
				u.setApellido(rs.getString("apellido"));
				u.setEmail(rs.getString("email"));
				u.setRol(rs.getString("rol"));
				//dr.setRoles(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) {rs.close();}
				if(stmt!=null) {stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return u;
	}
	
	public void add(Usuario u) {
		PreparedStatement stmt= null;
		ResultSet keyResultSet=null;
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"insert into usuario(nombre, apellido, email, password, rol) values(?,?,?,?,?)",
							PreparedStatement.RETURN_GENERATED_KEYS
							);
			stmt.setString(1, u.getNombre());
			stmt.setString(2, u.getApellido());
			stmt.setString(3, u.getEmail());
			stmt.setString(4, u.getPassword());
			stmt.setString(5, u.getRol());
			stmt.executeUpdate();
			
			keyResultSet=stmt.getGeneratedKeys();
            if(keyResultSet!=null && keyResultSet.next()){
                u.setId(keyResultSet.getInt(1));
            }
            
            //DataRol dr = new DataRol();
            //dr.setRolesDePersona(p);

			
		}  catch (SQLException e) {
            e.printStackTrace();
		} finally {
            try {
                if(keyResultSet!=null)keyResultSet.close();
                if(stmt!=null)stmt.close();
                DbConnector.getInstancia().releaseConn();
            } catch (SQLException e) {
            	e.printStackTrace();
            }
		}
    }

}
*/


package data;

import entities.Usuario;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.LinkedList;
public class DataUsuario {

    public LinkedList<Usuario> getAll() {
        
        Statement stmt = null;
        ResultSet rs = null;
        LinkedList<Usuario> usua = new LinkedList<>();
        Connection conn = null; // Declarar Connection aquí

        try {
            // Obtener la conexión a la base de datos.
            // Esto puede lanzar una SQLException si la conexión falla.
            conn = DbConnector.getInstancia().getConn();

            // Preparar y ejecutar la consulta.
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT id, nombre, apellido, email, rol FROM usuario");

            // Procesar los resultados.
            // Intencionalmente no se recupera la password por seguridad.
            if (rs != null) {
                while (rs.next()) {
                    Usuario u = new Usuario();
                    u.setId(rs.getInt("id"));
                    u.setNombre(rs.getString("nombre"));
                    u.setApellido(rs.getString("apellido"));
                    u.setEmail(rs.getString("email"));
                    u.setRol(rs.getString("rol"));

                    usua.add(u);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) { rs.close(); }
                if (stmt != null) { stmt.close(); }
                if (conn != null) { DbConnector.getInstancia().releaseConn(); }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return usua;
    }

    public Usuario getByUser(Usuario usu) {
        Usuario u = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection conn = null; 

        try {
            conn = DbConnector.getInstancia().getConn();

            stmt = conn.prepareStatement(
                    "SELECT id, nombre, apellido, email, password, rol FROM usuario WHERE email=? AND password=?"
            );
            stmt.setString(1, usu.getEmail());
            stmt.setString(2, usu.getPassword()); 

            // Ejecutar la consulta.
            rs = stmt.executeQuery();

            // Procesar el resultado 
            if (rs != null && rs.next()) {
                u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password")); 
                u.setSalt(rs.getString("salt"));         
                u.setRol(rs.getString("rol")); 

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) { rs.close(); }
                if (stmt != null) { stmt.close(); }
                if (conn != null) { DbConnector.getInstancia().releaseConn(); }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return u;
    }
    
    
    public void add(Usuario u) throws NoSuchAlgorithmException {
        PreparedStatement stmt = null;
        ResultSet keyResultSet = null;
        Connection conn = null;

        try {
            conn = DbConnector.getInstancia().getConn();

            // Generar salt y hash con la contraseña que viene en el Usuario
           // Ya viene hasheada desde el servlet Signup
            System.out.println("Contraseña (hash) recibida: " + u.getPassword());
            System.out.println("Salt recibido: " + u.getSalt());

            
            stmt = conn.prepareStatement(
                "INSERT INTO usuario(nombre, apellido, email, password, salt, rol) VALUES(?,?,?,?,?,?)",
                PreparedStatement.RETURN_GENERATED_KEYS
            );
            stmt.setString(1, u.getNombre());
            stmt.setString(2, u.getApellido());
            stmt.setString(3, u.getEmail());
            stmt.setString(4, u.getPassword()); 
            stmt.setString(5, u.getSalt());     
            stmt.setString(6, u.getRol());

            stmt.executeUpdate();

            keyResultSet = stmt.getGeneratedKeys();
            if (keyResultSet != null && keyResultSet.next()) {
                u.setId(keyResultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (keyResultSet != null) keyResultSet.close();
                if (stmt != null) stmt.close();
                if (conn != null) DbConnector.getInstancia().releaseConn();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean existeEmail(String email) {
    	PreparedStatement stmt = null;
    	ResultSet rs = null;
    	boolean existe = false;
    	
    	try {
			Connection conn = DbConnector.getInstancia().getConn();
			stmt = conn.prepareStatement("SELECT 1 FROM usuario WHERE email=?");
			stmt.setString(1, email);
			rs = stmt.executeQuery();
			
			if (rs != null && rs.next()) {
				existe = true;				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				if (rs != null ) rs.close();
				if (stmt != null) stmt.close();
				DbConnector.getInstancia().releaseConn();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
    	return existe;
    }
    
    public Usuario getByEmail(String email) {
        Usuario u = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection conn = null;

        try {
            conn = DbConnector.getInstancia().getConn();
            stmt = conn.prepareStatement(
                "SELECT id, nombre, apellido, email, password, salt, rol FROM usuario WHERE email=?"
            );
            stmt.setString(1, email);
            rs = stmt.executeQuery();

            if (rs != null && rs.next()) {
                u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password").trim()); 
                u.setSalt(rs.getString("salt").trim());         
                u.setRol(rs.getString("rol"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) DbConnector.getInstancia().releaseConn();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return u;
    }

    
}
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
import java.sql.*;
import java.util.LinkedList;

public class DataUsuario {

    public LinkedList<Usuario> getAll() {
        //DataRol dr=new DataRol(); // Comentado si no usas roles externos
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

                    //dr.setRoles(p); // Lógica para roles externos, si aplica.

                    usua.add(u);
                }
            }

        } catch (SQLException e) {
            // Captura cualquier error SQL que ocurra, incluida la falla de conexión.
            e.printStackTrace();
            // Considera lanzar una excepción personalizada (ej. DataAccessException)
            // para una mejor gestión de errores en la capa superior.
        } finally {
            // Asegúrate de cerrar los recursos en el orden inverso al que se abrieron.
            try {
                if (rs != null) { rs.close(); }
                if (stmt != null) { stmt.close(); }
                // Solo libera la conexión si fue obtenida exitosamente.
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
        Connection conn = null; // Declarar Connection aquí

        try {
            // Obtener la conexión a la base de datos.
            // Esto puede lanzar una SQLException si la conexión falla.
            conn = DbConnector.getInstancia().getConn();

            // Preparar la sentencia SQL con parámetros.
            // Se seleccionan todos los atributos necesarios, incluyendo 'rol'.
            stmt = conn.prepareStatement(
                    "SELECT id, nombre, apellido, email, password, rol FROM usuario WHERE email=? AND password=?"
            );
            stmt.setString(1, usu.getEmail());
            stmt.setString(2, usu.getPassword()); // ¡RECORDATORIO DE SEGURIDAD: Usar hashing para passwords!

            // Ejecutar la consulta.
            rs = stmt.executeQuery();

            // Procesar el resultado (solo se espera un usuario).
            if (rs != null && rs.next()) {
                u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password")); // ¡NO DEVOLVER PASSWORD EN UN OBJETO USUARIO REAL EN PRODUCCIÓN!
                u.setRol(rs.getString("rol")); // Asigna el rol directamente de la tabla 'usuario'.

                //dr.setRoles(p); // Lógica para roles externos, si aplica.
            }

        } catch (SQLException e) {
            // Captura cualquier error SQL, incluida la falla de conexión.
            e.printStackTrace();
            // Puedes lanzar una excepción personalizada para mejor manejo en capas superiores.
        } finally {
            // Cerrar recursos de forma segura.
            try {
                if (rs != null) { rs.close(); }
                if (stmt != null) { stmt.close(); }
                // Solo libera la conexión si fue obtenida.
                if (conn != null) { DbConnector.getInstancia().releaseConn(); }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return u;
    }

    public void add(Usuario u) {
        PreparedStatement stmt = null;
        ResultSet keyResultSet = null;
        Connection conn = null; // Declarar Connection aquí

        try {
            // Obtener la conexión a la base de datos.
            // Esto puede lanzar una SQLException si la conexión falla.
            conn = DbConnector.getInstancia().getConn();

            // Preparar la sentencia INSERT con retorno de claves generadas.
            stmt = conn.prepareStatement(
                    "INSERT INTO usuario(nombre, apellido, email, password, rol) VALUES(?,?,?,?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            stmt.setString(1, u.getNombre());
            stmt.setString(2, u.getApellido());
            stmt.setString(3, u.getEmail());
            stmt.setString(4, u.getPassword()); // ¡RECORDATORIO DE SEGURIDAD: Hashing para passwords!
            stmt.setString(5, u.getRol());

            // Ejecutar la actualización.
            stmt.executeUpdate();

            // Obtener la clave primaria generada (ID).
            keyResultSet = stmt.getGeneratedKeys();
            if (keyResultSet != null && keyResultSet.next()) {
                u.setId(keyResultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Lanza una excepción para notificar el fallo de la operación de inserción.
        } finally {
            // Cerrar recursos de forma segura.
            try {
                if (keyResultSet != null) { keyResultSet.close(); }
                if (stmt != null) { stmt.close(); }
                // Solo libera la conexión si fue obtenida.
                if (conn != null) { DbConnector.getInstancia().releaseConn(); }
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
    
}
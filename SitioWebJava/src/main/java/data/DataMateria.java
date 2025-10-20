package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import entities.*;

public class DataMateria {
	
	public LinkedList<Materia> getMateriasForDropdown() {
        LinkedList<Materia> materias = new LinkedList<>();
        String sql = "SELECT idMateria, nombre FROM materia";

        try (PreparedStatement stmt = DbConnector.getInstancia().getConn().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Materia m = new Materia();
                m.setIdMateria(rs.getInt("idMateria"));
                m.setNombreMateria(rs.getString("nombre"));
                materias.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbConnector.getInstancia().releaseConn();
        }

        return materias;
    }
	
	/* Metodo para obtener las materias que pertenecen a una carrera especifica */
	
	public LinkedList<Materia> getMateriasByCarrera(int idCarrera) {
        LinkedList<Materia> materias = new LinkedList<>();
        
        String sql = "SELECT m.idMateria, m.nombre " +
                     "FROM materia m " +
                     "INNER JOIN carrera_materia cm ON m.idMateria = cm.idMateria " +
                     "WHERE cm.idCarrera = ?";
        
        try (PreparedStatement stmt = DbConnector.getInstancia().getConn().prepareStatement(sql)) {
            stmt.setInt(1, idCarrera);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Materia m = new Materia();
                m.setIdMateria(rs.getInt("idMateria"));
                m.setNombreMateria(rs.getString("nombre"));
                materias.add(m);
            }
            
            rs.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbConnector.getInstancia().releaseConn();
        }
        
        return materias;
    }

}

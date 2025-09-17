package entities;

import java.util.LinkedList;

public class Materia {
	
	private int idMateria;
	private String nombreMateria;
	private LinkedList<Carrera> carreras = new LinkedList<>();
	
	public int getIdMateria() {
		return idMateria;
	}
	public void setIdMateria(int idMateria) {
		this.idMateria = idMateria;
	}
	public String getNombreMateria() {
		return nombreMateria;
	}
	public void setNombreMateria(String nombreMateria) {
		this.nombreMateria = nombreMateria;
	}
	
	public LinkedList<Carrera> getCarreras() {
		return carreras;
	}
	public void setCarreras(LinkedList<Carrera> carreras) {
		this.carreras = carreras;
	}
	@Override
	public String toString() {
		return "Materia [idMateria=" + idMateria + ", nombreMateria=" + nombreMateria + "]";
	}
	
	
	
	
}



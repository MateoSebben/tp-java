package entities;

public class Carrera {
	
	private int idCarrera;
	private String nombreCarrera;
	
	
	public int getIdCarrera() {
		return idCarrera;
	}
	public void setIdCarrera(int idCarrera) {
		this.idCarrera = idCarrera;
	}
	public String getNombreCarrera() {
		return nombreCarrera;
	}
	public void setNombreCarrera(String nombreCarrera) {
		this.nombreCarrera = nombreCarrera;
	}
	
	@Override
	public String toString() {
		return "Carrera [idCarrera=" + idCarrera + ", nombreCarrera=" + nombreCarrera + "]";
	}
	
	

	
	
}

package entities;

public class Carrera {
	
	private int idCarrera;
	private String nombreCarrera;
	private int plan;
	
	
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
	public int getPlan() {
		return plan;
	}
	public void setPlan(int plan) {
		this.plan = plan;
	}
	
	
	@Override
	public String toString() {
		return "Carrera [idCarrera=" + idCarrera + ", nombreCarrera=" + nombreCarrera + ", plan=" + plan + "]";
	}
	
	
}

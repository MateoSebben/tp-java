package entities;

public class Materia {
	
	private int idMateria;
	private String nombreMateria;
	
	
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
	
	@Override
	public String toString() {
		return "Materia [idMateria=" + idMateria + ", nombreMateria=" + nombreMateria + "]";
	}
	
	
	
	
}



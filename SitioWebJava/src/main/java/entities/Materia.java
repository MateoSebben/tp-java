package entities;

public class Materia {
	
	private int idMateria;
	private String nombreMateria;
	private int anioCursado;
	
	
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
	public int getAnioCursado() {
		return anioCursado;
	}
	public void setAnioCursado(int anioCursado) {
		this.anioCursado = anioCursado;
	}
	@Override
	public String toString() {
		return "Materia [idMateria=" + idMateria + ", nombreMateria=" + nombreMateria + ", anioCursado=" + anioCursado
				+ "]";
	}
	
	
}



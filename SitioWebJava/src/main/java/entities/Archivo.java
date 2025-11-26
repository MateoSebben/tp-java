package entities;

import java.sql.Timestamp;


public class Archivo {
	
	private int idArchivo;
	private int idUsuario;
	private int idMateria;
	private int idCarrera;
	private Usuario usuario;
	private Materia materia;
	private String nombre;
	private String extension;
	private String descripcion;
	private double peso;
	private int anioCursada;
	private String tipoArchivo;
	private boolean esFisico;
	private Timestamp fechaSubida;
	private String nombreFisico;
	
	
	
	public int getIdArchivo() {
		return idArchivo;
	}
	public void setIdArchivo(int idArchivo) {
		this.idArchivo = idArchivo;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public int getIdMateria() {
		return idMateria;
	}
	public void setIdMateria(int idMateria) {
		this.idMateria = idMateria;
	}
	
	public Integer getIdCarrera() {
	    return idCarrera;
	}

	public void setIdCarrera(Integer idCarrera) {
	    this.idCarrera = idCarrera;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Materia getMateria() {
		return materia;
	}
	public void setMateria(Materia materia) {
		this.materia = materia;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getExtension() {
		return extension;
	}
	
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	
	public Integer getAnioCursada() {
	    return anioCursada;
	}

	public void setAnioCursada(Integer anioCursada) {
	    this.anioCursada = anioCursada;
	}
	public String getTipoArchivo() {
		return tipoArchivo;
	}
	public void setTipoArchivo(String tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}
	public boolean isEsFisico() {
		return esFisico;
	}
	public void setEsFisico(boolean esFisico) {
		this.esFisico = esFisico;
	}
	public Timestamp getFechaSubida() {
		return fechaSubida;
	}
	public void setFechaSubida(Timestamp fechaSubida) {
		this.fechaSubida = fechaSubida;
	}
	
	public String getNombreFisico() {
		return nombreFisico;
	}
	public void setNombreFisico(String nombreFisico) {
		this.nombreFisico = nombreFisico;
	}
	
	@Override
	public String toString() {
		return "Archivo [idArchivo=" + idArchivo + ", idUsuario=" + idUsuario + ", idMateria=" + idMateria
				+ ", idCarrera=" + idCarrera + ", usuario=" + usuario + ", materia=" + materia + ", nombre=" + nombre
				+ ", extension=" + extension + ", descripcion=" + descripcion + ", peso=" + peso + ", anioCursada="
				+ anioCursada + ", tipoArchivo=" + tipoArchivo + ", esFisico=" + esFisico + ", fechaSubida="
				+ fechaSubida + ", nombreFisico=" + nombreFisico + "]";
	}	
	
}
	



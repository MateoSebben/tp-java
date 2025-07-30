package entities;

import java.security.Timestamp;

public class Archivo {
	
	private int idArchivo;
	private int idUsuario;
	private int idMateria;
	private Usuario usuario;
	private Materia materia;
	private String nombre;
	private String descripcion;
	private double peso;
	private String tipoArchivo;
	private boolean esFisico;
	private Timestamp fechaSubida;
	
	
	
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
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
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
	@Override
	public String toString() {
		return "Archivo [idArchivo=" + idArchivo + ", idUsuario=" + idUsuario + ", idMateria=" + idMateria
				+ ", usuario=" + usuario + ", materia=" + materia + ", nombre=" + nombre + ", descripcion="
				+ descripcion + ", peso=" + peso + ", tipoArchivo=" + tipoArchivo + ", esFisico=" + esFisico
				+ ", fechaSubida=" + fechaSubida + "]";
	}

	
	
}
	



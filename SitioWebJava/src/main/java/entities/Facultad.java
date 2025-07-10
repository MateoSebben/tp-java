package entities;

import java.time.LocalDate;

public class Facultad {

	private int id;
	private String nombre;
	private String direccion; 
	private String emailContacto;
	private String telefono;
	private LocalDate fechaAlta;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getEmailContacto() {
		return emailContacto;
	}
	public void setEmailContacto(String emailContacto) {
		this.emailContacto = emailContacto;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public LocalDate getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	@Override
	public String toString() {
		return "Facultad [id=" + id + ", nombre=" + nombre + ", direccion=" + direccion + ", emailContacto="
				+ emailContacto + ", telefono=" + telefono + ", fechaAlta=" + fechaAlta + "]";
	}
	
	
}

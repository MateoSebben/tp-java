package entities;

import java.time.LocalDateTime;

public class SolicitudMateria {
	
	private int idSolicitud;
    private String nombreMateria;
    private String descripcion;
    private int idCarrera;
    private int idUsuarioSolicitante;
    private LocalDateTime fechaSolicitud;
    private String estado; // PENDIENTE, APROBADA, RECHAZADA
    private String motivoRechazo;
    private Integer idAdministrador;
    private LocalDateTime fechaResolucion;
    
    // Campos auxiliares para mostrar información relacionada
    private String nombreCarrera;
    private String nombreUsuarioSolicitante;
    private String emailUsuarioSolicitante;
    private String nombreAdministrador;
	
    public int getIdSolicitud() {
		return idSolicitud;
	}
	public void setIdSolicitud(int idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	public String getNombreMateria() {
		return nombreMateria;
	}
	public void setNombreMateria(String nombreMateria) {
		this.nombreMateria = nombreMateria;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getIdCarrera() {
		return idCarrera;
	}
	public void setIdCarrera(int idCarrera) {
		this.idCarrera = idCarrera;
	}
	public int getIdUsuarioSolicitante() {
		return idUsuarioSolicitante;
	}
	public void setIdUsuarioSolicitante(int idUsuarioSolicitante) {
		this.idUsuarioSolicitante = idUsuarioSolicitante;
	}
	public LocalDateTime getFechaSolicitud() {
		return fechaSolicitud;
	}
	public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getMotivoRechazo() {
		return motivoRechazo;
	}
	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}
	public Integer getIdAdministrador() {
		return idAdministrador;
	}
	public void setIdAdministrador(Integer idAdministrador) {
		this.idAdministrador = idAdministrador;
	}
	public LocalDateTime getFechaResolucion() {
		return fechaResolucion;
	}
	public void setFechaResolucion(LocalDateTime fechaResolucion) {
		this.fechaResolucion = fechaResolucion;
	}
	
	public String getNombreCarrera() {
		return nombreCarrera;
	}
	public void setNombreCarrera(String nombreCarrera) {
		this.nombreCarrera = nombreCarrera;
	}
	public String getNombreUsuarioSolicitante() {
		return nombreUsuarioSolicitante;
	}
	public void setNombreUsuarioSolicitante(String nombreUsuarioSolicitante) {
		this.nombreUsuarioSolicitante = nombreUsuarioSolicitante;
	}
	public String getEmailUsuarioSolicitante() {
		return emailUsuarioSolicitante;
	}
	public void setEmailUsuarioSolicitante(String emailUsuarioSolicitante) {
		this.emailUsuarioSolicitante = emailUsuarioSolicitante;
	}
	public String getNombreAdministrador() {
		return nombreAdministrador;
	}
	public void setNombreAdministrador(String nombreAdministrador) {
		this.nombreAdministrador = nombreAdministrador;
	}
	
	@Override
	public String toString() {
		return "SolicitudMateria [idSolicitud=" + idSolicitud + ", nombreMateria=" + nombreMateria + ", descripcion="
				+ descripcion + ", idCarrera=" + idCarrera + ", idUsuarioSolicitante=" + idUsuarioSolicitante
				+ ", fechaSolicitud=" + fechaSolicitud + ", estado=" + estado + ", motivoRechazo=" + motivoRechazo
				+ ", idAdministrador=" + idAdministrador + ", fechaResolucion=" + fechaResolucion + "]";
	}
	
	
    
    


}

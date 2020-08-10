package com.main.dto;

import java.util.Date;

public class NotaDto {
	
	private String idUsuario;
	
    private String titulo;
	
	private String contenido;
	
	private Date fecha = new Date();

	public NotaDto() {
	}
	
	public NotaDto(String nombreUsuario, String titulo, String contenido, Date fecha) {
		this.idUsuario = nombreUsuario;
		this.titulo = titulo;
		this.contenido = contenido;
		this.fecha = fecha;
	}

	public String getNombreUsuario() {
		return idUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.idUsuario = nombreUsuario;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
}

package com.main.documents;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection = "Nota")
public class Nota {

	@Id
	private String id = new ObjectId().toString();
	
	private String nombreUsuario;
	
    private String titulo;
	
	private String contenido;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private Date fecha = new Date();

	public Nota() {}
	
	public Nota(String nombreUsuario, String titulo, String contenido, Date fecha) {
		this.nombreUsuario = nombreUsuario; 
		this.titulo = titulo;
		this.contenido = contenido;
		this.fecha = fecha;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	
}

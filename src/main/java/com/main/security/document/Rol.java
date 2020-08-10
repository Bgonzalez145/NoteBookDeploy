package com.main.security.document;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.main.security.enums.RolNombre;

@Document(collection = "Rol")
public class Rol {
	
	@Id
	private String id = new ObjectId().toString();
	
	private RolNombre rolNombre;
	
	private String nombre;
	
	public Rol() {
	}

	public RolNombre getRolNombre() {
		return rolNombre;
	}

	public void setRolNombre(RolNombre rolNombre) {
		this.rolNombre = rolNombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return rolNombre.toString();
	}
	
	public Rol(RolNombre nombre) {
		this.rolNombre = nombre;
		this.nombre = nombre.toString();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public RolNombre getNombreRol() {
		return rolNombre;
	}

	public void setNombreRol(RolNombre nombreRol) {
		this.rolNombre = nombreRol;
	}
	
}


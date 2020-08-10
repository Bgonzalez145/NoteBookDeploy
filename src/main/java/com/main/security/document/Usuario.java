package com.main.security.document;

import java.util.HashSet;

import java.util.Set;

import javax.swing.ImageIcon;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "Usuario")
public class Usuario {

		@Id
		private String id = new ObjectId().toString();
		
		private String nombreUsuario;
		
		private String passwordUsuario;
		
		private String nombre;
		
		private String apellido;
		
		private String email;
		
		private String imagePath;
		
		private Set<Rol> roles = new HashSet<>();

		public Usuario() {
			// TODO Auto-generated constructor stub
		}

		public Usuario(String nombreUsuario, String passwordUsuario, String nombre, String apellido, String email) {
			this.nombreUsuario = nombreUsuario;
			this.passwordUsuario = passwordUsuario;
			this.nombre = nombre;
			this.apellido = apellido;
			this.email = email;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getNombreUsuario() {
			return nombreUsuario;
		}

		public void setNombreUsuario(String nombreUsuario) {
			this.nombreUsuario = nombreUsuario;
		}

		public String getPasswordUsuario() {
			return passwordUsuario;
		}

		public void setPasswordUsuario(String passwordUsuario) {
			this.passwordUsuario = passwordUsuario;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getApellido() {
			return apellido;
		}

		public void setApellido(String apellido) {
			this.apellido = apellido;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public Set<Rol> getRoles() {
			return roles;
		}

		public void setRoles(Set<Rol> roles) {
			this.roles = roles;
		}

		public String getImagePath() {
			return imagePath;
		}

		public void setImagePath(String imagePath) {
			this.imagePath = imagePath;
		}
		
}

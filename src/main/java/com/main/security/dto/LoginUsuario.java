package com.main.security.dto;

import javax.validation.constraints.NotBlank;

public class LoginUsuario {
	
	@NotBlank
	private String nombreUsuario;
	
	@NotBlank
	private String passwordUsuario;

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
	
}

package com.main.security.document;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UsuarioPrincipal implements UserDetails{

	private String nombreUsuario;
	
	private String passwordUsuario;
	
	private String nombre;
	
	private String apellido;
	
	private String email;

	private Collection<? extends GrantedAuthority> authorities;
	
	public UsuarioPrincipal() {
	}

	public UsuarioPrincipal(String nombreUsuario, String passwordUsuario, String nombre, String apellido, String email,
	Collection<? extends GrantedAuthority> authorities) {
		this.nombreUsuario = nombreUsuario;
		this.passwordUsuario = passwordUsuario;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.authorities = authorities;
	}

	public static UsuarioPrincipal build(Usuario usuario) {
		List<GrantedAuthority> authorities = usuario.getRoles().stream().map(rol -> new SimpleGrantedAuthority(rol.getNombreRol().name())).collect(Collectors.toList());
		return new UsuarioPrincipal(usuario.getNombreUsuario(), usuario.getPasswordUsuario(), usuario.getNombre(), usuario.getApellido(), usuario.getEmail(), authorities);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return passwordUsuario;
	}

	@Override
	public String getUsername() {
		return nombreUsuario;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
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
	
}

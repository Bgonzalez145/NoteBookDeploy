package com.main.security.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.main.security.document.Rol;
import com.main.security.document.Usuario;
import com.main.security.enums.RolNombre;
import com.main.security.service.ImageService;
import com.main.security.service.RolService;
import com.main.security.service.UsuarioService;

@Component
public class LoadDatabase {

	@Autowired
	RolService rolService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	ImageService imageService;
	
	public void inicio() throws Exception {
		/* Creacion de los roles */
		Rol rolAdmin = new Rol(RolNombre.ROL_ADMIN);
		Rol rolUser = new Rol(RolNombre.ROL_USER);
		if(!(rolService.existByRolNombre(rolAdmin.getNombreRol()))) {
			rolService.save(rolAdmin);
		}
		if(!(rolService.existByRolNombre(rolUser.getNombreRol()))) {
			rolService.save(rolUser);
		}
		/* Creacion de la imagen del usuario */
		
		
		/* Creacion del usuario principal */
		Usuario usuario = new Usuario("admin", passwordEncoder.encode("123"), "root", "root", "prueba.david145@gmail.com");
		Set<Rol> roles = new HashSet<>();
		roles.add(rolService.getByRolNombre(RolNombre.ROL_ADMIN).get());
		roles.add(rolService.getByRolNombre(RolNombre.ROL_USER).get());
		usuario.setRoles(roles);
		if(!(usuarioService.existByNombreUsuario(usuario.getNombreUsuario())||usuarioService.existByEmail(usuario.getEmail()))) {
			usuarioService.save(usuario);
			imageService.crearImagenAdmin();
		}
	}
	
	private BufferedImage leerImagen(String filenameOrigen) {
		try {
			// Crear un archivo de origen
			File file = new File(filenameOrigen);
			// Leer el archivo y ponerlo en el buffer
			BufferedImage buffer = ImageIO.read(file);
			return buffer;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private byte[] obtenerBytesDeBuffer(BufferedImage buffer) {
		try {
			// Conversion de BufferedImage a ByteArrayOutputStream
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(buffer, "jpg", baos);
			baos.flush();
			// Obtencion de byte[] a partir de ByteArrayOutputStream
			return baos.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}

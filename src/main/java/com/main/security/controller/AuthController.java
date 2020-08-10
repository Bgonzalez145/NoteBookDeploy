package com.main.security.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.main.security.document.ImageModel;
import com.main.security.document.Rol;
import com.main.security.document.Usuario;
import com.main.security.dto.JwtDto;
import com.main.security.dto.LoginUsuario;
import com.main.security.dto.Mensaje;
import com.main.security.dto.NuevoUsuario;
import com.main.security.enums.RolNombre;
import com.main.security.jwt.JwtProvider;
import com.main.security.service.ImageService;
import com.main.security.service.RolService;
import com.main.security.service.UsuarioService;

import javax.swing.JFrame;

@RestController
@RequestMapping("/api")
@CrossOrigin(exposedHeaders="Access-Control-Allow-Origin")
public class AuthController {
    
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	RolService rolService;
	
	@Autowired
	JwtProvider jwtProvider;
	
	@Autowired
	ImageService imageService;
	
	@PostMapping("/register")
	public ResponseEntity<Mensaje> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult) throws IOException{
		if(bindingResult.hasErrors()) {
			return new ResponseEntity("Campos mal puestos o email invalido", HttpStatus.BAD_REQUEST);
		}
		if(usuarioService.existByNombreUsuario(nuevoUsuario.getNombreUsuario())) {
			return new ResponseEntity("Ese nombre ya existe", HttpStatus.BAD_REQUEST);
		}
		if(usuarioService.existByEmail(nuevoUsuario.getEmail())) {
			return new ResponseEntity("Ese email ya existe", HttpStatus.BAD_REQUEST);
		}
		Usuario usuario = new Usuario(nuevoUsuario.getNombreUsuario(), passwordEncoder.encode(nuevoUsuario.getPasswordUsuario()), nuevoUsuario.getNombre(), nuevoUsuario.getApellido(), nuevoUsuario.getEmail());
		Set<Rol> roles = new HashSet<>();
		roles.add(rolService.getByRolNombre(RolNombre.ROL_USER).get());
		//roles.add(rolService.getByRolNombre(RolNombre.ROL_ADMIN).get());
		if(nuevoUsuario.getRoles().contains("admin")) {
			roles.add(rolService.getByRolNombre(RolNombre.ROL_ADMIN).get());
		}
		usuario.setRoles(roles);
		usuarioService.save(usuario);
		return new ResponseEntity<>(new Mensaje("Solicitud exitosa"), HttpStatus.OK); 
	}
	
	@PostMapping("/upload")
	public ResponseEntity<?> uploadImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
		if(imageService.existeImagen(file.getOriginalFilename())) {
			return new ResponseEntity("Ese nombre ya existe", HttpStatus.BAD_REQUEST);
		} else {
			imageService.crearImagen(file);
			return new ResponseEntity<>(" Solicitud exitosa ", HttpStatus.OK);
		}
	}
	
	@GetMapping("/get/{imageName}")
	public ResponseEntity<ImageModel> getImage(@PathVariable("imageName") String imageName) throws IOException {
		ImageModel retorno = imageService.obtenerImagen(imageName);
		retorno.setPicByte(imageService.decompressBytes(retorno.getPicByte()));
		return new ResponseEntity<>(retorno, HttpStatus.OK);
	}
	
	@GetMapping("/printUsers")
	public List<Usuario> imprimirUsuarios(){
		return usuarioService.findAllUsers();
	}
	
	@PostMapping("/login")
	public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult){
		if(bindingResult.hasErrors()) {
			return new ResponseEntity("Campos mal puestos", HttpStatus.BAD_REQUEST);
		}
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), loginUsuario.getPasswordUsuario()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtProvider.generateToken(authentication);
		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
		JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
		return new ResponseEntity<>(jwtDto, HttpStatus.OK);
	}
	
	@GetMapping("/roles")
	public List<Rol> findAllRoles(){
		return rolService.findAllRoles();
	}
	
}

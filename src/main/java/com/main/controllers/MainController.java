package com.main.controllers;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.documents.Nota;
import com.main.security.document.Usuario;
import com.main.security.dto.Mensaje;
import com.main.security.service.UsuarioService;
import com.main.services.NotaService;
import com.main.services.ReporteService;

import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/api")
@CrossOrigin(exposedHeaders="Access-Control-Allow-Origin")
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MainController {
	
	@Autowired
	private NotaService notaService;
	
	@Autowired
	private ReporteService reporteService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping("/createNote")
	public void crearNota(@RequestBody Nota nota) {
		notaService.crearNota(nota);
	}
	
	@PostMapping("/updateNote/{idNota}")
	public void actualizarNota(@PathVariable("idNota") String idNota, @RequestBody Nota nota) {
		notaService.actualizarNota(idNota, nota);
	}
	
	@GetMapping("/deleteNote/{idNota}")
	public void borrarNota(@PathVariable("idNota") String idNota) {
		notaService.borrarNota(idNota);
	}
	
	@GetMapping("/printNote/{idNota}")
	public Nota detalleNota(@PathVariable("idNota") String idNota) {
			return notaService.detalleNota(idNota);
	}
	
	@GetMapping("/printNotesUser/{nombreUsuario}")
	public List<Nota> imprimirNotasUsuario(@PathVariable("nombreUsuario") String nombreUsuario) {
		return notaService.imprimirNotasUsuario(nombreUsuario);
	}
	
	//@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/printNotes")
	public List<Nota> imprimirNotas() {
		return notaService.imprimirNotas();
	}
	
	@GetMapping("/printUser/{nombreUsuario}")
	public Optional<Usuario> imprimirUsuario(@PathVariable("nombreUsuario") String nombreUsuario){
		return usuarioService.findByNombreUsuario(nombreUsuario);
	}
	
	@GetMapping("/printReportNotes/{nombreUsuario}")
	public ResponseEntity<Mensaje> generarReporteNotas(@PathVariable("nombreUsuario") String nombreUsuario) throws FileNotFoundException, JRException {
		boolean validador = reporteService.exportReport("pdf", nombreUsuario);
		if(validador == true) {
			return new ResponseEntity<>(new Mensaje("Solicitud exitosa"), HttpStatus.OK); 
		}else {
			return new ResponseEntity<>(new Mensaje("Solicitud exitosa"), HttpStatus.BAD_REQUEST); 
		}
	}
	
}

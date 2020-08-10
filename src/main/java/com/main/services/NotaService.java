package com.main.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.documents.Nota;
import com.main.repositorys.NotaRepository;

@Service
@Transactional
public class NotaService {

	@Autowired
	NotaRepository noteRepository;
	
	public void crearNota(Nota nota) {
		noteRepository.crearNota(nota);
	}
	
	public void borrarNota(String idNota) {
		noteRepository.borrarNota(idNota);
	}
	
	public void actualizarNota(String idNota, Nota nota) {
		noteRepository.actualizarNota(idNota, nota);
	}
	
	public List<Nota> imprimirNotasUsuario(String nombreUsuario) {
		return noteRepository.imprimirNotasUsuario(nombreUsuario);
	}
	
	public List<Nota> imprimirNotas() {
       return noteRepository.imprimirNotas();
	}

	public Nota detalleNota(String idNota) {
		return noteRepository.detalleNota(idNota);
	}
	
}

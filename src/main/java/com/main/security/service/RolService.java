package com.main.security.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.security.document.Rol;
import com.main.security.enums.RolNombre;
import com.main.security.repository.RolRepository;

@Service
@Transactional
public class RolService {
   
	@Autowired
	RolRepository rolRepository;
	
	public Optional<Rol> getByRolNombre(RolNombre rolNombre){
		return rolRepository.findByRolNombre(rolNombre);
	}
	
	public void save(Rol rol) {
		rolRepository.save(rol);
	}
	
	public boolean existByRolNombre(RolNombre rolNombre) {
		return rolRepository.existsByRolNombre(rolNombre);
	}
	
	public List<Rol> findAllRoles() {
		return rolRepository.findAll();
	}
	
}

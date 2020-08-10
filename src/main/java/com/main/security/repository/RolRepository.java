package com.main.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.main.security.document.Rol;
import com.main.security.enums.RolNombre;

@Repository
public interface RolRepository extends MongoRepository<Rol, Integer>{
	Optional<Rol> findByRolNombre(RolNombre rolNombre);
	boolean existsByRolNombre(RolNombre rolNombre);
	List<Rol> findAll();
}

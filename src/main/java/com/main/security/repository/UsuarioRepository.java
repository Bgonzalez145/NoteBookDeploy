package com.main.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.main.security.document.Usuario;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, Integer>{
     Optional<Usuario> findByNombreUsuario(String nombreUsuario);
     boolean existsByNombreUsuario(String nombreUsuario);
     boolean existsByEmail(String email);
     List<Usuario> findAll();
}

package com.valerian.evaluacionplataformas.repository;

import com.valerian.evaluacionplataformas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsuario(String usuario);
}




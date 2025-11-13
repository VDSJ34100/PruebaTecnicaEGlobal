package com.valerian.evaluacionplataformas.service;

import com.valerian.evaluacionplataformas.model.Usuario;
import com.valerian.evaluacionplataformas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public boolean validarCredenciales(String usuario, String password) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsuario(usuario);

        if (usuarioOpt.isPresent()) {
            Usuario u = usuarioOpt.get();
            return encoder.matches(password, u.getPassword());
        }
        return false;
    }
}



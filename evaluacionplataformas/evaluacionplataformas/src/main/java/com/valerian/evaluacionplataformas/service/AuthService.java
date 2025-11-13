package com.valerian.evaluacionplataformas.service;

import com.valerian.evaluacionplataformas.dto.LoginRequest;
import com.valerian.evaluacionplataformas.dto.LoginResponse;
import com.valerian.evaluacionplataformas.model.Usuario;
import com.valerian.evaluacionplataformas.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class AuthService {

    private final UsuarioRepository repo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthService(UsuarioRepository repo) { this.repo = repo; }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest req) {
        Usuario u = repo.findByUsuario(req.getUsuario())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas"));

        if (!encoder.matches(req.getPassword(), u.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }

        String token = UUID.randomUUID().toString(); // token temporal
        return new LoginResponse("Login exitoso", token);
    }
}


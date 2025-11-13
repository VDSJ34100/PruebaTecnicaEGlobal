package com.valerian.evaluacionplataformas.controller;

import com.valerian.evaluacionplataformas.model.Usuario;
import com.valerian.evaluacionplataformas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:3000")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // ✅ Crear nuevo usuario
    @PostMapping("/crear")
    public Map<String, Object> crearUsuario(@RequestBody Usuario usuario) {
        Map<String, Object> respuesta = new HashMap<>();

        try {
            // Verificar si ya existe el usuario
            if (usuarioRepository.findByUsuario(usuario.getUsuario()) != null) {
                respuesta.put("error", "⚠️ El usuario ya existe");
                return respuesta;
            }

            // Encriptar contraseña
            String passwordEncriptada = encoder.encode(usuario.getPassword());
            usuario.setPassword(passwordEncriptada);

            // Guardar usuario
            usuarioRepository.save(usuario);

            respuesta.put("mensaje", "✅ Usuario creado correctamente");
            respuesta.put("usuario", usuario.getUsuario());
            return respuesta;

        } catch (Exception e) {
            respuesta.put("error", "❌ Error al crear usuario: " + e.getMessage());
            return respuesta;
        }
    }
}

package com.valerian.evaluacionplataformas.controller;

import com.valerian.evaluacionplataformas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public Map<String, Object> login(@RequestBody Map<String, String> datos) {
        String usuario = datos.get("usuario");
        String password = datos.get("password");

        Map<String, Object> resp = new HashMap<>();

        if (usuario == null || password == null) {
            resp.put("error", "Faltan datos");
            return resp;
        }

        boolean valido = usuarioService.validarCredenciales(usuario, password);

        if (valido) {
            resp.put("mensaje", "Login exitoso");
            resp.put("usuario", usuario);
        } else {
            resp.put("error", "Credenciales incorrectas");
        }

        return resp;
    }
}







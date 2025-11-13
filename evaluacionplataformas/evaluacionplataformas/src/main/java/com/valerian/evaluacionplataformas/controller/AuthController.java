package com.valerian.evaluacionplataformas.controller;

import com.valerian.evaluacionplataformas.dto.LoginRequest;
import com.valerian.evaluacionplataformas.dto.LoginResponse;
import com.valerian.evaluacionplataformas.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) { this.authService = authService; }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }
}

package com.valerian.evaluacionplataformas.controller;

import com.valerian.evaluacionplataformas.model.Transaccion;
import com.valerian.evaluacionplataformas.repository.TransaccionRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

@RestController
@RequestMapping("/api/operacion")
public class OperacionController {

    @Autowired
    private TransaccionRepository transaccionRepository;

    @PostMapping("/procesar")
    public ResponseEntity<?> procesar(@Valid @RequestBody OperacionRequest request) {
        try {
            String recalculada = generarSHA(request.getOperacion() + request.getImporte() + request.getCliente());
            if (!recalculada.equals(request.getFirma())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Firma inválida");
            }

            Transaccion t = new Transaccion();
            t.setOperacion(request.getOperacion());
            t.setImporte(Double.valueOf(request.getImporte()));
            t.setCliente(request.getCliente());
            t.setReferencia(String.valueOf(new Random().nextInt(900000) + 100000));
            t.setEstatus("Aprobada");
            t.setSecreto(UUID.randomUUID().toString());

            transaccionRepository.save(t);

            Map<String, Object> response = new HashMap<>();
            response.put("id", t.getId());
            response.put("estatus", t.getEstatus());
            response.put("referencia", t.getReferencia());
            response.put("operacion", t.getOperacion());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PatchMapping("/cancelar")
    public ResponseEntity<?> cancelar(@RequestParam Long id, @RequestParam String referencia) {
        transaccionRepository.actualizarEstatus(id, referencia, "Cancelada");
        return ResponseEntity.ok("Transacción cancelada");
    }

    @GetMapping("/consultar")
    public ResponseEntity<?> consultar() {
        return ResponseEntity.ok(transaccionRepository.findAll());
    }

    @GetMapping("/buscar/{ref}")
    public ResponseEntity<?> buscarPorReferencia(@PathVariable String ref) {
        return ResponseEntity.ok(transaccionRepository.buscarPorReferencia(ref));
    }

    private String generarSHA(String texto) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(texto.getBytes(StandardCharsets.UTF_8));
        StringBuilder hex = new StringBuilder();
        for (byte b : hash) {
            String h = Integer.toHexString(0xff & b);
            if (h.length() == 1) hex.append('0');
            hex.append(h);
        }
        return hex.toString();
    }

    @Data
    static class OperacionRequest {
        @Pattern(regexp = "^[a-zA-Z]+$", message = "Operación debe ser texto")
        private String operacion;

        @Pattern(regexp = "^[0-9]+(\\.[0-9]{1,2})?$", message = "Importe inválido")
        private String importe;

        @Pattern(regexp = "^[a-zA-Z]+$", message = "Cliente debe ser texto")
        private String cliente;

        private String firma;
    }
}








package com.valerian.evaluacionplataformas.controller;

import com.valerian.evaluacionplataformas.model.Transaccion;
import com.valerian.evaluacionplataformas.repository.TransaccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/transacciones")
@CrossOrigin(origins = "http://localhost:3000")
public class TransaccionController {

    @Autowired
    private TransaccionRepository transaccionRepository;

    // ✅ Crear una nueva transacción
    @PostMapping
    public ResponseEntity<?> guardarTransaccion(@RequestBody Map<String, Object> datos) {
        try {
            // Validar campos obligatorios
            if (datos.get("operacion") == null || datos.get("importe") == null || datos.get("cliente") == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Faltan datos obligatorios"));
            }

            Transaccion t = new Transaccion();
            t.setOperacion((String) datos.get("operacion"));
            t.setImporte(Double.parseDouble(datos.get("importe").toString()));
            t.setCliente((String) datos.get("cliente"));
            t.setEstatus("Aprobada");
            t.setReferencia(String.valueOf(new Random().nextInt(900000) + 100000)); // referencia aleatoria de 6 dígitos

            Transaccion guardada = transaccionRepository.save(t);

            return ResponseEntity.ok(Map.of(
                    "id", guardada.getId(),
                    "estatus", guardada.getEstatus(),
                    "referencia", guardada.getReferencia(),
                    "operacion", guardada.getOperacion()
            ));

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", "Error al guardar: " + e.getMessage()));
        }
    }

    // ✅ Listar todas las transacciones
    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            List<Transaccion> lista = transaccionRepository.findAll();
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    // ✅ Cancelar transacción por referencia (usa findByReferencia)
    @PatchMapping
    public ResponseEntity<?> cancelar(@RequestBody Map<String, Object> datos) {
        try {
            String ref = (String) datos.get("referencia");
            if (ref == null || ref.isBlank()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Referencia no proporcionada"));
            }

            Optional<Transaccion> t = transaccionRepository.findByReferencia(ref);

            if (t.isPresent()) {
                Transaccion trans = t.get();
                if ("Cancelada".equalsIgnoreCase(trans.getEstatus())) {
                    return ResponseEntity.badRequest().body(Map.of("error", "La transacción ya está cancelada"));
                }

                trans.setEstatus("Cancelada");
                transaccionRepository.save(trans);

                return ResponseEntity.ok(Map.of(
                        "mensaje", "Transacción cancelada correctamente",
                        "referencia", trans.getReferencia(),
                        "estatus", trans.getEstatus()
                ));
            } else {
                return ResponseEntity.badRequest().body(Map.of("error", "Referencia no encontrada"));
            }

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    // ✅ Cancelar transacción por ID (para botón del frontend)
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelarPorId(@PathVariable Long id) {
        try {
            Optional<Transaccion> optional = transaccionRepository.findById(id);
            if (optional.isPresent()) {
                Transaccion t = optional.get();

                if ("Cancelada".equalsIgnoreCase(t.getEstatus())) {
                    return ResponseEntity.badRequest().body(Map.of("error", "La transacción ya está cancelada"));
                }

                t.setEstatus("Cancelada");
                transaccionRepository.save(t);

                return ResponseEntity.ok(Map.of(
                        "mensaje", "Transacción cancelada correctamente",
                        "id", t.getId(),
                        "estatus", t.getEstatus()
                ));
            } else {
                return ResponseEntity.badRequest().body(Map.of("error", "Transacción no encontrada"));
            }

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    // ✅ Ejemplo alternativo: cancelar usando el método @Query del repo (si prefieres probar)
    @PutMapping("/cancelarQuery/{id}/{referencia}")
    public ResponseEntity<?> cancelarUsandoQuery(
            @PathVariable Long id,
            @PathVariable String referencia) {

        try {
            int filas = transaccionRepository.actualizarEstatus(id, referencia, "Cancelada");
            if (filas > 0) {
                return ResponseEntity.ok(Map.of(
                        "mensaje", "Transacción cancelada correctamente (por query)",
                        "id", id,
                        "referencia", referencia
                ));
            } else {
                return ResponseEntity.badRequest().body(Map.of("error", "No se encontró la transacción con esa referencia o ya estaba cancelada"));
            }

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }
}








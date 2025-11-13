package com.valerian.evaluacionplataformas.service;

import com.valerian.evaluacionplataformas.model.Operacion;
import com.valerian.evaluacionplataformas.repository.OperacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OperacionService {

    @Autowired
    private OperacionRepository operacionRepository;

    public Operacion guardarOperacion(Operacion op) {
        // Generar referencia aleatoria de 6 dígitos
        Random rnd = new Random();
        String referencia = String.format("%06d", rnd.nextInt(1000000));
        op.setReferencia(referencia);
        op.setEstatus("Aprobada");
        return operacionRepository.save(op);
    }

    public Operacion actualizarEstatus(Long id, String estatus) {
        Operacion op = operacionRepository.findById(id).orElse(null);
        if (op != null) {
            op.setEstatus(estatus);
            return operacionRepository.save(op);
        }
        return null;
    }

    public Operacion buscarPorReferencia(String referencia) {
        return operacionRepository.findByReferencia(referencia);
    }
}

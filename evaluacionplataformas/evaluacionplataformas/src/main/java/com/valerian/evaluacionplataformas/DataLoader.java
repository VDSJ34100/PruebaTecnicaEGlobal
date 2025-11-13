package com.valerian.evaluacionplataformas;

import com.valerian.evaluacionplataformas.model.Usuario;
import com.valerian.evaluacionplataformas.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner init(UsuarioRepository repo) {
        return args -> {
            if (repo.findByUsuario("admin").isEmpty()) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                Usuario u = new Usuario();
                u.setUsuario("admin");
                u.setPassword(encoder.encode("1234"));
                repo.save(u);
                System.out.println("Usuario de prueba creado: admin / 1234");
            }
        };
    }
}



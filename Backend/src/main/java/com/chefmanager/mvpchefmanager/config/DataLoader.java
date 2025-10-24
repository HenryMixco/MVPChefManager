package com.chefmanager.mvpchefmanager.config;

import com.chefmanager.mvpchefmanager.model.User;
import com.chefmanager.mvpchefmanager.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Verificar si el usuario admin ya existe
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole("ADMIN");
                userRepository.save(admin);
                System.out.println("Usuario administrador creado: admin/admin123");
            } else {
                System.out.println("Usuario administrador ya existe");
            }
        };
    }
}
package com.example.ecommerce_usuarios.init;

import com.example.ecommerce_usuarios.config.DataSeedingProperties;
import com.example.ecommerce_usuarios.model.rolEntity;
import com.example.ecommerce_usuarios.model.userEntity;
import com.example.ecommerce_usuarios.repository.RolRepository;
import com.example.ecommerce_usuarios.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import com.example.ecommerce_usuarios.util.SimplePasswordEncoder;

@Configuration
public class DataInitializer {
    
    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    
    @Bean
    @Order(1)
    public CommandLineRunner initRolesAndVendedor(
            RolRepository rolRepository,
            UserRepository userRepository,
            DataSeedingProperties dataSeedingProperties,
            SimplePasswordEncoder passwordEncoder) {
        
        return args -> {
            if (!dataSeedingProperties.isEnabled()) {
                log.info("Data seeding está deshabilitado");
                return;
            }
            
            log.info("Iniciando carga de datos iniciales...");
            
            // 1. Crear roles VENDEDOR y CLIENTE
            rolEntity vendedorRole = rolRepository.findByName("VENDEDOR")
                .orElseGet(() -> {
                    log.info("Creando rol: VENDEDOR");
                    return rolRepository.save(new rolEntity("VENDEDOR"));
                });
            
            rolEntity clienteRole = rolRepository.findByName("CLIENTE")
                .orElseGet(() -> {
                    log.info("Creando rol: CLIENTE");
                    return rolRepository.save(new rolEntity("CLIENTE"));
                });
            
            // 2. Verificar si existen usuarios
            long userCount = userRepository.count();
            log.info("Total de usuarios en la base de datos: {}", userCount);
            
            // 3. Crear usuario vendedor si no existen usuarios
            if (userCount == 0) {
                if (!userRepository.existsByEmail(dataSeedingProperties.getVendedorEmail())) {
                    log.info("Creando usuario vendedor inicial");
                    
                    String encryptedPassword = passwordEncoder.encode(dataSeedingProperties.getVendedorPassword());
                    
                    userEntity vendedorUser = new userEntity(
                        "Vendedor Inicial",
                        dataSeedingProperties.getVendedorEmail(),
                        encryptedPassword,
                        vendedorRole
                    );
                    vendedorUser.setStatus(true);
                    
                    userRepository.save(vendedorUser);
                    log.info("Usuario vendedor creado exitosamente");
                }
            } else {
                log.info("La base de datos ya contiene usuarios. No se crearán datos de prueba.");
            }
            
            log.info("Carga de datos iniciales completada");
        };
    }
}

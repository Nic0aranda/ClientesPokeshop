package com.example.ecommerce_usuarios.repository;

import com.example.ecommerce_usuarios.model.rolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RolRepository extends JpaRepository<rolEntity, Long> {
    Optional<rolEntity> findByName(String name);
}

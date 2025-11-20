package com.example.ecommerce_usuarios.service;

import com.example.ecommerce_usuarios.model.rolEntity;
import com.example.ecommerce_usuarios.repository.RolRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class RolService {

    private final RolRepository rolRepository;

    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    public List<rolEntity> findAll() {
        return rolRepository.findAll();
    }

    public Optional<rolEntity> findById(Long id) {
        return rolRepository.findById(id);
    }

    public Optional<rolEntity> findByName(String name) {
        return rolRepository.findByName(name);
    }

    @Transactional
    public rolEntity save(rolEntity rol) {
        if (rol.getName() == null || rol.getName().isBlank()) {
            throw new RuntimeException("Nombre de rol obligatorio");
        }
        if (rolRepository.findByName(rol.getName()).isPresent()) {
            throw new RuntimeException("El rol ya existe");
        }
        return rolRepository.save(rol);
    }

    @Transactional
    public rolEntity update(Long id, rolEntity rolDetails) {
        rolEntity rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + id));
        if (rolDetails.getName() != null && !rolDetails.getName().equals(rol.getName())) {
            if (rolRepository.findByName(rolDetails.getName()).isPresent()) {
                throw new RuntimeException("El nombre de rol ya está en uso");
            }
            rol.setName(rolDetails.getName());
        }
        return rolRepository.save(rol);
    }

    @Transactional
    public void delete(Long id) {
        if (!rolRepository.existsById(id)) {
            throw new RuntimeException("Rol no encontrado con ID: " + id);
        }
        rolRepository.deleteById(id);
    }
}

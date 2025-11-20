package com.example.ecommerce_usuarios.service;

import com.example.ecommerce_usuarios.model.userEntity;
import com.example.ecommerce_usuarios.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UserRepository usuarioRepository;

    public UsuarioService(UserRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<userEntity> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<userEntity> findById(Long userId) {
        return usuarioRepository.findById(userId);
    }

    public Optional<userEntity> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Transactional
    public userEntity save(userEntity usuario) {
        // validar email único
        if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
            throw new RuntimeException("Email obligatorio");
        }
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Email ya registrado");
        }
        // Opcional: cifrar contraseña aquí (inyectar encoder)
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public userEntity update(Long id, userEntity usuarioDetails) {
        userEntity usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        // Nombre editable
        if (usuarioDetails.getNames() != null) {
            usuario.setNames(usuarioDetails.getNames());
        }

        // Email editable: validar único si cambia
        if (usuarioDetails.getEmail() != null && !usuarioDetails.getEmail().equals(usuario.getEmail())) {
            if (usuarioRepository.existsByEmail(usuarioDetails.getEmail())) {
                throw new RuntimeException("El email ya está en uso por otro usuario");
            }
            usuario.setEmail(usuarioDetails.getEmail());
        }

        // Contraseña: actualizar solo si se provee (y aplicar cifrado si corresponde)
        if (usuarioDetails.getPassword() != null && !usuarioDetails.getPassword().isBlank()) {
            usuario.setPassword(usuarioDetails.getPassword());
        }

        // Estado y rol (si vienen)
        if (usuarioDetails.getStatus() != null) {
            usuario.setStatus(usuarioDetails.getStatus());
        }
        if (usuarioDetails.getRol() != null) {
            usuario.setRol(usuarioDetails.getRol());
        }

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void updateStatus(Long userId, Boolean status) {
        if (!usuarioRepository.existsById(userId)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + userId);
        }
        usuarioRepository.updateUserStatus(userId, status);
    }

    @Transactional
    public void delete(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}
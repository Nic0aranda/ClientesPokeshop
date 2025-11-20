package com.example.ecommerce_usuarios.repository;

import com.example.ecommerce_usuarios.model.userEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<userEntity, Long> {

    Optional<userEntity> findByEmail(String email);

    // utilidad para comprobar email único rápidamente
    boolean existsByEmail(String email);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE userEntity u SET u.status = :status WHERE u.id = :userId")
    void updateUserStatus(@Param("userId") Long userId, @Param("status") Boolean status);

    List<userEntity> findByStatus(Boolean status);
}
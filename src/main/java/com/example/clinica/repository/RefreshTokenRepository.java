package com.example.clinica.repository;

import com.example.clinica.model.RefreshToken;
import com.example.clinica.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteAllByUsuario(Usuario usuario);
}

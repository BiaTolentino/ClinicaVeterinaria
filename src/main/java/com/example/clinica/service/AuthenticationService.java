package com.example.clinica.service;

import com.example.clinica.model.RefreshToken;
import com.example.clinica.model.Usuario;
import com.example.clinica.repository.RefreshTokenRepository;
import com.example.clinica.repository.UsuarioRepository;
import com.example.clinica.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    private final long refreshDurationMillis = 1000L * 60 * 60 * 24 * 7; // 7 dias

    public LoginResponse login(String username, String senha) {

        Usuario user = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(senha, user.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }

        // PADRONIZAR role
        String rawRole = user.getPapel() != null ? user.getPapel() : "USER";
        String role = "ROLE_" + rawRole;

        String accessToken = jwtUtil.generateToken(user.getUsername(), role);

        // refresh rotacionável
        String refreshTokenStr = UUID.randomUUID().toString() + "-" + UUID.randomUUID();
        RefreshToken refreshToken = RefreshToken.builder()
                .token(refreshTokenStr)
                .usuario(user)
                .expiryDate(Instant.now().plusMillis(refreshDurationMillis))
                .revoked(false)
                .build();

        refreshTokenRepository.save(refreshToken);

        return new LoginResponse(accessToken, refreshTokenStr);
    }


    public LoginResponse refresh(String refreshTokenStr) {

        RefreshToken r = refreshTokenRepository.findByToken(refreshTokenStr)
                .orElseThrow(() -> new RuntimeException("Refresh token inválido"));

        if (r.isRevoked() || r.getExpiryDate().isBefore(Instant.now())) {
            throw new RuntimeException("Refresh token inválido ou expirado");
        }

        Usuario user = r.getUsuario();

        // revogar o token antigo
        r.setRevoked(true);
        refreshTokenRepository.save(r);

        // gerar novo refresh
        String newRefresh = UUID.randomUUID().toString() + "-" + UUID.randomUUID();
        RefreshToken nr = RefreshToken.builder()
                .token(newRefresh)
                .usuario(user)
                .expiryDate(Instant.now().plusMillis(refreshDurationMillis))
                .revoked(false)
                .build();
        refreshTokenRepository.save(nr);

        // ✔ ROLE CONSISTENTE (igual no login)
        String rawRole = user.getPapel() != null ? user.getPapel() : "USER";
        String role = "ROLE_" + rawRole;

        String accessToken = jwtUtil.generateToken(user.getUsername(), role);

        return new LoginResponse(accessToken, newRefresh);
    }



    public void logout(String refreshTokenStr) {
        refreshTokenRepository.findByToken(refreshTokenStr).ifPresent(t -> {
            t.setRevoked(true);
            refreshTokenRepository.save(t);
        });
    }

    // DTO
    @lombok.Data
    @lombok.AllArgsConstructor
    public static class LoginResponse {
        private String token;
        private String refreshToken;
    }
}

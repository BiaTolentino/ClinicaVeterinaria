package com.example.clinica.controller;

import com.example.clinica.Dto.LoginRequest;
import com.example.clinica.service.AuthenticationService;
import com.example.clinica.service.AuthenticationService.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        LoginResponse resp = authService.login(request.getUsername(), request.getSenha());
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshRequest r) {
        LoginResponse resp = authService.refresh(r.getRefreshToken());
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody RefreshRequest r) {
        authService.logout(r.getRefreshToken());
        return ResponseEntity.noContent().build();
    }

    public static class RefreshRequest {
        private String refreshToken;
        public String getRefreshToken(){return refreshToken;}
        public void setRefreshToken(String rt){this.refreshToken = rt;}
    }
}

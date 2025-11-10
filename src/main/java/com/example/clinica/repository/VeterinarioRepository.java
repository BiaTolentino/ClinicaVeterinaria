package com.example.clinica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.clinica.model.Veterinario;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {
    @Query("SELECT v.idVet, v.nome, COUNT(c) FROM Veterinario v LEFT JOIN Consulta c ON c.veterinario = v GROUP BY v.idVet, v.nome")
    List<Object[]> countConsultasByVet();
}

package com.example.clinica.repository;

import com.example.clinica.model.Consulta;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    @Override
    @EntityGraph(attributePaths = {"pet", "veterinario"})
    List<Consulta> findAll();

    @EntityGraph(attributePaths = {"pet", "veterinario"})
    List<Consulta> findByPetIdPet(Long petId);

    @EntityGraph(attributePaths = {"pet", "veterinario"})
    List<Consulta> findByVeterinarioIdVetAndDataHoraBetween(Long vetId, LocalDateTime start, LocalDateTime end);

    @Query("""
        SELECT c FROM Consulta c
        WHERE (:idVet IS NULL OR c.veterinario.idVet = :idVet)
          AND (:idPet IS NULL OR c.pet.idPet = :idPet)
          AND (:inicio IS NULL OR c.dataHora >= :inicio)
          AND (:fim IS NULL OR c.dataHora <= :fim)
        ORDER BY c.dataHora DESC
    """)
    List<Consulta> buscarComFiltros(
            @Param("idVet") Long idVet,
            @Param("idPet") Long idPet,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );

}

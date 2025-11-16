package com.example.clinica.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "consulta")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Schema(description = "Representa uma consulta veterinária realizada na clínica")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID da consulta", example = "1")
    private Long idConsulta;

    @Schema(description = "Data e hora da consulta", example = "2025-01-15T14:30:00")
    private LocalDateTime dataHora;

    @Column(columnDefinition = "TEXT")
    @Schema(description = "Motivo da consulta", example = "Cachorro vomitando e apático")
    private String motivo;

    @Column(columnDefinition = "TEXT")
    @Schema(description = "Diagnóstico fornecido pelo veterinário", example = "Gastroenterite leve")
    private String diagnostico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pet", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @Schema(description = "Pet atendido")
    private Pet pet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vet", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @Schema(description = "Veterinário responsável pela consulta")
    private Veterinario veterinario;
}

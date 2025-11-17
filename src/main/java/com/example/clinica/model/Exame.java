package com.example.clinica.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "exame")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Schema(description = "Entidade que representa um exame clínico realizado para um pet")
public class Exame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único do exame", example = "15")
    private Long idExame;

    @Schema(description = "Tipo do exame realizado", example = "Raio-X")
    private String tipo;

    @Column(columnDefinition = "TEXT")
    @Schema(description = "Resultado detalhado do exame", example = "Fratura na pata dianteira esquerda")
    private String resultado;

    @Schema(description = "Data em que o exame foi realizado", example = "2025-02-11")
    private LocalDate dataExame;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_consulta", nullable = false)
    @Schema(description = "Consulta associada ao exame")
    private Consulta consulta;
}

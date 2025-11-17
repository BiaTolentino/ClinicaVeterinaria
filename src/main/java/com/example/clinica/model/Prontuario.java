package com.example.clinica.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "prontuario")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Entidade que representa o prontuário de um pet, contendo observações e última atualização")
public class Prontuario {

    @Id
    @Column(name = "id_pet")
    @Schema(description = "Identificador do pet associado ao prontuário", example = "5")
    private Long idPet;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id_pet")
    @Schema(description = "Pet associado ao prontuário")
    private Pet pet;

    @Column(columnDefinition = "TEXT")
    @Schema(description = "Observações gerais sobre o pet", example = "Pet apresenta alergia alimentar, deve ser monitorado")
    private String observacoesGerais;

    @Schema(description = "Data e hora da última atualização do prontuário", example = "2025-11-24T15:30:00")
    private LocalDateTime ultimaAtualizacao;
}

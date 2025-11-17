package com.example.clinica.model;

import jakarta.persistence.Embeddable;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Schema(description = "ID composto do tratamento, contendo consulta e medicamento")
public class TratamentoId implements Serializable {

    @Schema(description = "ID da consulta associada ao tratamento", example = "10")
    private Long idConsulta;

    @Schema(description = "ID do medicamento utilizado no tratamento", example = "5")
    private Long idMedicamento;
}

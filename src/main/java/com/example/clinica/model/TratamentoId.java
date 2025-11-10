package com.example.clinica.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TratamentoId implements Serializable {
    private Long idConsulta;
    private Long idMedicamento;
}

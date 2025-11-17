package com.example.clinica.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "tratamento")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Entidade que representa o tratamento aplicado a um pet, com duração e observações")
public class Tratamento {

    @EmbeddedId
    @Schema(description = "ID composto do tratamento (consulta + medicamento)")
    private TratamentoId id;

    @Schema(description = "Duração do tratamento em dias", example = "7")
    private Integer duracaoDias;

    @Column(columnDefinition = "TEXT")
    @Schema(description = "Observações adicionais sobre o tratamento", example = "Aplicar medicamento após as refeições")
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idConsulta")
    @JoinColumn(name = "id_consulta")
    @Schema(description = "Consulta associada ao tratamento")
    private Consulta consulta;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idMedicamento")
    @JoinColumn(name = "id_medicamento")
    @Schema(description = "Medicamento utilizado no tratamento")
    private Medicamento medicamento;

    public TratamentoId getId() {
        return id;
    }

    public void setId(TratamentoId id) {
        this.id = id;
    }
}

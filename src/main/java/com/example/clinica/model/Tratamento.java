package com.example.clinica.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tratamento")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Tratamento {
    @EmbeddedId
    private TratamentoId id;

    private Integer duracaoDias;
    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idConsulta")
    @JoinColumn(name = "id_consulta")
    private Consulta consulta;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idMedicamento")
    @JoinColumn(name = "id_medicamento")
    private Medicamento medicamento;

    public TratamentoId getId() {
        return id;
    }

    public void setId(TratamentoId id) {
        this.id = id;
    }
}

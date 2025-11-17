package com.example.clinica.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medicamento")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Entidade que representa um medicamento utilizado em tratamentos veterinários")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único do medicamento", example = "10")
    private Long idMedicamento;

    @Schema(description = "Nome do medicamento", example = "Amoxicilina")
    private String nome;

    @Column(columnDefinition = "TEXT")
    @Schema(description = "Descrição detalhada do medicamento", example = "Antibiótico de amplo espectro utilizado no tratamento de infecções bacterianas.")
    private String descricao;

    @Schema(description = "Dosagem recomendada para administração", example = "250 mg a cada 12 horas")
    private String dosagem;

    public Long getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(Long idMedicamento) {
        this.idMedicamento = idMedicamento;
    }
}

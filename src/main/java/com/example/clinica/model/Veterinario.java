package com.example.clinica.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "veterinario")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Entidade que representa um veterinário da clínica")
public class Veterinario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único do veterinário", example = "1")
    private Long idVet;

    @Schema(description = "Nome completo do veterinário", example = "Dr. João Silva")
    private String nome;

    @Schema(description = "Número do registro no CRMV", example = "12345-SP")
    private String crmv;

    @Schema(description = "Especialidade do veterinário", example = "Clínica Geral")
    private String especialidade;

    @Schema(description = "Número de telefone do veterinário", example = "(11) 98765-4321")
    private String telefone;

    @Schema(description = "E-mail do veterinário", example = "joao.silva@clinica.com")
    private String email;
}

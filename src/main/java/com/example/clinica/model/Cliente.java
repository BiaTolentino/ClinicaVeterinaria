package com.example.clinica.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Schema(description = "Entidade que representa um cliente da clínica veterinária.")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único do cliente", example = "1")
    private Long idCliente;

    @Schema(description = "Nome completo do cliente", example = "João da Silva")
    private String nome;

    @Schema(description = "CPF do cliente", example = "123.456.789-00")
    private String cpf;

    @Schema(description = "Telefone de contato", example = "(11) 98765-4321")
    private String telefone;

    @Schema(description = "E-mail do cliente", example = "joao.silva@email.com")
    private String email;

    @Column(columnDefinition = "TEXT")
    @Schema(description = "Endereço completo do cliente",
            example = "Rua das Flores, 123 - Centro - São Paulo - SP")
    private String endereco;
}

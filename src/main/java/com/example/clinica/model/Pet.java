package com.example.clinica.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Entity
@Table(name = "pet")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Schema(description = "Entidade que representa um pet de um cliente na clínica")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único do pet", example = "12")
    private Long idPet;

    @Schema(description = "Nome do pet", example = "Rex")
    private String nome;

    @Schema(description = "Espécie do animal", example = "Cachorro")
    private String especie;

    @Schema(description = "Raça do animal", example = "Labrador")
    private String raca;

    @Schema(description = "Data de nascimento do pet", example = "2018-05-21")
    private LocalDate dataNascimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    @Schema(description = "Cliente dono do pet")
    private Cliente cliente;

    @Transient
    @Schema(description = "ID do cliente associado, usado apenas para requests", example = "5")
    private Long idCliente;

    public Long getIdPet() {
        return idPet;
    }

    public void setIdPet(Long idPet) {
        this.idPet = idPet;
    }
}

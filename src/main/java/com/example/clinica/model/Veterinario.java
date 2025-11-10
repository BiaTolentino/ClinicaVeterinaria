package com.example.clinica.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "veterinario")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Veterinario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVet;
    private String nome;
    private String crmv;
    private String especialidade;
    private String telefone;
    private String email;
}

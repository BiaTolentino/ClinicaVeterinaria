package com.example.clinica.controller;

import com.example.clinica.model.Exame;
import com.example.clinica.model.Consulta;
import com.example.clinica.repository.ExameRepository;
import com.example.clinica.repository.ConsultaRepository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/exames")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Exames", description = "Endpoints para gerenciamento de exames veterinários")
public class ExameController {

    private final ExameRepository repo;
    private final ConsultaRepository consultaRepo;


    @GetMapping
    @Operation(
            summary = "Listar todos os exames",
            description = "Retorna uma lista de exames com informações resumidas da consulta relacionada."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista retornada com sucesso",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Map.class)))
    )
    public List<Map<String, Object>> all() {
        return repo.findAllWithConsulta().stream().map(e -> Map.of(
                "idExame", e.getIdExame(),
                "tipo", e.getTipo(),
                "resultado", e.getResultado(),
                "dataExame", e.getDataExame(),
                "consulta", Map.of(
                        "idConsulta", e.getConsulta().getIdConsulta()
                )
        )).toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar exame por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Exame encontrado"),
            @ApiResponse(responseCode = "404", description = "Exame não encontrado")
    })
    public Optional<Exame> get(@PathVariable Long id) {
        return repo.findById(id);
    }

    @PostMapping
    @Operation(
            summary = "Criar um novo exame",
            description = "Cria um exame e associa a uma consulta já existente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Exame criado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Consulta não encontrada")
    })
    public Exame create(@RequestBody ExamePayload p) {
        Exame e = new Exame();
        e.setTipo(p.getTipo());
        e.setResultado(p.getResultado());
        e.setDataExame(p.getDataExame());

        Consulta c = consultaRepo.findById(p.getConsultaId())
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        e.setConsulta(c);
        e.setIdExame(null);
        return repo.save(e);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um exame existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Exame atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Exame ou consulta não encontrada")
    })
    public Exame update(@PathVariable Long id, @RequestBody ExamePayload p) {
        Exame e = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Exame não encontrado"));

        e.setTipo(p.getTipo());
        e.setResultado(p.getResultado());
        e.setDataExame(p.getDataExame());

        Consulta c = consultaRepo.findById(p.getConsultaId())
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        e.setConsulta(c);
        return repo.save(e);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir exame por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Exame excluído"),
            @ApiResponse(responseCode = "404", description = "Exame não encontrado")
    })
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }

    @Schema(description = "Dados enviados para criar ou atualizar um exame")
    public static class ExamePayload {

        @Schema(description = "Tipo de exame", example = "Raio-X")
        private String tipo;

        @Schema(description = "Resultado do exame", example = "Fratura na pata esquerda")
        private String resultado;

        @Schema(description = "Data em que o exame foi realizado", example = "2025-02-10")
        private java.time.LocalDate dataExame;

        @Schema(description = "ID da consulta relacionada", example = "12")
        private Long consultaId;

        public String getTipo(){return tipo;} public void setTipo(String t){this.tipo=t;}
        public String getResultado(){return resultado;} public void setResultado(String r){this.resultado=r;}
        public java.time.LocalDate getDataExame(){return dataExame;} public void setDataExame(java.time.LocalDate d){this.dataExame=d;}
        public Long getConsultaId(){return consultaId;} public void setConsultaId(Long id){this.consultaId=id;}
    }
}

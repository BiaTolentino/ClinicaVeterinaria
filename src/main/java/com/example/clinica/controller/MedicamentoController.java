package com.example.clinica.controller;

import com.example.clinica.model.Medicamento;
import com.example.clinica.repository.MedicamentoRepository;

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
import java.util.Optional;

@RestController
@RequestMapping("/api/medicamentos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Medicamentos", description = "Gerenciamento de medicamentos veterinários")
public class MedicamentoController {

    private final MedicamentoRepository repo;

    @GetMapping
    @Operation(summary = "Listar todos os medicamentos",
            description = "Retorna uma lista completa de medicamentos cadastrados.")
    @ApiResponse(
            responseCode = "200",
            description = "Lista retornada com sucesso",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Medicamento.class)))
    )
    public List<Medicamento> all() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar medicamento por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Medicamento encontrado"),
            @ApiResponse(responseCode = "404", description = "Medicamento não encontrado")
    })
    public Optional<Medicamento> get(@PathVariable Long id) {
        return repo.findById(id);
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo medicamento")
    @ApiResponse(responseCode = "200", description = "Medicamento criado com sucesso")
    public Medicamento create(@RequestBody Medicamento m) {
        m.setIdMedicamento(null);
        return repo.save(m);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados de um medicamento existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Medicamento atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Medicamento não encontrado")
    })
    public Medicamento update(@PathVariable Long id, @RequestBody Medicamento m) {
        m.setIdMedicamento(id);
        return repo.save(m);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir medicamento por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Medicamento excluído"),
            @ApiResponse(responseCode = "404", description = "Medicamento não encontrado")
    })
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}

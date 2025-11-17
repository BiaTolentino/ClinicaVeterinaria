package com.example.clinica.controller;

import com.example.clinica.model.Veterinario;
import com.example.clinica.repository.VeterinarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/veterinarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Veterinários", description = "Gerenciamento de veterinários da clínica")
public class VeterinarioController {
    private final VeterinarioRepository repo;

    @GetMapping
    @Operation(summary = "Listar todos os veterinários", description = "Retorna todos os veterinários cadastrados")
    public List<Veterinario> all() { return repo.findAll(); }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar veterinário por ID", description = "Retorna um veterinário específico pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Veterinário encontrado"),
            @ApiResponse(responseCode = "404", description = "Veterinário não encontrado")
    })
    public Optional<Veterinario> get(@PathVariable Long id) { return repo.findById(id); }

    @PostMapping
    @Operation(summary = "Criar novo veterinário", description = "Adiciona um novo veterinário ao sistema")
    public Veterinario create(@RequestBody Veterinario v) {
        v.setIdVet(null);
        return repo.save(v);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar veterinário", description = "Atualiza os dados de um veterinário existente")
    public Veterinario update(@PathVariable Long id, @RequestBody Veterinario v) {
        v.setIdVet(id);
        return repo.save(v);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir veterinário", description = "Remove um veterinário do sistema pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Veterinário excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Veterinário não encontrado")
    })
    public void delete(@PathVariable Long id) { repo.deleteById(id); }
}

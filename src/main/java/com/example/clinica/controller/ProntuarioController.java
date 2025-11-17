package com.example.clinica.controller;

import com.example.clinica.model.Prontuario;
import com.example.clinica.model.Pet;
import com.example.clinica.repository.ProntuarioRepository;
import com.example.clinica.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/prontuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Prontuários", description = "Gerenciamento de prontuários dos pets")
public class ProntuarioController {
    private final ProntuarioRepository repo;
    private final PetRepository petRepo;

    @GetMapping
    @Operation(summary = "Listar todos os prontuários", description = "Retorna todos os prontuários cadastrados, incluindo informações do pet")
    public List<Prontuario> all() {
        return repo.findAllWithPet();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar prontuário por ID", description = "Retorna um prontuário específico pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prontuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Prontuário não encontrado")
    })
    public Optional<Prontuario> get(@PathVariable Long id) {
        return repo.findById(id);
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Criar novo prontuário", description = "Cria um novo prontuário para um pet específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prontuário criado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pet não encontrado")
    })
    public Prontuario create(@RequestBody ProntuarioPayload payload) {
        Prontuario p = new Prontuario();
        Pet pet = petRepo.findById(payload.getPetId()).orElseThrow(() -> new RuntimeException("Pet não encontrado"));
        p.setPet(pet);
        p.setObservacoesGerais(payload.getObservacoesGerais());
        p.setUltimaAtualizacao(java.time.LocalDateTime.now());
        return repo.save(p);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar prontuário", description = "Atualiza um prontuário existente")
    public Prontuario update(@PathVariable Long id, @RequestBody Prontuario p) {
        p.setIdPet(id);
        p.setUltimaAtualizacao(java.time.LocalDateTime.now());
        return repo.save(p);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir prontuário por ID", description = "Remove um prontuário do sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Prontuário excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Prontuário não encontrado")
    })
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }

    public static class ProntuarioPayload {
        private Long petId;
        private String observacoesGerais;

        public Long getPetId(){return petId;}
        public void setPetId(Long id){this.petId=id;}
        public String getObservacoesGerais(){return observacoesGerais;}
        public void setObservacoesGerais(String s){this.observacoesGerais=s;}
    }
}

package com.example.clinica.controller;

import com.example.clinica.model.Consulta;
import com.example.clinica.model.Pet;
import com.example.clinica.model.Veterinario;
import com.example.clinica.repository.ConsultaRepository;
import com.example.clinica.repository.PetRepository;
import com.example.clinica.repository.VeterinarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/consultas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Consultas", description = "Endpoints de gerenciamento de consultas veterinárias")
public class ConsultaController {

    private final ConsultaRepository consultaRepository;
    private final PetRepository petRepository;
    private final VeterinarioRepository veterinarioRepository;

    @Operation(summary = "Listar todas as consultas",
            description = "Retorna todas as consultas cadastradas no sistema, incluindo dados do pet e veterinário.")
    @GetMapping
    public List<Map<String, Object>> listAll() {
        List<Consulta> consultas = consultaRepository.findAll();

        return consultas.stream().map(c -> Map.of(
                "idConsulta", c.getIdConsulta(),
                "dataHora", c.getDataHora() != null ? c.getDataHora().toString() : null,
                "motivo", c.getMotivo(),
                "diagnostico", c.getDiagnostico(),
                "pet", c.getPet() != null ? Map.of(
                        "idPet", c.getPet().getIdPet(),
                        "nome", c.getPet().getNome(),
                        "especie", c.getPet().getEspecie()
                ) : null,
                "veterinario", c.getVeterinario() != null ? Map.of(
                        "idVet", c.getVeterinario().getIdVet(),
                        "nome", c.getVeterinario().getNome(),
                        "especialidade", c.getVeterinario().getEspecialidade()
                ) : null
        )).toList();
    }

    @Operation(summary = "Buscar consulta por ID",
            description = "Retorna uma consulta específica a partir do seu ID.")
    @GetMapping("/{id}")
    public Consulta getById(
            @Parameter(description = "ID da consulta")
            @PathVariable Long id) {
        return consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));
    }

    @Operation(summary = "Cadastrar nova consulta",
            description = "Cria uma nova consulta associando um pet e um veterinário.")
    @PostMapping
    public Consulta create(
            @RequestBody Map<String, Object> payload) {

        Long petId = Long.valueOf(payload.get("petId").toString());
        Long veterinarioId = Long.valueOf(payload.get("veterinarioId").toString());

        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado: " + petId));
        Veterinario vet = veterinarioRepository.findById(veterinarioId)
                .orElseThrow(() -> new RuntimeException("Veterinário não encontrado: " + veterinarioId));

        Consulta c = new Consulta();
        c.setDataHora(payload.get("dataHora") != null
                ? LocalDateTime.parse(payload.get("dataHora").toString())
                : null);
        c.setMotivo((String) payload.get("motivo"));
        c.setDiagnostico((String) payload.get("diagnostico"));
        c.setPet(pet);
        c.setVeterinario(vet);

        return consultaRepository.save(c);
    }

    @Operation(summary = "Atualizar consulta existente",
            description = "Atualiza informações da consulta, como horário, motivo, diagnóstico, pet ou veterinário.")
    @PutMapping("/{id}")
    public Consulta update(
            @Parameter(description = "ID da consulta")
            @PathVariable Long id,
            @RequestBody Map<String, Object> payload) {

        Consulta c = consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        if (payload.get("dataHora") != null)
            c.setDataHora(LocalDateTime.parse(payload.get("dataHora").toString()));
        if (payload.get("motivo") != null)
            c.setMotivo((String) payload.get("motivo"));
        if (payload.get("diagnostico") != null)
            c.setDiagnostico((String) payload.get("diagnostico"));

        if (payload.get("petId") != null) {
            Pet pet = petRepository.findById(Long.valueOf(payload.get("petId").toString()))
                    .orElseThrow(() -> new RuntimeException("Pet não encontrado"));
            c.setPet(pet);
        }
        if (payload.get("veterinarioId") != null) {
            Veterinario vet = veterinarioRepository.findById(Long.valueOf(payload.get("veterinarioId").toString()))
                    .orElseThrow(() -> new RuntimeException("Veterinário não encontrado"));
            c.setVeterinario(vet);
        }

        return consultaRepository.save(c);
    }

    @Operation(summary = "Excluir consulta",
            description = "Remove uma consulta do sistema.")
    @DeleteMapping("/{id}")
    public void delete(
            @Parameter(description = "ID da consulta a ser removida")
            @PathVariable Long id) {
        consultaRepository.deleteById(id);
    }

    @Operation(summary = "Listar consultas por Pet",
            description = "Retorna todas as consultas pertencentes a um pet específico.")
    @GetMapping("/pet/{petId}")
    public List<Consulta> getByPet(
            @Parameter(description = "ID do Pet")
            @PathVariable Long petId) {
        return consultaRepository.findByPetIdPet(petId);
    }

    @Operation(summary = "Listar consultas por Veterinário e período",
            description = "Retorna as consultas de um veterinário dentro de um intervalo de datas.")
    @GetMapping("/veterinario/{vetId}")
    public List<Consulta> getByVetAndPeriod(
            @Parameter(description = "ID do veterinário")
            @PathVariable Long vetId,
            @Parameter(description = "Data inicial (formato ISO: 2024-05-20T10:00)")
            @RequestParam LocalDateTime inicio,
            @Parameter(description = "Data final (formato ISO: 2024-05-21T10:00)")
            @RequestParam LocalDateTime fim) {
        return consultaRepository.findByVeterinarioIdVetAndDataHoraBetween(vetId, inicio, fim);
    }

    @Operation(summary = "Busca avançada de consultas",
            description = "Filtro geral por veterinário, pet e data inicial/final.")
    @GetMapping("/filtro")
    public List<Consulta> buscarComFiltros(
            @Parameter(description = "ID do veterinário")
            @RequestParam(required = false) Long idVet,
            @Parameter(description = "ID do pet")
            @RequestParam(required = false) Long idPet,
            @Parameter(description = "Data inicial (formato ISO)")
            @RequestParam(required = false) String inicio,
            @Parameter(description = "Data final (formato ISO)")
            @RequestParam(required = false) String fim
    ) {
        LocalDateTime dataInicio = (inicio != null) ? LocalDateTime.parse(inicio) : null;
        LocalDateTime dataFim = (fim != null) ? LocalDateTime.parse(fim) : null;
        return consultaRepository.buscarComFiltros(idVet, idPet, dataInicio, dataFim);
    }
}

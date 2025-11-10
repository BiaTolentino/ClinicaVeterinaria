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

@RestController
@RequestMapping("/api/consultas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ConsultaController {

    private final ConsultaRepository consultaRepository;
    private final PetRepository petRepository;
    private final VeterinarioRepository veterinarioRepository;

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

    @GetMapping("/{id}")
    public Consulta getById(@PathVariable Long id) {
        return consultaRepository.findById(id).orElseThrow(() -> new RuntimeException("Consulta não encontrada"));
    }

    @PostMapping
    public Consulta create(@RequestBody Map<String, Object> payload) {
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

    @PutMapping("/{id}")
    public Consulta update(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
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

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        consultaRepository.deleteById(id);
    }

    @GetMapping("/pet/{petId}")
    public List<Consulta> getByPet(@PathVariable Long petId) {
        return consultaRepository.findByPetIdPet(petId);
    }

    @GetMapping("/veterinario/{vetId}")
    public List<Consulta> getByVetAndPeriod(
            @PathVariable Long vetId,
            @RequestParam LocalDateTime inicio,
            @RequestParam LocalDateTime fim) {
        return consultaRepository.findByVeterinarioIdVetAndDataHoraBetween(vetId, inicio, fim);
    }

    @GetMapping("/filtro")
    public List<Consulta> buscarComFiltros(
            @RequestParam(required = false) Long idVet,
            @RequestParam(required = false) Long idPet,
            @RequestParam(required = false) String inicio,
            @RequestParam(required = false) String fim
    ) {
        LocalDateTime dataInicio = (inicio != null) ? LocalDateTime.parse(inicio) : null;
        LocalDateTime dataFim = (fim != null) ? LocalDateTime.parse(fim) : null;
        return consultaRepository.buscarComFiltros(idVet, idPet, dataInicio, dataFim);
    }
}

package com.example.clinica.controller;

import com.example.clinica.model.Prontuario;
import com.example.clinica.model.Pet;
import com.example.clinica.repository.ProntuarioRepository;
import com.example.clinica.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/prontuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProntuarioController {
    private final ProntuarioRepository repo;
    private final PetRepository petRepo;

    @GetMapping
    public List<Prontuario> all() {
        return repo.findAllWithPet();
    }


    @GetMapping("/{id}")
    public Optional<Prontuario> get(@PathVariable Long id) { return repo.findById(id); }

    @PostMapping
    @Transactional
    public Prontuario create(@RequestBody ProntuarioPayload payload) {
        Prontuario p = new Prontuario();
        Pet pet = petRepo.findById(payload.getPetId()).orElseThrow(() -> new RuntimeException("Pet não encontrado"));
        p.setPet(pet);
        p.setObservacoesGerais(payload.getObservacoesGerais());
        p.setUltimaAtualizacao(java.time.LocalDateTime.now());
        return repo.save(p);
    }

    @PutMapping("/{id}")
    public Prontuario update(@PathVariable Long id, @RequestBody Prontuario p) {
        p.setIdPet(id);
        p.setUltimaAtualizacao(java.time.LocalDateTime.now());
        return repo.save(p);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { repo.deleteById(id); }

    public static class ProntuarioPayload {
        private Long petId;
        private String observacoesGerais;
        public Long getPetId(){return petId;} public void setPetId(Long id){this.petId=id;}
        public String getObservacoesGerais(){return observacoesGerais;} public void setObservacoesGerais(String s){this.observacoesGerais=s;}
    }
}

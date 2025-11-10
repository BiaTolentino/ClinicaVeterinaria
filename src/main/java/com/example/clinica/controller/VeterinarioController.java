package com.example.clinica.controller;

import com.example.clinica.model.Veterinario;
import com.example.clinica.repository.VeterinarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/veterinarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VeterinarioController {
    private final VeterinarioRepository repo;

    @GetMapping
    public List<Veterinario> all() { return repo.findAll(); }

    @GetMapping("/{id}")
    public Optional<Veterinario> get(@PathVariable Long id) { return repo.findById(id); }

    @PostMapping
    public Veterinario create(@RequestBody Veterinario v) {
        v.setIdVet(null);
        return repo.save(v);
    }

    @PutMapping("/{id}")
    public Veterinario update(@PathVariable Long id, @RequestBody Veterinario v) {
        v.setIdVet(id);
        return repo.save(v);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { repo.deleteById(id); }
}

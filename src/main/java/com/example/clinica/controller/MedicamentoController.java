package com.example.clinica.controller;

import com.example.clinica.model.Medicamento;
import com.example.clinica.repository.MedicamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/medicamentos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MedicamentoController {
    private final MedicamentoRepository repo;

    @GetMapping
    public List<Medicamento> all() { return repo.findAll(); }

    @GetMapping("/{id}")
    public Optional<Medicamento> get(@PathVariable Long id) { return repo.findById(id); }

    @PostMapping
    public Medicamento create(@RequestBody Medicamento m) {
        m.setIdMedicamento(null);
        return repo.save(m);
    }

    @PutMapping("/{id}")
    public Medicamento update(@PathVariable Long id, @RequestBody Medicamento m) {
        m.setIdMedicamento(id);
        return repo.save(m);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { repo.deleteById(id); }
}

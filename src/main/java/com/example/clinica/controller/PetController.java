package com.example.clinica.controller;

import com.example.clinica.model.Cliente;
import com.example.clinica.model.Pet;
import com.example.clinica.repository.ClienteRepository;
import com.example.clinica.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // permite requisições do front local
public class PetController {

    private final PetRepository petRepo;
    private final ClienteRepository clienteRepo;

    @GetMapping
    public List<Pet> getAll() {
        return petRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> getById(@PathVariable Long id) {
        return petRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Pet> create(@RequestBody Pet pet) {
        if (pet.getCliente() == null && pet.getIdCliente() != null) {
            clienteRepo.findById(pet.getIdCliente()).ifPresent(pet::setCliente);
        }
        Pet saved = petRepo.save(pet);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pet> update(@PathVariable Long id, @RequestBody Pet pet) {
        return petRepo.findById(id).map(existing -> {
            existing.setNome(pet.getNome());
            existing.setEspecie(pet.getEspecie());
            existing.setRaca(pet.getRaca());
            existing.setDataNascimento(pet.getDataNascimento());
            if (pet.getCliente() == null && pet.getIdCliente() != null) {
                clienteRepo.findById(pet.getIdCliente()).ifPresent(existing::setCliente);
            }
            return ResponseEntity.ok(petRepo.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!petRepo.existsById(id)) return ResponseEntity.notFound().build();
        petRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

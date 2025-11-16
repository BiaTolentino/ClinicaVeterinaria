package com.example.clinica.controller;

import com.example.clinica.model.Cliente;
import com.example.clinica.repository.ClienteRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Clientes", description = "Operações relacionadas ao cadastro de clientes")
public class ClienteController {

    private final ClienteRepository clienteRepo;

    @GetMapping
    public List<Cliente> getAll() {
        return clienteRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getById(@PathVariable Long id) {
        return clienteRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cliente> create(@RequestBody Cliente cliente) {
        cliente.setIdCliente(null);
        Cliente saved = clienteRepo.save(cliente);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable Long id, @RequestBody Cliente cliente) {
        return clienteRepo.findById(id).map(existing -> {
            existing.setNome(cliente.getNome());
            existing.setCpf(cliente.getCpf());
            existing.setTelefone(cliente.getTelefone());
            existing.setEmail(cliente.getEmail());
            existing.setEndereco(cliente.getEndereco());
            return ResponseEntity.ok(clienteRepo.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!clienteRepo.existsById(id)) return ResponseEntity.notFound().build();
        clienteRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

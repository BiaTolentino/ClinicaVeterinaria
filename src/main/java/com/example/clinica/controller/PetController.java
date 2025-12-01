package com.example.clinica.controller;

import com.example.clinica.messaging.MensagemProducer;
import com.example.clinica.model.Pet;
import com.example.clinica.repository.PetRepository;
import com.example.clinica.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // permite requisições do front local
@Tag(name = "Pets", description = "Gerenciamento de pets e suas informações")
public class PetController {

    private final PetRepository petRepo;
    private final ClienteRepository clienteRepo;
    private final MensagemProducer mensagemProducer;

    // -----------------------------------------
    // GET /api/pets → lista todos os pets
    // -----------------------------------------
    @GetMapping
    @Operation(summary = "Listar todos os pets", description = "Retorna a lista completa de pets cadastrados na clínica")
    public List<Pet> getAll() {
        return petRepo.findAll();
    }

    // -----------------------------------------
    // GET /api/pets/{id} → busca pet por ID
    // -----------------------------------------
    @GetMapping("/{id}")
    @Operation(summary = "Buscar pet por ID", description = "Retorna os dados de um pet específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pet encontrado"),
            @ApiResponse(responseCode = "404", description = "Pet não encontrado")
    })
    public ResponseEntity<Pet> getById(@PathVariable Long id) {
        return petRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // -----------------------------------------
    // POST /api/pets → cadastra novo pet + envia mensagens
    // -----------------------------------------
    @PostMapping
    @Operation(summary = "Cadastrar novo pet", description = "Adiciona um novo pet ao sistema e envia mensagens de notificação")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pet cadastrado com sucesso")
    })
    public ResponseEntity<Pet> create(@RequestBody Pet pet) {
        // vincula cliente se necessário
        if (pet.getCliente() == null && pet.getIdCliente() != null) {
            clienteRepo.findById(pet.getIdCliente()).ifPresent(pet::setCliente);
        }

        Pet saved = petRepo.save(pet);

        // 🔹 Mensageria
        String msg = "Novo pet cadastrado: " + saved.getNome() + " (ID: " + saved.getIdPet() + ")";
        mensagemProducer.enviarFilaNovoPaciente(msg);          // envia para a fila
        mensagemProducer.enviarTopicoEventoPaciente(msg);     // envia para o tópico

        return ResponseEntity.ok(saved);
    }

    // -----------------------------------------
    // PUT /api/pets/{id} → atualiza pet
    // -----------------------------------------
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados de um pet", description = "Atualiza informações do pet existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pet atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pet não encontrado")
    })
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

    // -----------------------------------------
    // DELETE /api/pets/{id} → exclui pet
    // -----------------------------------------
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir pet por ID", description = "Remove um pet do sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pet excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pet não encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!petRepo.existsById(id)) return ResponseEntity.notFound().build();
        petRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // -----------------------------------------
    // ENDPOINTS DE TESTE DE MENSAGERIA
    // -----------------------------------------
    @GetMapping("/mensagem-fila")
    public String testeFila(@RequestParam String msg) {
        mensagemProducer.enviarFilaNovoPaciente(msg);
        return "Mensagem enviada para fila: " + msg;
    }

    @GetMapping("/mensagem-topico")
    public String testeTopico(@RequestParam String msg) {
        mensagemProducer.enviarTopicoEventoPaciente(msg);
        return "Mensagem enviada para tópico: " + msg;
    }
}

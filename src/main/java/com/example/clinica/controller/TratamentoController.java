package com.example.clinica.controller;

import com.example.clinica.model.Tratamento;
import com.example.clinica.model.TratamentoId;
import com.example.clinica.repository.TratamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/tratamentos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TratamentoController {

    private final TratamentoRepository tratamentoRepo;

    @GetMapping
    public List<Map<String, Object>> listarTodos() {
        List<Tratamento> lista = tratamentoRepo.findAllWithDetails();
        return mapear(lista);
    }

    @GetMapping("/consulta/{idConsulta}")
    public List<Map<String, Object>> listarPorConsulta(@PathVariable Long idConsulta) {
        List<Tratamento> lista = tratamentoRepo.findByConsultaWithDetails(idConsulta);
        return mapear(lista);
    }

    private List<Map<String, Object>> mapear(List<Tratamento> lista) {
        List<Map<String, Object>> resultado = new ArrayList<>();

        for (Tratamento t : lista) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("idConsulta", t.getConsulta() != null ? t.getConsulta().getIdConsulta() : null);
            map.put("medicamentoId", t.getMedicamento() != null ? t.getMedicamento().getIdMedicamento() : null);
            map.put("pet", t.getConsulta() != null && t.getConsulta().getPet() != null
                    ? t.getConsulta().getPet().getNome() : "—");
            map.put("medicamento", t.getMedicamento() != null
                    ? t.getMedicamento().getNome() : "—");
            map.put("veterinario", t.getConsulta() != null && t.getConsulta().getVeterinario() != null
                    ? t.getConsulta().getVeterinario().getNome() : "—");
            map.put("duracaoDias", t.getDuracaoDias());
            map.put("observacoes", t.getObservacoes());
            map.put("motivo", t.getConsulta() != null ? t.getConsulta().getMotivo() : "—");
            resultado.add(map);
        }

        return resultado;
    }

    @PutMapping("/{consultaId}/{medicamentoId}")
    public ResponseEntity<?> atualizar(
            @PathVariable Long consultaId,
            @PathVariable Long medicamentoId,
            @RequestBody Map<String, Object> body) {

        TratamentoId id = new TratamentoId(consultaId, medicamentoId);
        Optional<Tratamento> opt = tratamentoRepo.findById(id);
        if (!opt.isPresent()) return ResponseEntity.notFound().build();

        Tratamento t = opt.get();
        if (body.containsKey("duracaoDias")) {
            Object d = body.get("duracaoDias");
            t.setDuracaoDias(d == null ? null : ((Number) d).intValue());
        }
        if (body.containsKey("observacoes")) {
            t.setObservacoes((String) body.get("observacoes"));
        }

        tratamentoRepo.save(t);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{consultaId}/{medicamentoId}")
    public ResponseEntity<?> excluir(@PathVariable Long consultaId, @PathVariable Long medicamentoId) {
        TratamentoId id = new TratamentoId(consultaId, medicamentoId);
        if (!tratamentoRepo.existsById(id)) return ResponseEntity.notFound().build();
        tratamentoRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

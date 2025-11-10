package com.example.clinica.controller;

import com.example.clinica.model.Exame;
import com.example.clinica.model.Consulta;
import com.example.clinica.repository.ExameRepository;
import com.example.clinica.repository.ConsultaRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/exames")
@RequiredArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@CrossOrigin(origins = "*")
public class ExameController {
    private final ExameRepository repo;
    private final ConsultaRepository consultaRepo;

    @GetMapping
    public List<Map<String, Object>> all() {
        return repo.findAllWithConsulta().stream().map(e -> Map.of(
                "idExame", e.getIdExame(),
                "tipo", e.getTipo(),
                "resultado", e.getResultado(),
                "dataExame", e.getDataExame(),
                "consulta", Map.of(
                        "idConsulta", e.getConsulta().getIdConsulta()
                )
        )).toList();
    }

    @GetMapping("/{id}")
    public Optional<Exame> get(@PathVariable Long id) { return repo.findById(id); }

    @PostMapping
    public Exame create(@RequestBody ExamePayload p) {
        Exame e = new Exame();
        e.setTipo(p.getTipo());
        e.setResultado(p.getResultado());
        e.setDataExame(p.getDataExame());
        Consulta c = consultaRepo.findById(p.getConsultaId()).orElseThrow(() -> new RuntimeException("Consulta não encontrada"));
        e.setConsulta(c);
        e.setIdExame(null);
        return repo.save(e);
    }

    @PutMapping("/{id}")
    public Exame update(@PathVariable Long id, @RequestBody ExamePayload p) {
        Exame e = repo.findById(id).orElseThrow(() -> new RuntimeException("Exame não encontrado"));
        e.setTipo(p.getTipo());
        e.setResultado(p.getResultado());
        e.setDataExame(p.getDataExame());
        Consulta c = consultaRepo.findById(p.getConsultaId()).orElseThrow(() -> new RuntimeException("Consulta não encontrada"));
        e.setConsulta(c);
        return repo.save(e);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { repo.deleteById(id); }

    public static class ExamePayload {
        private String tipo;
        private String resultado;
        private java.time.LocalDate dataExame;
        private Long consultaId;
        public String getTipo(){return tipo;} public void setTipo(String t){this.tipo=t;}
        public String getResultado(){return resultado;} public void setResultado(String r){this.resultado=r;}
        public java.time.LocalDate getDataExame(){return dataExame;} public void setDataExame(java.time.LocalDate d){this.dataExame=d;}
        public Long getConsultaId(){return consultaId;} public void setConsultaId(Long id){this.consultaId=id;}
    }
}

package com.example.clinica.controller;

import com.example.clinica.Dto.TratamentoDto;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import com.example.clinica.service.TratamentoService;
import com.example.clinica.service.ReportService;
import com.example.clinica.model.Tratamento;

import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/operations")
@RequiredArgsConstructor
public class OperacoesController {
    private final TratamentoService tratamentoService;
    private final ReportService reportService;

    @PostMapping("/tratamento")
    public Map<String, Object> adicionarTratamento(@RequestBody Map<String, Object> payload) {
        Long consultaId = Long.valueOf(payload.get("consultaId").toString());
        Long medicamentoId = Long.valueOf(payload.get("medicamentoId").toString());
        Integer duracao = payload.get("duracaoDias") == null ? null : Integer.valueOf(payload.get("duracaoDias").toString());
        String observacoes = payload.get("observacoes") == null ? "" : payload.get("observacoes").toString();

        Tratamento t = tratamentoService.adicionarTratamentoEAtualizarProntuario(consultaId, medicamentoId, duracao, observacoes);

        // Retorna apenas o DTO, sem proxies do Hibernate
        return Map.of("status", "ok", "tratamento", new TratamentoDto(t));
    }

    @GetMapping("/reports/consultas-por-veterinario")
    public List<Object[]> consultasPorVeterinario() {
        return reportService.consultasPorVeterinario();
    }

    @GetMapping("/reports/medicamentos-mais-usados")
    public List<Object[]> medicamentosMaisUsados() {
        return reportService.medicamentosMaisUsados();
    }

    @GetMapping("/reports/consultas-por-especie")
    public List<Object[]> consultasPorEspecie() {
        return reportService.consultasPorEspecie();
    }
}

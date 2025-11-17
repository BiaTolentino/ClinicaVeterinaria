package com.example.clinica.controller;

import com.example.clinica.Dto.TratamentoDto;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import com.example.clinica.service.TratamentoService;
import com.example.clinica.service.ReportService;
import com.example.clinica.model.Tratamento;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/operations")
@RequiredArgsConstructor
@Tag(name = "Operações", description = "Operações especiais, tratamentos e relatórios")
public class OperacoesController {
    private final TratamentoService tratamentoService;
    private final ReportService reportService;

    @PostMapping("/tratamento")
    @Operation(summary = "Adicionar tratamento", description = "Adiciona um tratamento a uma consulta e atualiza o prontuário.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tratamento adicionado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Consulta ou medicamento não encontrado")
    })
    public Map<String, Object> adicionarTratamento(@RequestBody Map<String, Object> payload) {
        Long consultaId = Long.valueOf(payload.get("consultaId").toString());
        Long medicamentoId = Long.valueOf(payload.get("medicamentoId").toString());
        Integer duracao = payload.get("duracaoDias") == null ? null : Integer.valueOf(payload.get("duracaoDias").toString());
        String observacoes = payload.get("observacoes") == null ? "" : payload.get("observacoes").toString();

        Tratamento t = tratamentoService.adicionarTratamentoEAtualizarProntuario(consultaId, medicamentoId, duracao, observacoes);

        return Map.of("status", "ok", "tratamento", new TratamentoDto(t));
    }

    @GetMapping("/reports/consultas-por-veterinario")
    @Operation(summary = "Relatório: Consultas por veterinário", description = "Retorna a quantidade de consultas realizadas por cada veterinário.")
    public List<Object[]> consultasPorVeterinario() {
        return reportService.consultasPorVeterinario();
    }

    @GetMapping("/reports/medicamentos-mais-usados")
    @Operation(summary = "Relatório: Medicamentos mais usados", description = "Retorna os medicamentos mais utilizados em tratamentos.")
    public List<Object[]> medicamentosMaisUsados() {
        return reportService.medicamentosMaisUsados();
    }

    @GetMapping("/reports/consultas-por-especie")
    @Operation(summary = "Relatório: Consultas por espécie", description = "Retorna a quantidade de consultas por espécie de animal.")
    public List<Object[]> consultasPorEspecie() {
        return reportService.consultasPorEspecie();
    }
}

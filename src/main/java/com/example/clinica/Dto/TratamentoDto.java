package com.example.clinica.Dto;

import com.example.clinica.model.Tratamento;

public class TratamentoDto {
    private Long consultaId;
    private Long medicamentoId;
    private Integer duracaoDias;
    private String observacoes;

    public TratamentoDto() {}

    public TratamentoDto(Tratamento t) {
        this.consultaId = t.getConsulta().getIdConsulta();
        this.medicamentoId = t.getMedicamento().getIdMedicamento();
        this.duracaoDias = t.getDuracaoDias();
        this.observacoes = t.getObservacoes();
    }

    // Retorna o ID da consulta
    public Long getConsultaId() { return consultaId; }

    // Define o ID da consulta
    public void setConsultaId(Long consultaId) { this.consultaId = consultaId; }

    // Retorna o ID do medicamento
    public Long getMedicamentoId() { return medicamentoId; }

    // Define o ID do medicamento
    public void setMedicamentoId(Long medicamentoId) { this.medicamentoId = medicamentoId; }

    // Retorna a duração do tratamento em dias
    public Integer getDuracaoDias() { return duracaoDias; }

    // Define a duração do tratamento em dias
    public void setDuracaoDias(Integer duracaoDias) { this.duracaoDias = duracaoDias; }

    // Retorna as observações do tratamento
    public String getObservacoes() { return observacoes; }

    // Define observações adicionais para o tratamento
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}

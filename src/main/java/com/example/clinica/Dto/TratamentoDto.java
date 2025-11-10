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

    public Long getConsultaId() { return consultaId; }
    public void setConsultaId(Long consultaId) { this.consultaId = consultaId; }

    public Long getMedicamentoId() { return medicamentoId; }
    public void setMedicamentoId(Long medicamentoId) { this.medicamentoId = medicamentoId; }

    public Integer getDuracaoDias() { return duracaoDias; }
    public void setDuracaoDias(Integer duracaoDias) { this.duracaoDias = duracaoDias; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}

package com.example.clinica.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.example.clinica.repository.*;
import com.example.clinica.model.*;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TratamentoService {
    private final TratamentoRepository tratamentoRepository;
    private final ConsultaRepository consultaRepository;
    private final MedicamentoRepository medicamentoRepository;
    private final ProntuarioRepository prontuarioRepository;

    @Transactional
    public Tratamento adicionarTratamentoEAtualizarProntuario(Long consultaId, Long medicamentoId, Integer duracaoDias, String observacoes) {
        Consulta consulta = consultaRepository.findById(consultaId)
                .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada: " + consultaId));
        Medicamento medicamento = medicamentoRepository.findById(medicamentoId)
                .orElseThrow(() -> new IllegalArgumentException("Medicamento não encontrado: " + medicamentoId));

        TratamentoId id = new TratamentoId(consultaId, medicamentoId);
        Tratamento t = Tratamento.builder()
                .id(id)
                .consulta(consulta)
                .medicamento(medicamento)
                .duracaoDias(duracaoDias)
                .observacoes(observacoes)
                .build();
        tratamentoRepository.save(t);

        Long idPet = consulta.getPet().getIdPet();
        Prontuario p = prontuarioRepository.findById(idPet).orElseGet(() -> {
            Prontuario novo = Prontuario.builder().idPet(idPet).observacoesGerais("").ultimaAtualizacao(LocalDateTime.now()).build();
            novo.setPet(consulta.getPet());
            return novo;
        });
        String novoTexto = (p.getObservacoesGerais() == null ? "" : p.getObservacoesGerais())
                + "\n[" + LocalDateTime.now() + "] Tratamento: " + observacoes;
        p.setObservacoesGerais(novoTexto);
        p.setUltimaAtualizacao(LocalDateTime.now());
        prontuarioRepository.save(p);

        return t;
    }
}

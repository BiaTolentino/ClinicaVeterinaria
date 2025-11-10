package com.example.clinica.repository;

import com.example.clinica.model.Tratamento;
import com.example.clinica.model.TratamentoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TratamentoRepository extends JpaRepository<Tratamento, TratamentoId> {

    @Query("""
    SELECT t FROM Tratamento t
    JOIN FETCH t.consulta c
    JOIN FETCH c.pet p
    JOIN FETCH c.veterinario v
    JOIN FETCH t.medicamento m
""")
    List<Tratamento> findAllWithDetails();


    @Query("SELECT t FROM Tratamento t " +
            "JOIN FETCH t.consulta c " +
            "JOIN FETCH c.pet p " +
            "JOIN FETCH c.veterinario v " +
            "JOIN FETCH t.medicamento m " +
            "WHERE c.idConsulta = :consultaId")
    List<Tratamento> findByConsultaWithDetails(@Param("consultaId") Long consultaId);


}

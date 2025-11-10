package com.example.clinica.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReportService {

    @PersistenceContext
    private EntityManager em;

    public List<Object[]> consultasPorVeterinario() {
        return em.createQuery("""
            SELECT v.nome, COUNT(c)
            FROM Consulta c
            JOIN c.veterinario v
            GROUP BY v.nome
            ORDER BY COUNT(c) DESC
        """, Object[].class).getResultList();
    }

    public List<Object[]> medicamentosMaisUsados() {
        return em.createQuery("""
            SELECT m.nome, COUNT(t)
            FROM Tratamento t
            JOIN t.medicamento m
            GROUP BY m.nome
            ORDER BY COUNT(t) DESC
        """, Object[].class).getResultList();
    }

    public List<Object[]> consultasPorEspecie() {
        return em.createQuery("""
            SELECT p.especie, COUNT(c)
            FROM Consulta c
            JOIN c.pet p
            GROUP BY p.especie
            ORDER BY COUNT(c) DESC
        """, Object[].class).getResultList();
    }
}

package com.example.clinica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.clinica.model.Medicamento;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {
    @Query("SELECT m.idMedicamento, m.nome, COUNT(t) FROM Medicamento m LEFT JOIN Tratamento t ON t.medicamento = m GROUP BY m.idMedicamento, m.nome")
    List<Object[]> countUses();
}

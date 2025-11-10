package com.example.clinica.repository;

import com.example.clinica.model.Exame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExameRepository extends JpaRepository<Exame, Long> {

    @Query("SELECT e FROM Exame e JOIN FETCH e.consulta")
    List<Exame> findAllWithConsulta();

}

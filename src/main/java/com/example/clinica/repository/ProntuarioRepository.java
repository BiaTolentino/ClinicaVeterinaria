package com.example.clinica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.clinica.model.Prontuario;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProntuarioRepository extends JpaRepository<Prontuario, Long> {

    @Query("SELECT p FROM Prontuario p JOIN FETCH p.pet")
    List<Prontuario> findAllWithPet();

}

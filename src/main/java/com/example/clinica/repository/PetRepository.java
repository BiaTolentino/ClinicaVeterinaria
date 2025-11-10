package com.example.clinica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.clinica.model.Pet;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    @Query("SELECT p.especie, COUNT(c) FROM Pet p JOIN Consulta c ON c.pet = p GROUP BY p.especie")
    List<Object[]> countConsultasByEspecie();
}

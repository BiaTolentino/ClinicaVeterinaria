package com.example.clinica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.clinica.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}

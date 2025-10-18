package com.avicia.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avicia.api.model.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {

    // Buscar paciênte pelo CPF do usuário
    Optional<Paciente> findByUsuario_Cpf(String cpf);

}

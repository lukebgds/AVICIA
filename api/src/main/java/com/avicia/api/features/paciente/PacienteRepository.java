package com.avicia.api.features.paciente;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {

    // Buscar paciênte pelo CPF do usuário
    Optional<Paciente> findByUsuario_Cpf(String cpf);

    // Buscar Paciente de saúde pelo ID do usuário
    Optional<Paciente> findByUsuario_IdUsuario(Integer idUsuario);

}

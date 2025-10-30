package com.avicia.api.features.associacao.paciente.funcionario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avicia.api.data.serializer.PacienteFuncionarioId;

@Repository
public interface PacienteFuncionarioRepository extends JpaRepository<PacienteFuncionario, PacienteFuncionarioId> {
    boolean existsByFuncionario_IdFuncionarioAndPaciente_IdPaciente(Integer idFuncionario, Integer idPaciente);
}

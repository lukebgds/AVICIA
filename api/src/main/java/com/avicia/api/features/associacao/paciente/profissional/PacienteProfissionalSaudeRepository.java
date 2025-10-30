package com.avicia.api.features.associacao.paciente.profissional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avicia.api.data.serializer.PacienteProfissionalSaudeId;

@Repository
public interface PacienteProfissionalSaudeRepository extends JpaRepository<PacienteProfissionalSaude, PacienteProfissionalSaudeId>{

    boolean existsById_IdProfissionalAndId_IdPaciente(Integer idProfissional, Integer idPaciente);
}

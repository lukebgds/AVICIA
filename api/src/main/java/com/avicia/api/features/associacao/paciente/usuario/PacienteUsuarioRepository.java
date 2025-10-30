package com.avicia.api.features.associacao.paciente.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avicia.api.data.serializer.PacienteUsuarioId;

@Repository
public interface PacienteUsuarioRepository extends JpaRepository<PacienteUsuario, PacienteUsuarioId> {  
    boolean existsByUsuario_IdUsuarioAndPaciente_IdPaciente(Integer idUsuario, Integer idPaciente);
}

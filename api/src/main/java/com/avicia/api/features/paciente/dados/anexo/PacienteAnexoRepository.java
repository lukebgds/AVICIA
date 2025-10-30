package com.avicia.api.features.paciente.dados.anexo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteAnexoRepository extends JpaRepository<PacienteAnexo, Integer> {

    // Buscar todos os anexos de um paciente pelo ID do paciente
    List<PacienteAnexo> findByPaciente_IdPaciente(Integer idPaciente);

    // Buscar todos os anexos de um paciente pelo CPF do usuário
    List<PacienteAnexo> findByPaciente_Usuario_Cpf(String cpf);

    // Buscar anexos por tipo
    List<PacienteAnexo> findByPaciente_IdPacienteAndTipo(Integer idPaciente, String tipo);

}

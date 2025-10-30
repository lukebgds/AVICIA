package com.avicia.api.features.paciente.dados.antecedente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteAntecedenteRepository extends JpaRepository<PacienteAntecedente, Integer>{
    
    // Buscar todos os antecedentes de um paciente pelo ID do paciente
    List<PacienteAntecedente> findByPaciente_IdPaciente(Integer idPaciente);

    // Buscar todos os antecedentes de um paciente pelo CPF do usuário
    List<PacienteAntecedente> findByPaciente_Usuario_Cpf(String cpf);

    // Buscar antecedentes por tipo de doença
    List<PacienteAntecedente> findByPaciente_IdPacienteAndTipoDoenca(Integer idPaciente, String tipoDoenca);

    // Buscar antecedentes por parentesco
    List<PacienteAntecedente> findByPaciente_IdPacienteAndParentesco(Integer idPaciente, String parentesco);

}

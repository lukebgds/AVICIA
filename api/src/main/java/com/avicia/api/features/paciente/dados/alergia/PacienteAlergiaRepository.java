package com.avicia.api.features.paciente.dados.alergia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteAlergiaRepository extends JpaRepository<PacienteAlergia, Integer> {

    // Buscar todas as alergias de um paciente pelo ID do paciente
    List<PacienteAlergia> findByPaciente_IdPaciente(Integer idPaciente);

    // Buscar todas as alergias de um paciente pelo CPF do usuário
    List<PacienteAlergia> findByPaciente_Usuario_Cpf(String cpf);

}

package com.avicia.api.features.paciente.dados.vacina;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteVacinaRepository extends JpaRepository<PacienteVacina, Integer> {

    // Buscar todas as vacinas de um paciente pelo ID do paciente
    List<PacienteVacina> findByPaciente_IdPaciente(Integer idPaciente);

    // Buscar todas as vacinas de um paciente pelo CPF do usuário
    List<PacienteVacina> findByPaciente_Usuario_Cpf(String cpf);

    // Buscar vacinas por nome (contém)
    List<PacienteVacina> findByPaciente_IdPacienteAndVacinaContainingIgnoreCase(
        Integer idPaciente, 
        String vacina
    );

    // Buscar vacinas por período de aplicação
    List<PacienteVacina> findByPaciente_IdPacienteAndDataAplicacaoBetween(
        Integer idPaciente, 
        LocalDateTime dataInicio, 
        LocalDateTime dataFim
    );

    // Buscar vacinas aplicadas após uma data específica
    List<PacienteVacina> findByPaciente_IdPacienteAndDataAplicacaoAfter(
        Integer idPaciente, 
        LocalDateTime data
    );

    // Buscar vacinas ordenadas por data de aplicação (mais recentes primeiro)
    List<PacienteVacina> findByPaciente_IdPacienteOrderByDataAplicacaoDesc(Integer idPaciente);

}

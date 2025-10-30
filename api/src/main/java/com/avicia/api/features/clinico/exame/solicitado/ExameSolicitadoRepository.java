package com.avicia.api.features.clinico.exame.solicitado;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExameSolicitadoRepository extends JpaRepository<ExameSolicitado, Integer> {

    // Buscar exames solicitados por consulta
    List<ExameSolicitado> findByConsulta_IdConsulta(Integer idConsulta);

    // Buscar exames solicitados por paciente
    List<ExameSolicitado> findByPaciente_IdPaciente(Integer idPaciente);

    // Buscar exames solicitados por profissional
    List<ExameSolicitado> findByProfissionalSaude_IdProfissional(Integer idProfissional);

    // Buscar exames solicitados por tipo de exame
    List<ExameSolicitado> findByExame_IdExame(Integer idExame);

    // Buscar por status
    List<ExameSolicitado> findByStatus(String status);

    // Buscar por paciente e status
    List<ExameSolicitado> findByPaciente_IdPacienteAndStatus(Integer idPaciente, String status);

    // Buscar por período de solicitação
    List<ExameSolicitado> findByDataSolicitacaoBetween(LocalDate dataInicio, LocalDate dataFim);

    // Buscar por paciente e período
    List<ExameSolicitado> findByPaciente_IdPacienteAndDataSolicitacaoBetween(
        Integer idPaciente, 
        LocalDate dataInicio, 
        LocalDate dataFim
    );

    // Buscar por paciente ordenado por data
    List<ExameSolicitado> findByPaciente_IdPacienteOrderByDataSolicitacaoDesc(Integer idPaciente);

}

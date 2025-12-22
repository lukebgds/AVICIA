package com.avicia.api.features.clinico.exame.resultado;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExameResultadoRepository extends JpaRepository<ExameResultado, Integer> {

    // Buscar resultado por exame solicitado
    Optional<ExameResultado> findByExameSolicitado_IdExameSolicitado(Integer idExameSolicitado);

    // Buscar resultados por paciente (através do exame solicitado)
    List<ExameResultado> findByExameSolicitado_Paciente_IdPaciente(Integer idPaciente);

    // Buscar resultados assinados por um profissional
    List<ExameResultado> findByAssinadoPor_IdProfissional(Integer idProfissional);

    // Buscar resultados por status
    List<ExameResultado> findByStatus(String status);

    // Buscar resultados por período
    List<ExameResultado> findByDataResultadoBetween(LocalDate dataInicio, LocalDate dataFim);

    // Buscar resultados por paciente e status
    List<ExameResultado> findByExameSolicitado_Paciente_IdPacienteAndStatus(Integer idPaciente, String status);

    // Buscar resultados por paciente ordenados por data
    List<ExameResultado> findByExameSolicitado_Paciente_IdPacienteOrderByDataResultadoDesc(Integer idPaciente);

    // Buscar resultados pendentes de assinatura
    List<ExameResultado> findByAssinadoPorIsNull();

}

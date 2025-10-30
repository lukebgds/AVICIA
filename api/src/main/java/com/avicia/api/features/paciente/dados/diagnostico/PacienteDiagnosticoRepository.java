package com.avicia.api.features.paciente.dados.diagnostico;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avicia.api.data.enumerate.StatusDiagnostico;
import com.avicia.api.data.enumerate.TipoDiagnostico;

public interface PacienteDiagnosticoRepository extends JpaRepository<PacienteDiagnostico, Integer> {

    // Buscar todos os diagnósticos de um paciente pelo ID do paciente
    List<PacienteDiagnostico> findByPaciente_IdPaciente(Integer idPaciente);

    // Buscar todos os diagnósticos de um paciente pelo CPF do usuário
    List<PacienteDiagnostico> findByPaciente_Usuario_Cpf(String cpf);

    // Buscar diagnósticos por código CID-10
    List<PacienteDiagnostico> findByPaciente_IdPacienteAndCodigoCidDez(Integer idPaciente, String codigoCidDez);

    // Buscar diagnósticos por tipo
    List<PacienteDiagnostico> findByPaciente_IdPacienteAndTipo(Integer idPaciente, TipoDiagnostico tipo);

    // Buscar diagnósticos por status
    List<PacienteDiagnostico> findByPaciente_IdPacienteAndStatus(Integer idPaciente, StatusDiagnostico status);

    // Buscar diagnósticos por período
    List<PacienteDiagnostico> findByPaciente_IdPacienteAndDataDiagnosticoBetween(
        Integer idPaciente, 
        LocalDate dataInicio, 
        LocalDate dataFim
    );

    // Buscar diagnósticos por tipo e status
    List<PacienteDiagnostico> findByPaciente_IdPacienteAndTipoAndStatus(
        Integer idPaciente, 
        TipoDiagnostico tipo, 
        StatusDiagnostico status
    );

}

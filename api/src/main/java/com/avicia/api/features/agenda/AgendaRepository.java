package com.avicia.api.features.agenda;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.avicia.api.data.enumerate.StatusAgenda;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Integer> {
    
    // Buscar agendas por profissional de saúde
    List<Agenda> findByProfissionalSaude_IdProfissional(Integer idProfissional);
    
    // Buscar agendas por paciente
    List<Agenda> findByPaciente_IdPaciente(Integer idPaciente);
    
    // Buscar agendas por status
    List<Agenda> findByStatus(StatusAgenda status);
    
    // Buscar agendas por profissional e status
    List<Agenda> findByProfissionalSaude_IdProfissionalAndStatus(
        Integer idProfissional, 
        StatusAgenda status
    );
    
    // Buscar agendas por paciente e status
    List<Agenda> findByPaciente_IdPacienteAndStatus(Integer idPaciente, StatusAgenda status);
    
    // Buscar agendas por período
    List<Agenda> findByDataHorarioBetween(LocalDateTime dataInicio, LocalDateTime dataFim);
    
    // Buscar agendas por profissional e período
    List<Agenda> findByProfissionalSaude_IdProfissionalAndDataHorarioBetween(
        Integer idProfissional, 
        LocalDateTime dataInicio, 
        LocalDateTime dataFim
    );
    
    // Buscar agendas por paciente e período
    List<Agenda> findByPaciente_IdPacienteAndDataHorarioBetween(
        Integer idPaciente, 
        LocalDateTime dataInicio, 
        LocalDateTime dataFim
    );
    
    // Buscar agendas futuras do profissional
    List<Agenda> findByProfissionalSaude_IdProfissionalAndDataHorarioAfter(
        Integer idProfissional, 
        LocalDateTime dataHorario
    );
    
    // Buscar agendas futuras do paciente
    List<Agenda> findByPaciente_IdPacienteAndDataHorarioAfter(
        Integer idPaciente, 
        LocalDateTime dataHorario
    );
    
    // Buscar agendas do profissional ordenadas por data
    List<Agenda> findByProfissionalSaude_IdProfissionalOrderByDataHorarioAsc(
        Integer idProfissional
    );
    
    // Buscar agendas do paciente ordenadas por data
    List<Agenda> findByPaciente_IdPacienteOrderByDataHorarioAsc(Integer idPaciente);
}
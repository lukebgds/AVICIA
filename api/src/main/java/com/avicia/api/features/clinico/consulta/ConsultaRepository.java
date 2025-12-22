package com.avicia.api.features.clinico.consulta;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avicia.api.data.enumerate.TipoConsulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {

    // Buscar consultas por paciente
    List<Consulta> findByPaciente_IdPaciente(Integer idPaciente);

    // Buscar consultas por profissional de saúde
    List<Consulta> findByProfissionalSaude_IdProfissional(Integer idProfissionalSaude);

    // Buscar consultas por tipo
    List<Consulta> findByTipoConsulta(TipoConsulta tipoConsulta);

    // Buscar consultas por paciente e tipo
    List<Consulta> findByPaciente_IdPacienteAndTipoConsulta(Integer idPaciente, TipoConsulta tipoConsulta);

    // Buscar consultas por profissional e tipo
    List<Consulta> findByProfissionalSaude_IdProfissionalAndTipoConsulta(
        Integer idProfissionalSaude, 
        TipoConsulta tipoConsulta
    );

    // Buscar consultas por período
    List<Consulta> findByDataConsultaBetween(LocalDateTime dataInicio, LocalDateTime dataFim);

    // Buscar consultas por paciente e período
    List<Consulta> findByPaciente_IdPacienteAndDataConsultaBetween(
        Integer idPaciente, 
        LocalDateTime dataInicio, 
        LocalDateTime dataFim
    );

    // Buscar consultas por profissional e período
    List<Consulta> findByProfissionalSaude_IdProfissionalAndDataConsultaBetween(
        Integer idProfissionalSaude, 
        LocalDateTime dataInicio, 
        LocalDateTime dataFim
    );

    // Buscar consultas do paciente ordenadas por data (mais recentes primeiro)
    List<Consulta> findByPaciente_IdPacienteOrderByDataConsultaDesc(Integer idPaciente);

    // Buscar consultas do profissional ordenadas por data
    List<Consulta> findByProfissionalSaude_IdProfissionalOrderByDataConsultaDesc(
        Integer idProfissionalSaude
    );

    // Buscar consultas por local
    List<Consulta> findByLocalConsultaContainingIgnoreCase(String localConsulta);

}

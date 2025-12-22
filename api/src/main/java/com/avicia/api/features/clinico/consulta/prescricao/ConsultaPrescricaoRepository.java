package com.avicia.api.features.clinico.consulta.prescricao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avicia.api.data.enumerate.StatusPrescricao;

@Repository
public interface ConsultaPrescricaoRepository extends JpaRepository<ConsultaPrescricao, Integer>{

    // Buscar todas as prescrições de uma consulta
    List<ConsultaPrescricao> findByConsulta_IdConsulta(Integer idConsulta);

    // Buscar prescrições por status
    List<ConsultaPrescricao> findByStatus(StatusPrescricao status);

    // Buscar prescrições de uma consulta por status
    List<ConsultaPrescricao> findByConsulta_IdConsultaAndStatus(Integer idConsulta, StatusPrescricao status);

    // Buscar prescrições por período de emissão
    List<ConsultaPrescricao> findByDataEmissaoBetween(LocalDate dataInicio, LocalDate dataFim);

    // Buscar prescrições de uma consulta por período
    List<ConsultaPrescricao> findByConsulta_IdConsultaAndDataEmissaoBetween(
        Integer idConsulta, 
        LocalDate dataInicio, 
        LocalDate dataFim
    );

    // Buscar prescrições emitidas após uma data
    List<ConsultaPrescricao> findByDataEmissaoAfter(LocalDate data);

    // Buscar prescrições de uma consulta ordenadas por data de emissão
    List<ConsultaPrescricao> findByConsulta_IdConsultaOrderByDataEmissaoDesc(Integer idConsulta);

}

package com.avicia.api.features.clinico.consulta.diagnostico;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultaDiagnosticoRepository extends JpaRepository<ConsultaDiagnostico, Integer> {

    // Buscar todos os diagnósticos de uma consulta
    List<ConsultaDiagnostico> findByConsulta_IdConsulta(Integer idConsulta);

    // Buscar diagnósticos por código CID-10
    List<ConsultaDiagnostico> findByCodigoCidDez(String codigoCidDez);

    // Buscar diagnósticos de uma consulta por código CID-10
    List<ConsultaDiagnostico> findByConsulta_IdConsultaAndCodigoCidDez(Integer idConsulta, String codigoCidDez);

    // Buscar diagnósticos por descrição (contém)
    List<ConsultaDiagnostico> findByDescricaoContainingIgnoreCase(String descricao);

}

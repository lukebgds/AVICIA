package com.avicia.api.features.clinico.consulta.diagnostico;

import org.springframework.stereotype.Component;

import com.avicia.api.exception.SystemError;
import com.avicia.api.features.clinico.consulta.Consulta;
import com.avicia.api.features.clinico.consulta.ConsultaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificarConsultaDiagnostico {

    private final ConsultaDiagnosticoRepository consultaDiagnosticoRepository;
    private final ConsultaRepository consultaRepository;
    private final SystemError systemError;

    /**
     * Valida se o ID do diagnóstico não é nulo
     */
    public void validarIdDiagnosticoNaoNulo(Integer id) {
        if (id == null) {
            systemError.error("ID do diagnóstico não pode ser nulo");
        }
    }

    /**
     * Valida se o ID da consulta não é nulo
     */
    public void validarIdConsultaNaoNulo(Integer idConsulta) {
        if (idConsulta == null) {
            systemError.error("ID da consulta não pode ser nulo");
        }
    }

    /**
     * Valida se o código CID-10 não é nulo ou vazio
     */
    public void validarCodigoCidDezNaoVazio(String codigoCidDez) {
        if (codigoCidDez == null || codigoCidDez.trim().isEmpty()) {
            systemError.error("Código CID-10 não pode ser vazio");
        }
    }

    /**
     * Busca diagnóstico por ID ou lança exceção
     */
    public ConsultaDiagnostico buscarDiagnosticoPorId(Integer id) {
        validarIdDiagnosticoNaoNulo(id);
        ConsultaDiagnostico diagnostico = consultaDiagnosticoRepository.findById(id).orElse(null);
        if (diagnostico == null) {
            systemError.error("Diagnóstico com ID %d não encontrado", id);
        }
        return diagnostico;
    }

    /**
     * Busca consulta por ID ou lança exceção
     */
    public Consulta buscarConsultaPorId(Integer idConsulta) {
        validarIdConsultaNaoNulo(idConsulta);
        Consulta consulta = consultaRepository.findById(idConsulta).orElse(null);
        if (consulta == null) {
            systemError.error("Consulta com ID %d não encontrada", idConsulta);
        }
        return consulta;
    }

    /**
     * Verifica se o ID do diagnóstico já existe
     */
    public boolean idDiagnosticoExiste(Integer idDiagnostico) {
        return consultaDiagnosticoRepository.existsById(idDiagnostico);
    }

}

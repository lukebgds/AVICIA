package com.avicia.api.features.clinico.consulta.prescricao;

import org.springframework.stereotype.Component;

import com.avicia.api.exception.SystemError;
import com.avicia.api.features.clinico.consulta.Consulta;
import com.avicia.api.features.clinico.consulta.ConsultaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificarConsultaPrescricao {

    private final ConsultaPrescricaoRepository consultaPrescricaoRepository;
    private final ConsultaRepository consultaRepository;
    private final SystemError systemError;

    /**
     * Valida se o ID da prescrição não é nulo
     */
    public void validarIdPrescricaoNaoNulo(Integer id) {
        if (id == null) {
            systemError.error("ID da prescrição não pode ser nulo");
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
     * Valida se a data de emissão não é nula
     */
    public void validarDataEmissaoNaoNula(java.time.LocalDate dataEmissao) {
        if (dataEmissao == null) {
            systemError.error("Data de emissão da prescrição não pode ser nula");
        }
    }

    /**
     * Valida se o status não é nulo
     */
    public void validarStatusNaoNulo(com.avicia.api.data.enumerate.StatusPrescricao status) {
        if (status == null) {
            systemError.error("Status da prescrição não pode ser nulo");
        }
    }

    /**
     * Busca prescrição por ID ou lança exceção
     */
    public ConsultaPrescricao buscarPrescricaoPorId(Integer id) {
        validarIdPrescricaoNaoNulo(id);
        ConsultaPrescricao prescricao = consultaPrescricaoRepository.findById(id).orElse(null);
        if (prescricao == null) {
            systemError.error("Prescrição com ID %d não encontrada", id);
        }
        return prescricao;
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
     * Verifica se o ID da prescrição já existe
     */
    public boolean idPrescricaoExiste(Integer idPrescricao) {
        return consultaPrescricaoRepository.existsById(idPrescricao);
    }

}

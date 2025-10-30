package com.avicia.api.features.clinico.consulta.prescricao.item;

import org.springframework.stereotype.Component;

import com.avicia.api.exception.SystemError;
import com.avicia.api.features.clinico.consulta.prescricao.ConsultaPrescricao;
import com.avicia.api.features.clinico.consulta.prescricao.ConsultaPrescricaoRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificarPrescricaoItem {

    private final PrescricaoItemRepository prescricaoItemRepository;
    private final ConsultaPrescricaoRepository consultaPrescricaoRepository;
    private final SystemError systemError;

    /**
     * Valida se o ID do item não é nulo
     */
    public void validarIdItemNaoNulo(Integer id) {
        if (id == null) {
            systemError.error("ID do item não pode ser nulo");
        }
    }

    /**
     * Valida se o ID da prescrição não é nulo
     */
    public void validarIdPrescricaoNaoNulo(Integer idPrescricao) {
        if (idPrescricao == null) {
            systemError.error("ID da prescrição não pode ser nulo");
        }
    }

    /**
     * Valida se o nome do medicamento não é nulo ou vazio
     */
    public void validarMedicamentoNaoVazio(String medicamento) {
        if (medicamento == null || medicamento.trim().isEmpty()) {
            systemError.error("Nome do medicamento não pode ser vazio");
        }
    }

    /**
     * Valida se a dosagem não é nula ou vazia
     */
    public void validarDosagemNaoVazia(String dosagem) {
        if (dosagem == null || dosagem.trim().isEmpty()) {
            systemError.error("Dosagem não pode ser vazia");
        }
    }

    /**
     * Busca item por ID ou lança exceção
     */
    public PrescricaoItem buscarItemPorId(Integer id) {
        validarIdItemNaoNulo(id);
        PrescricaoItem item = prescricaoItemRepository.findById(id).orElse(null);
        if (item == null) {
            systemError.error("Item com ID %d não encontrado", id);
        }
        return item;
    }

    /**
     * Busca prescrição por ID ou lança exceção
     */
    public ConsultaPrescricao buscarPrescricaoPorId(Integer idPrescricao) {
        validarIdPrescricaoNaoNulo(idPrescricao);
        ConsultaPrescricao prescricao = consultaPrescricaoRepository.findById(idPrescricao).orElse(null);
        if (prescricao == null) {
            systemError.error("Prescrição com ID %d não encontrada", idPrescricao);
        }
        return prescricao;
    }

    /**
     * Verifica se o ID do item já existe
     */
    public boolean idItemExiste(Integer idItem) {
        return prescricaoItemRepository.existsById(idItem);
    }

}

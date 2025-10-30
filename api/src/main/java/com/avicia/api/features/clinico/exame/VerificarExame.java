package com.avicia.api.features.clinico.exame;

import org.springframework.stereotype.Component;

import com.avicia.api.exception.SystemError;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificarExame {

    private final ExameRepository exameRepository;
    private final SystemError systemError;

    /**
     * Valida se o ID do exame não é nulo
     */
    public void validarIdExameNaoNulo(Integer id) {
        if (id == null) {
            systemError.error("ID do exame não pode ser nulo");
        }
    }

    /**
     * Valida se o nome do exame não é nulo ou vazio
     */
    public void validarNomeExameNaoVazio(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            systemError.error("Nome do exame não pode ser vazio");
        }
    }

    /**
     * Valida se o tipo do exame não é nulo ou vazio
     */
    public void validarTipoExameNaoVazio(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            systemError.error("Tipo do exame não pode ser vazio");
        }
    }

    /**
     * Valida se o status ativo não é nulo
     */
    public void validarAtivoNaoNulo(Boolean ativo) {
        if (ativo == null) {
            systemError.error("Status ativo não pode ser nulo");
        }
    }

    /**
     * Busca exame por ID ou lança exceção
     */
    public Exame buscarExamePorId(Integer id) {
        validarIdExameNaoNulo(id);
        Exame exame = exameRepository.findById(id).orElse(null);
        if (exame == null) {
            systemError.error("Exame com ID %d não encontrado", id);
        }
        return exame;
    }

    /**
     * Verifica se o ID do exame já existe
     */
    public boolean idExameExiste(Integer idExame) {
        return exameRepository.existsById(idExame);
    }

}

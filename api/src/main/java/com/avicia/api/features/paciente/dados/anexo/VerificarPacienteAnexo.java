package com.avicia.api.features.paciente.dados.anexo;

import org.springframework.stereotype.Component;

import com.avicia.api.exception.SystemError;
import com.avicia.api.features.paciente.Paciente;
import com.avicia.api.features.paciente.PacienteRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificarPacienteAnexo {

    private final PacienteAnexoRepository pacienteAnexoRepository;
    private final PacienteRepository pacienteRepository;
    private final SystemError systemError;

    /**
     * Valida se o ID do anexo não é nulo
     */
    public void validarIdAnexoNaoNulo(Integer id) {
        if (id == null) {
            systemError.error("ID do anexo não pode ser nulo");
        }
    }

    /**
     * Valida se o ID do paciente não é nulo
     */
    public void validarIdPacienteNaoNulo(Integer idPaciente) {
        if (idPaciente == null) {
            systemError.error("ID do paciente não pode ser nulo");
        }
    }

    /**
     * Valida se a URL do arquivo não é nula ou vazia
     */
    public void validarUrlArquivoNaoVazia(String urlArquivo) {
        if (urlArquivo == null || urlArquivo.trim().isEmpty()) {
            systemError.error("URL do arquivo não pode ser vazia");
        }
    }

    /**
     * Busca anexo por ID ou lança exceção
     */
    public PacienteAnexo buscarAnexoPorId(Integer id) {
        validarIdAnexoNaoNulo(id);
        PacienteAnexo anexo = pacienteAnexoRepository.findById(id).orElse(null);
        if (anexo == null) {
            systemError.error("Anexo com ID %d não encontrado", id);
        }
        return anexo;
    }

    /**
     * Busca paciente por ID ou lança exceção
     */
    public Paciente buscarPacientePorId(Integer idPaciente) {
        validarIdPacienteNaoNulo(idPaciente);
        Paciente paciente = pacienteRepository.findById(idPaciente).orElse(null);
        if (paciente == null) {
            systemError.error("Paciente com ID %d não encontrado", idPaciente);
        }
        return paciente;
    }

    /**
     * Verifica se o ID do anexo já existe
     */
    public boolean idAnexoExiste(Integer idAnexo) {
        return pacienteAnexoRepository.existsById(idAnexo);
    }

}

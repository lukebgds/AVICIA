package com.avicia.api.security.verify;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.avicia.api.data.dto.request.system.SystemLogRequest;
import com.avicia.api.data.enumerate.TipoSystemLog;
import com.avicia.api.exception.SystemError;
import com.avicia.api.model.SystemLog;
import com.avicia.api.model.Usuario;
import com.avicia.api.repository.SystemLogRepository;
import com.avicia.api.repository.UsuarioRepository;
import com.avicia.api.util.Throwing;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificarSystemLog {

    private final SystemLogRepository systemLogRepository;
    private final UsuarioRepository usuarioRepository;
    private final SystemError systemError;

    /**
     * Valida se o request de log não é nulo
     */
    public void validarRequestNaoNulo(SystemLogRequest dto) {
        if (dto == null) {
            systemError.error("Request de log não pode ser nulo");
        }
    }

    /**
     * Valida se o tipo de log não é nulo
     */
    public void validarTipoLogNaoNulo(TipoSystemLog tipoLog) {
        if (tipoLog == null) {
            systemError.error("Tipo de log não pode ser nulo");
        }
    }

    /**
     * Valida se a ação do log não é nula ou vazia
     */
    public void validarAcaoNaoVazia(String acao) {
        if (acao == null || acao.trim().isEmpty()) {
            systemError.error("Ação do log não pode ser vazia");
        }
    }

    /**
     * Valida se a entidade afetada não é nula ou vazia
     */
    public void validarEntidadeNaoVazia(String entidadeAfetada) {
        if (entidadeAfetada == null || entidadeAfetada.trim().isEmpty()) {
            systemError.error("Entidade afetada não pode ser vazia");
        }
    }

    /**
     * Valida se o ID do log não é nulo
     */
    public void validarIdLogNaoNulo(Integer id) {
        if (id == null) {
            systemError.error("ID do log não pode ser nulo");
        }
    }

    /**
     * Valida se o ID do usuário não é nulo
     */
    public void validarIdUsuarioNaoNulo(Integer idUsuario) {
        if (idUsuario == null) {
            systemError.error("ID do usuário não pode ser nulo");
        }
    }

    /**
     * Valida se a data não é nula
     */
    public void validarDataNaoNula(LocalDateTime data, String nomeCampo) {
        if (data == null) {
            systemError.error("%s não pode ser nula", nomeCampo);
        }
    }

    /**
     * Valida se a data de início não é posterior à data de fim
     */
    public void validarPeriodoValido(LocalDateTime dataInicio, LocalDateTime dataFim) {
        if (dataInicio.isAfter(dataFim)) {
            systemError.error("Data de início não pode ser posterior à data de fim");
        }
    }

    /**
     * Valida se a data limite não está no futuro
     */
    public void validarDataLimiteNaoFuturo(LocalDateTime dataLimite) {
        if (dataLimite.isAfter(LocalDateTime.now())) {
            systemError.error("Data limite não pode ser no futuro");
        }
    }

    /**
     * Busca log por ID ou lança exceção
     */
    public SystemLog buscarLogPorId(Integer id) {
        validarIdLogNaoNulo(id);
        
        return systemLogRepository.findById(id)
            .orElseThrow(Throwing.supplier(() ->
                systemError.error("Log com ID %d não encontrado", id)
            ));
    }

    /**
     * Verifica se o usuário existe pelo ID
     */
    public Usuario verificarUsuarioExiste(Integer idUsuario) {
        return usuarioRepository.findById(idUsuario)
            .orElseThrow(Throwing.supplier(() ->
                systemError.error("Usuário com ID %d não encontrado para registro de log", idUsuario)
            ));
    }

    /**
     * Verifica se o usuário existe (apenas validação booleana)
     */
    public void verificarUsuarioExisteBoolean(Integer idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            systemError.error("Usuário com ID %d não encontrado", idUsuario);
        }
    }

    /**
     * Valida todos os campos obrigatórios do SystemLogRequest
     */
    public void validarCamposObrigatorios(SystemLogRequest dto) {
        validarRequestNaoNulo(dto);
        validarTipoLogNaoNulo(dto.getTipoLog());
        validarAcaoNaoVazia(dto.getAcao());
        validarEntidadeNaoVazia(dto.getEntidadeAfetada());
    }

    /**
     * Valida período completo (ambas as datas e ordem)
     */
    public void validarPeriodoCompleto(LocalDateTime dataInicio, LocalDateTime dataFim) {
        validarDataNaoNula(dataInicio, "Data de início");
        validarDataNaoNula(dataFim, "Data de fim");
        validarPeriodoValido(dataInicio, dataFim);
    }

    /**
     * Valida data limite para exclusão de logs antigos
     */
    public void validarDataLimiteExclusao(LocalDateTime dataLimite) {
        validarDataNaoNula(dataLimite, "Data limite");
        validarDataLimiteNaoFuturo(dataLimite);
    }
}
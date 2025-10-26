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

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificarSystemLog {

    private final SystemLogRepository systemLogRepository;
    private final UsuarioRepository usuarioRepository;
    private final SystemError systemError;

    public void validarRequestNaoNulo(SystemLogRequest dto) {
        if (dto == null) {
            systemError.error("Request de log não pode ser nulo");
        }
    }

    public void validarTipoLogNaoNulo(TipoSystemLog tipoLog) {
        if (tipoLog == null) {
            systemError.error("Tipo de log não pode ser nulo");
        }
    }

    public void validarAcaoNaoVazia(String acao) {
        if (acao == null || acao.trim().isEmpty()) {
            systemError.error("Ação do log não pode ser vazia");
        }
    }

    public void validarEntidadeNaoVazia(String entidadeAfetada) {
        if (entidadeAfetada == null || entidadeAfetada.trim().isEmpty()) {
            systemError.error("Entidade afetada não pode ser vazia");
        }
    }

    public void validarIdLogNaoNulo(Integer id) {
        if (id == null) {
            systemError.error("ID do log não pode ser nulo");
        }
    }

    public void validarIdUsuarioNaoNulo(Integer idUsuario) {
        if (idUsuario == null) {
            systemError.error("ID do usuário não pode ser nulo");
        }
    }

    public void validarDataNaoNula(LocalDateTime data, String nomeCampo) {
        if (data == null) {
            systemError.error("%s não pode ser nula", nomeCampo);
        }
    }

    public void validarPeriodoValido(LocalDateTime dataInicio, LocalDateTime dataFim) {
        if (dataInicio.isAfter(dataFim)) {
            systemError.error("Data de início não pode ser posterior à data de fim");
        }
    }

    public void validarDataLimiteNaoFuturo(LocalDateTime dataLimite) {
        if (dataLimite.isAfter(LocalDateTime.now())) {
            systemError.error("Data limite não pode ser no futuro");
        }
    }

    public SystemLog buscarLogPorId(Integer id) {
        validarIdLogNaoNulo(id);
        var log = systemLogRepository.findById(id).orElse(null);
        if (log == null) {
            systemError.error("Log com ID %d não encontrado", id);
        }
        return log;
    }

    public Usuario verificarUsuarioExiste(Integer idUsuario) {
        var usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (usuario == null) {
            systemError.error("Usuário com ID %d não encontrado para registro de log", idUsuario);
        }
        return usuario;
    }

    public void verificarUsuarioExisteBoolean(Integer idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            systemError.error("Usuário com ID %d não encontrado", idUsuario);
        }
    }

    public void validarCamposObrigatorios(SystemLogRequest dto) {
        validarRequestNaoNulo(dto);
        validarTipoLogNaoNulo(dto.getTipoLog());
        validarAcaoNaoVazia(dto.getAcao());
        validarEntidadeNaoVazia(dto.getEntidadeAfetada());
    }

    public void validarPeriodoCompleto(LocalDateTime dataInicio, LocalDateTime dataFim) {
        validarDataNaoNula(dataInicio, "Data de início");
        validarDataNaoNula(dataFim, "Data de fim");
        validarPeriodoValido(dataInicio, dataFim);
    }

    public void validarDataLimiteExclusao(LocalDateTime dataLimite) {
        validarDataNaoNula(dataLimite, "Data limite");
        validarDataLimiteNaoFuturo(dataLimite);
    }
}

package com.avicia.api.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.avicia.api.exception.SystemError;
import com.avicia.api.features.funcionario.Funcionario;
import com.avicia.api.features.funcionario.FuncionarioRepository;
import com.avicia.api.features.paciente.Paciente;
import com.avicia.api.features.paciente.PacienteRepository;
import com.avicia.api.features.profissional.ProfissionalSaude;
import com.avicia.api.features.profissional.ProfissionalSaudeRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UsuarioAutenticadoUtil {

    private final PacienteRepository pacienteRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final ProfissionalSaudeRepository profissionalSaudeRepository;
    private final SystemError systemError;

    public Integer getIdUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getClaim("idUsuario");
        }
        systemError.error("ID do usuário não encontrado no token");
        throw new IllegalStateException("ID do usuário não encontrado no token");
    }

    public String getRoleUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getClaim("role");
        }
        systemError.error("Tipo de usuário não encontrado no token");
        throw new IllegalStateException("Tipo de usuário não encontrado no token");
    }

    public Integer getIdPaciente() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof Jwt jwt) {  
            Integer idUsuarioToken = jwt.getClaim("idUsuario");
            Integer idPaciente = pacienteRepository.findByUsuario_IdUsuario(idUsuarioToken)
                .map(Paciente::getIdPaciente)
                .orElse(null);

            if (idPaciente == null) {
                systemError.error(idUsuarioToken, "Paciente", "Não encontrado ID de Paciente para o Usuário: %d", idUsuarioToken);
            }

            return idPaciente;
        }
        systemError.error("ID do usuário não encontrado no token");
        throw new IllegalStateException("ID do usuário não encontrado no token");
    }

    public Integer getIdFuncionario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            Integer idUsuarioToken = jwt.getClaim("idUsuario");
            Integer idFuncionario = funcionarioRepository.findByUsuario_IdUsuario(idUsuarioToken)
                .map(Funcionario::getIdFuncionario)
                .orElse(null);

            if (idFuncionario == null) {
                systemError.error(idUsuarioToken, "Funcionario", "Não encontrado ID de Funcionario para o Usuário: %d", idUsuarioToken);
            }

            return idFuncionario;
        }
        systemError.error("ID do usuário não encontrado no token");
        throw new IllegalStateException("ID do usuário não encontrado no token");
    }

    public Integer getIdProfissionalSaude() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            Integer idUsuarioToken = jwt.getClaim("idUsuario");
            Integer idProfissional = profissionalSaudeRepository.findByUsuario_IdUsuario(idUsuarioToken)
                .map(ProfissionalSaude::getIdProfissional)
                .orElse(null);

            if (idProfissional == null) {
                systemError.error(idUsuarioToken, "Profissional de Saude", "Não encontrado ID de Profissional de Saude para o Usuário: %d", idUsuarioToken);
            }

            return idProfissional;
        }
        systemError.error("ID do usuário não encontrado no token");
        throw new IllegalStateException("ID do usuário não encontrado no token");
    }
}

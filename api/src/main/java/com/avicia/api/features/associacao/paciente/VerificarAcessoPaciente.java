package com.avicia.api.features.associacao.paciente;

import org.springframework.stereotype.Component;

import com.avicia.api.data.serializer.PacienteFuncionarioId;
import com.avicia.api.data.serializer.PacienteProfissionalSaudeId;
import com.avicia.api.data.serializer.PacienteUsuarioId;
import com.avicia.api.exception.SystemError;
import com.avicia.api.features.associacao.paciente.funcionario.PacienteFuncionarioRepository;
import com.avicia.api.features.associacao.paciente.profissional.PacienteProfissionalSaudeRepository;
import com.avicia.api.features.associacao.paciente.usuario.PacienteUsuarioRepository;
import com.avicia.api.features.sistema.role.Role;
import com.avicia.api.features.sistema.role.RoleRepository;
import com.avicia.api.util.CustomGetter;
import com.avicia.api.util.UsuarioAutenticadoUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificarAcessoPaciente {

    private final PacienteProfissionalSaudeRepository pacienteProfissionalSaudeRepository;
    private final PacienteFuncionarioRepository pacienteFuncionarioRepository;
    private final PacienteUsuarioRepository pacienteUsuarioRepository;
    private final RoleRepository roleRepository;

    private final UsuarioAutenticadoUtil usuarioAutenticadoUtil;
    private final CustomGetter customGetter;
    private final SystemError systemError;

    /**
     * Verifica se um profissional de saúde tem acesso ao paciente
     */
    public void verificarAcessoProfissional(Integer idProfissional, Integer idPaciente) {
        boolean vinculoExiste = pacienteProfissionalSaudeRepository.existsById(
            new PacienteProfissionalSaudeId(idProfissional, idPaciente)
        );

        if (!vinculoExiste) {
            systemError.error(idProfissional, "Paciente",
                "Profissional de saúde não possui vínculo com o paciente de ID %d", idPaciente);
        }
    }

    /**
     * Verifica se um funcionário tem acesso ao paciente
     */
    public void verificarAcessoFuncionario(Integer idFuncionario, Integer idPaciente) {
        boolean vinculoExiste = pacienteFuncionarioRepository.existsById(
            new PacienteFuncionarioId(idFuncionario, idPaciente)
        );

        if (!vinculoExiste) {
            systemError.error(idFuncionario, "Paciente",
                "Funcionário não possui vínculo com o paciente de ID %d", idPaciente);
        }
    }

    /**
     * Verifica se um usuário genérico (ex: admin, suporte) tem acesso ao paciente
     */
    public void verificarAcessoUsuario(Integer idUsuario, Integer idPaciente) {
        boolean vinculoExiste = pacienteUsuarioRepository.existsById(
            new PacienteUsuarioId(idUsuario, idPaciente)
        );

        if (!vinculoExiste) {
            systemError.error(idUsuario, "Paciente",
                "Usuário não possui vínculo com o paciente de ID %d", idPaciente);
        }
    }

    /**
     * Verifica se QUALQUER tipo de vínculo (profissional, funcionário ou admin genérico) existe,
     * com base na role cadastrada em banco de dados.
     */
    private void verificarAcessoGeral(Integer idGenerico, Integer idPaciente, String roleNome, Integer primeiroDigito) {

        boolean autorizado = switch (primeiroDigito) {
            case 3 -> pacienteProfissionalSaudeRepository.existsById(
                        new PacienteProfissionalSaudeId(idGenerico, idPaciente));
            case 5 -> pacienteFuncionarioRepository.existsById(
                        new PacienteFuncionarioId(idGenerico, idPaciente));
            case 1 -> pacienteUsuarioRepository.existsById(
                        new PacienteUsuarioId(idGenerico, idPaciente));
            default -> false;
        };

        // Caso nenhum vínculo seja encontrado
        if (!autorizado) {
            String tipoUsuario = switch (primeiroDigito) {
                case 3 -> "Profissional de Saúde";
                case 5 -> "Funcionário";
                case 1 -> "System.Admin";
                default -> "Usuário Desconhecido";
            };

            systemError.error(idGenerico, "Paciente",
                    "%s não possui vínculo com o paciente de ID %d", tipoUsuario, idPaciente);
        }
    }

    /*
     *  Pega as informações do usuário pelo token de sessão e verifica se tem associação
     */
    public void verificarAcesso(String cpf) {

        // Buscar role pelo nome vindo do JWT
        String roleNome = usuarioAutenticadoUtil.getRoleUsuario();
        
        Role role = roleRepository.findByNome(roleNome)
            .orElseThrow(() -> new IllegalArgumentException("Role não encontrada: " + roleNome));

        // Extrai o primeiro dígito do ID
        int primeiroDigito = Integer.parseInt(String.valueOf(role.getIdRole()).substring(0, 1));

        Integer idGenerico = switch (primeiroDigito) {
            case 3 -> usuarioAutenticadoUtil.getIdProfissionalSaude();
            case 5 -> usuarioAutenticadoUtil.getIdFuncionario();
            case 1 -> usuarioAutenticadoUtil.getIdUsuario();
            default -> 1;
        };
            
        Integer idPaciente = customGetter.getIdPacienteByCpf(cpf);
        
        verificarAcessoGeral(idGenerico, idPaciente, roleNome, primeiroDigito);

    }

}

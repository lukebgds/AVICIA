package com.avicia.api.features.clinico.exame.solicitado;

import org.springframework.stereotype.Component;

import com.avicia.api.exception.SystemError;
import com.avicia.api.features.clinico.consulta.Consulta;
import com.avicia.api.features.clinico.consulta.ConsultaRepository;
import com.avicia.api.features.clinico.exame.Exame;
import com.avicia.api.features.clinico.exame.ExameRepository;
import com.avicia.api.features.paciente.Paciente;
import com.avicia.api.features.paciente.PacienteRepository;
import com.avicia.api.features.profissional.ProfissionalSaude;
import com.avicia.api.features.profissional.ProfissionalSaudeRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificarExameSolicitado {

    private final ExameSolicitadoRepository exameSolicitadoRepository;
    private final ConsultaRepository consultaRepository;
    private final PacienteRepository pacienteRepository;
    private final ExameRepository exameRepository;
    private final ProfissionalSaudeRepository profissionalSaudeRepository;
    private final SystemError systemError;

    public void validarIdExameSolicitadoNaoNulo(Integer id) {
        if (id == null) systemError.error("ID do exame solicitado não pode ser nulo");
    }

    public void validarIdPacienteNaoNulo(Integer id) {
        if (id == null) systemError.error("ID do paciente não pode ser nulo");
    }

    public void validarIdExameNaoNulo(Integer id) {
        if (id == null) systemError.error("ID do exame não pode ser nulo");
    }

    public void validarIdProfissionalNaoNulo(Integer id) {
        if (id == null) systemError.error("ID do profissional não pode ser nulo");
    }

    public void validarDataSolicitacaoNaoNula(java.time.LocalDate data) {
        if (data == null) systemError.error("Data de solicitação não pode ser nula");
    }

    public void validarStatusNaoVazio(String status) {
        if (status == null || status.trim().isEmpty()) systemError.error("Status não pode ser vazio");
    }

    public ExameSolicitado buscarExameSolicitadoPorId(Integer id) {
        validarIdExameSolicitadoNaoNulo(id);
        ExameSolicitado exame = exameSolicitadoRepository.findById(id).orElse(null);
        if (exame == null) systemError.error("Exame solicitado com ID %d não encontrado", id);
        return exame;
    }

    public Consulta buscarConsultaPorId(Integer id) {
        Consulta consulta = consultaRepository.findById(id).orElse(null);
        if (consulta == null) systemError.error("Consulta com ID %d não encontrada", id);
        return consulta;
    }

    public Paciente buscarPacientePorId(Integer id) {
        validarIdPacienteNaoNulo(id);
        Paciente paciente = pacienteRepository.findById(id).orElse(null);
        if (paciente == null) systemError.error("Paciente com ID %d não encontrado", id);
        return paciente;
    }

    public Exame buscarExamePorId(Integer id) {
        validarIdExameNaoNulo(id);
        Exame exame = exameRepository.findById(id).orElse(null);
        if (exame == null) systemError.error("Exame com ID %d não encontrado", id);
        return exame;
    }

    public ProfissionalSaude buscarProfissionalPorId(Integer id) {
        validarIdProfissionalNaoNulo(id);
        ProfissionalSaude prof = profissionalSaudeRepository.findById(id).orElse(null);
        if (prof == null) systemError.error("Profissional com ID %d não encontrado", id);
        return prof;
    }

}

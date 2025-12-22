package com.avicia.api.features.clinico.internacao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InternacaoRepository extends JpaRepository<Internacao, Integer> {

    // Buscar internações por paciente
    List<Internacao> findByPaciente_IdPaciente(Integer idPaciente);

    // Buscar internações por profissional de saúde
    List<Internacao> findByProfissionalSaude_IdProfissional(Integer idProfissional);

    // Buscar internações ativas (sem data de alta)
    List<Internacao> findByDataAltaIsNull();

    // Buscar internações ativas por paciente
    List<Internacao> findByPaciente_IdPacienteAndDataAltaIsNull(Integer idPaciente);

    // Buscar internações finalizadas (com data de alta)
    List<Internacao> findByDataAltaIsNotNull();

    // Buscar internações por período de admissão
    List<Internacao> findByDataAdmissaoBetween(LocalDate dataInicio, LocalDate dataFim);

    // Buscar internações por leito
    List<Internacao> findByLeitoContainingIgnoreCase(String leito);

    // Buscar internações ativas por leito
    List<Internacao> findByLeitoAndDataAltaIsNull(String leito);

    // Buscar internações do paciente ordenadas por data de admissão
    List<Internacao> findByPaciente_IdPacienteOrderByDataAdmissaoDesc(Integer idPaciente);

}

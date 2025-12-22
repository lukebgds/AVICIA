package com.avicia.api.features.paciente.dados.convenio;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteConvenioRepository extends JpaRepository<PacienteConvenio, Integer> {

    // Buscar todos os convênios de um paciente pelo ID do paciente
    List<PacienteConvenio> findByPaciente_IdPaciente(Integer idPaciente);

    // Buscar todos os convênios de um paciente pelo CPF do usuário
    List<PacienteConvenio> findByPaciente_Usuario_Cpf(String cpf);

    // Buscar convênio por número de carteirinha
    Optional<PacienteConvenio> findByNumeroCarteirinha(String numeroCarteirinha);

    // Buscar convênios por nome do convênio
    List<PacienteConvenio> findByPaciente_IdPacienteAndNomeConvenio(Integer idPaciente, String nomeConvenio);

    // Buscar convênios válidos (validade maior ou igual à data atual)
    List<PacienteConvenio> findByPaciente_IdPacienteAndValidadeGreaterThanEqual(Integer idPaciente, LocalDate data);

    // Buscar convênios vencidos (validade menor que a data atual)
    List<PacienteConvenio> findByPaciente_IdPacienteAndValidadeLessThan(Integer idPaciente, LocalDate data);

}

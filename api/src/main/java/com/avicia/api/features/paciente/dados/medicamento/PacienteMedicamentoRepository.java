package com.avicia.api.features.paciente.dados.medicamento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteMedicamentoRepository extends JpaRepository<PacienteMedicamento, Integer> {

    // Buscar todos os medicamentos de um paciente pelo ID do paciente
    List<PacienteMedicamento> findByPaciente_IdPaciente(Integer idPaciente);

    // Buscar todos os medicamentos de um paciente pelo CPF do usuário
    List<PacienteMedicamento> findByPaciente_Usuario_Cpf(String cpf);

    // Buscar medicamentos por nome (contém)
    List<PacienteMedicamento> findByPaciente_IdPacienteAndMedicamentoContainingIgnoreCase(
        Integer idPaciente, 
        String medicamento
    );

    // Buscar medicamentos por frequência
    List<PacienteMedicamento> findByPaciente_IdPacienteAndFrequencia(Integer idPaciente, String frequencia);

}

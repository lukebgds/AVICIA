package com.avicia.api.features.clinico.consulta.prescricao.item;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescricaoItemRepository extends JpaRepository<PrescricaoItem, Integer> {

    // Buscar todos os itens de uma prescrição
    List<PrescricaoItem> findByConsultaPrescricao_IdPrescricao(Integer idPrescricao);

    // Buscar itens por medicamento (contém)
    List<PrescricaoItem> findByMedicamentoContainingIgnoreCase(String medicamento);

    // Buscar itens de uma prescrição por medicamento
    List<PrescricaoItem> findByConsultaPrescricao_IdPrescricaoAndMedicamentoContainingIgnoreCase(
        Integer idPrescricao, 
        String medicamento
    );

    // Buscar itens por frequência
    List<PrescricaoItem> findByFrequencia(String frequencia);

    // Buscar itens de uma prescrição por frequência
    List<PrescricaoItem> findByConsultaPrescricao_IdPrescricaoAndFrequencia(
        Integer idPrescricao, 
        String frequencia
    );

}

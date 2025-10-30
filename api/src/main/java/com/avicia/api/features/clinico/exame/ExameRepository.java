package com.avicia.api.features.clinico.exame;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExameRepository extends JpaRepository<Exame, Integer> {

    // Buscar exames por nome (contém)
    List<Exame> findByNomeContainingIgnoreCase(String nome);

    // Buscar exames por tipo
    List<Exame> findByTipo(String tipo);

    // Buscar exames ativos ou inativos
    List<Exame> findByAtivo(Boolean ativo);

    // Buscar exames por tipo e status ativo
    List<Exame> findByTipoAndAtivo(String tipo, Boolean ativo);

    // Buscar exames ativos ordenados por nome
    List<Exame> findByAtivoOrderByNomeAsc(Boolean ativo);

    // Buscar exames por tipo ordenados por nome
    List<Exame> findByTipoOrderByNomeAsc(String tipo);
    
}

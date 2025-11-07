package com.avicia.api.features.associacao.paciente.profissional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.avicia.api.data.serializer.PacienteProfissionalSaudeId;

@Repository
public interface PacienteProfissionalSaudeRepository extends JpaRepository<PacienteProfissionalSaude, PacienteProfissionalSaudeId>{
    
    @Query("""
        SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END
        FROM PacienteProfissionalSaude p
        WHERE p.id.idProfissional = :idProfissional
          AND p.id.idPaciente = :idPaciente
        """)
    boolean existsVinculo(@Param("idProfissional") Integer idProfissional, @Param("idPaciente") Integer idPaciente);

}

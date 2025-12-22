package com.avicia.api.features.associacao.paciente.funcionario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.avicia.api.data.serializer.PacienteFuncionarioId;

@Repository
public interface PacienteFuncionarioRepository extends JpaRepository<PacienteFuncionario, PacienteFuncionarioId> {
    
    @Query("""
        SELECT CASE WHEN COUNT(pf) > 0 THEN TRUE ELSE FALSE END
        FROM PacienteFuncionario pf
        WHERE pf.funcionario.idFuncionario = :idFuncionario
          AND pf.paciente.idPaciente = :idPaciente
    """)
    boolean existsVinculo(@Param("idFuncionario") Integer idFuncionario, @Param("idPaciente") Integer idPaciente);

    @Query("""
        SELECT p FROM PacienteFuncionario p
        WHERE p.funcionario.idFuncionario = :idFuncionario
    """)
    List<PacienteFuncionario> findAllByFuncionarioId(Integer idFuncionario);


    
}

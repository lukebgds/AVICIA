package com.avicia.api.features.associacao.paciente.usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.avicia.api.data.serializer.PacienteUsuarioId;

@Repository
public interface PacienteUsuarioRepository extends JpaRepository<PacienteUsuario, PacienteUsuarioId> {  

    @Query("""
        SELECT CASE WHEN COUNT(pu) > 0 THEN TRUE ELSE FALSE END
        FROM PacienteUsuario pu
        WHERE pu.usuario.idUsuario = :idUsuario
          AND pu.paciente.idPaciente = :idPaciente
    """)
    boolean existsVinculo(@Param("idUsuario") Integer idUsuario, @Param("idPaciente") Integer idPaciente);


    @Query("""
        SELECT p FROM PacienteUsuario p
        WHERE p.usuario.idUsuario = :idUsuario
    """)
    List<PacienteUsuario> findAllByUsuarioId(Integer idUsuario);

}

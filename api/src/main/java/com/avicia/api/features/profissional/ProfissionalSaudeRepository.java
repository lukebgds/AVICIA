package com.avicia.api.features.profissional;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfissionalSaudeRepository extends JpaRepository<ProfissionalSaude, Integer>{

    // Buscar por Matrícula
    Optional<ProfissionalSaude> findByMatricula(String matricula);

    // Buscar por Resgistro de Conselho
    Optional<ProfissionalSaude> findByRegistroConselho(String registroConselho);

    // Buscar Profissional de saúde pelo ID do usuário
    Optional<ProfissionalSaude> findByUsuario_IdUsuario(Integer idUsuario);
	
	// Buscar Profissional de saúde pelo ID do profissional de saúde;
	Optional<ProfissionalSaude> findByIdProfissional(Integer idProfissional);

}

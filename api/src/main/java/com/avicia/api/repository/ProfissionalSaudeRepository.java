package com.avicia.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avicia.api.model.ProfissionalSaude;

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

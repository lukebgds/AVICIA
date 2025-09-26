package com.avicia.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avicia.api.model.Funcionario;


@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

    // Buscar funcionário pela matrícula
    Optional<Funcionario> findByMatricula(String matricula);

    // Buscar funcionário pelo ID do funcionário
    Optional<Funcionario> findByIdFuncionario(Integer idFuncionario);
    
    // Buscar funcionário pelo ID do usuário
    Optional<Funcionario> findByUsuario_IdUsuario(Integer idUsuario);

}

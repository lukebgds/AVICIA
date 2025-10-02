package com.avicia.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avicia.api.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

    // Busca por CPF
    Optional<Usuario> findByCpf(String cpf);

    // Busca por Nome
    Optional<Usuario> findByNome(String nome);

    // Verifica se o usuário está ativo
    Optional<Usuario> findByCpfAndAtivoTrue(String cpf);

}

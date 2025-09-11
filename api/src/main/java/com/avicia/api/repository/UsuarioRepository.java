package com.avicia.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avicia.api.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

    // Busca Por CPF
    Optional<Usuario> findByCpf(String cpf);

    // Verifica se o usuário está ativo
    Optional<Usuario> findByCpfAndAtivoTrue(String email);

}

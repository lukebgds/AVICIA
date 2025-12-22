package com.avicia.api.features.usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

    // Busca por CPF
    Optional<Usuario> findByCpf(String cpf);

    // Busca por Nome
    Optional<Usuario> findByNome(String nome);

    // Busca por id
    Optional<Usuario> findByIdUsuario(Integer idUsuario);

    // Verifica se o usuário está ativo
    Optional<Usuario> findByCpfAndAtivoTrue(String cpf);

}

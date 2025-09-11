package com.avicia.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avicia.api.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{

    Optional<Role> findByNome(String nome);

    Optional<Role> findByIdRole(Integer idRole);

}

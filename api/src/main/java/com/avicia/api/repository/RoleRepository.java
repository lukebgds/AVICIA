package com.avicia.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avicia.api.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

    Optional<Role> findByNome(String nome);

    Optional<Role> findByIdRole(Integer idRole);

}

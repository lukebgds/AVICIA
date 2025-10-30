package com.avicia.api.features.sistema.role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

    Optional<Role> findByNome(String nome);

    Optional<Role> findByIdRole(Integer idRole);

}

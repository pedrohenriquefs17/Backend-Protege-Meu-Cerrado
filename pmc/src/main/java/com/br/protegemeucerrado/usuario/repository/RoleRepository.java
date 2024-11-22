package com.br.protegemeucerrado.usuario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.protegemeucerrado.usuario.enums.Role;
import com.br.protegemeucerrado.usuario.model.ModelRole;

@Repository
public interface RoleRepository extends JpaRepository<ModelRole, Long> {

    Optional<ModelRole> findByName(Role name);
}

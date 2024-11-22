package com.br.protegemeucerrado.usuario.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.protegemeucerrado.usuario.enums.Role;
import com.br.protegemeucerrado.usuario.model.ModelRole;
import com.br.protegemeucerrado.usuario.repository.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public ModelRole getOrCreateRole(Role roleName) {
        return roleRepository.findByName(roleName)
                .orElseGet(() -> roleRepository.save(new ModelRole(null, roleName)));
    }

}

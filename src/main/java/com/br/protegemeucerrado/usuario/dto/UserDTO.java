package com.br.protegemeucerrado.usuario.dto;

import java.util.List;

import com.br.protegemeucerrado.usuario.enums.Role;

public record UserDTO(Long id, String email, List<Role> roles) {

}

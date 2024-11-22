package com.br.protegemeucerrado.usuario.dto;

import java.time.LocalDate;

import com.br.protegemeucerrado.usuario.enums.Role;

public record CreateUserDTO(
                String email,
                String nome,
                String senha,
                String cpf,
                LocalDate dataNascimento,
                String telefone,
                Role role) {

}

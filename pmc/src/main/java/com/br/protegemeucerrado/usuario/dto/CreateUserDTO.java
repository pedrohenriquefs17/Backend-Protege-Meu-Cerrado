package com.br.protegemeucerrado.usuario.dto;

import java.time.LocalDate;

import com.br.protegemeucerrado.usuario.enums.Role;

public record CreateUserDTO(
                String email,
                String nome,
                String senha,
                int operador,
                String cpf,
                LocalDate dataNascimento,
                String telefone,
                String telefoneEmergencia,
                String tipoSanguineo,
                String ocupacao,
                String statusOperador,
                Role role) {

}

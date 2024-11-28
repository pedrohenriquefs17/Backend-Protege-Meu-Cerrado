package com.br.protegemeucerrado.usuario.model;

import com.br.protegemeucerrado.usuario.dto.JwtTokenDTO;

public class ReturnModel {
    private JwtTokenDTO tokenDTO;
    private String idUsuario;

    public JwtTokenDTO getTokenDTO() {
        return this.tokenDTO;
    }

    public void setTokenDTO(JwtTokenDTO tokenDTO) {
        this.tokenDTO = tokenDTO;
    }

    public String getIdUsuario() {
        return this.idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

}

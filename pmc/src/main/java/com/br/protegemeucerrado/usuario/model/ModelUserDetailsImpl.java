package com.br.protegemeucerrado.usuario.model;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;

@Getter
public class ModelUserDetailsImpl implements UserDetails {

    private final ModelUser modelUser;

    public ModelUserDetailsImpl(ModelUser modelUser) {
        this.modelUser = modelUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return modelUser.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name())) // Corrigido para usar name()
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return modelUser.getSenha();
    }

    @Override
    public String getUsername() {
        return modelUser.getEmail();
    }

    public ModelUser getModelUser() {
        return this.modelUser;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}

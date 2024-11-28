package com.br.protegemeucerrado.usuario.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.br.protegemeucerrado.usuario.model.ModelUser;
import com.br.protegemeucerrado.usuario.model.ModelUserDetailsImpl;
import com.br.protegemeucerrado.usuario.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Verifica se o email tem um formato válido (opcional)
        if (!email.contains("@")) {
            logger.warn("Tentativa de login com email inválido: {}", email);
            throw new UsernameNotFoundException("Formato de email inválido: " + email);
        }

        // Procura o usuário pelo email
        ModelUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.warn("Usuário não encontrado com o email: {}", email);
                    return new UsernameNotFoundException("User not found with email: " + email);
                });

        // Retorna uma instância de ModelUserDetailsImpl com as informações do usuário
        // encontrado
        return new ModelUserDetailsImpl(user);
    }

    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        // Procura o usuário pelo ID
        ModelUser user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        // Retorna uma instância de ModelUserDetailsImpl com as informações do usuário
        // encontrado
        return new ModelUserDetailsImpl(user);
    }
}

package com.br.protegemeucerrado.usuario.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.br.protegemeucerrado.usuario.service.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserAuthenticationFilter extends GenericFilterBean {

    private static final Logger authLogger = LoggerFactory.getLogger(UserAuthenticationFilter.class); // Renomeado

    private final JwtTokenService jwtTokenService;
    private final UserDetailsServiceImpl userDetailsService;

    public UserAuthenticationFilter(JwtTokenService jwtTokenService, UserDetailsServiceImpl userDetailsService) {
        this.jwtTokenService = jwtTokenService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String header = httpRequest.getHeader("Authorization");
        authLogger.debug("Header Authorization: " + header); // Alterado

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7); // Remove "Bearer " prefix

            if (token.isEmpty()) {
                authLogger.warn("Token JWT está vazio"); // Alterado
                chain.doFilter(request, response);
                return;
            }

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                try {
                    String username = jwtTokenService.pegarToken(token);

                    if (username != null) {
                        UserDetails userDetails = userDetailsService.loadUserById(Long.valueOf(username));

                        if (userDetails != null) {
                            var authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                                    userDetails.getAuthorities());
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        } else {
                            authLogger.warn("Detalhes do usuário não encontrados para: " + username); // Alterado
                        }
                    }
                } catch (JWTVerificationException e) {
                    authLogger.error("Erro de verificação de token: " + e.getMessage()); // Alterado
                    httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                } catch (UsernameNotFoundException e) {
                    authLogger.error("Erro inesperado: " + e.getMessage()); // Alterado
                    httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    return;
                }
            }
        } else {
            authLogger.warn("Token não encontrado ou inválido no cabeçalho"); // Alterado
        }

        chain.doFilter(request, response);
    }
}

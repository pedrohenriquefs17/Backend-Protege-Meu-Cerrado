package com.br.protegemeucerrado.usuario.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.protegemeucerrado.usuario.dto.CreateUserDTO;
import com.br.protegemeucerrado.usuario.dto.JwtTokenDTO;
import com.br.protegemeucerrado.usuario.dto.LoginUserDTO;
import com.br.protegemeucerrado.usuario.model.ModelRole;
import com.br.protegemeucerrado.usuario.model.ModelUser;
import com.br.protegemeucerrado.usuario.model.ModelUserDetailsImpl;
import com.br.protegemeucerrado.usuario.repository.UserRepository;
import com.br.protegemeucerrado.usuario.security.JwtTokenService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager,
            JwtTokenService jwtTokenService, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    public void salvarUsuario(CreateUserDTO createUserDto) {
        // Obtemos ou criamos a role
        ModelRole role = roleService.getOrCreateRole(createUserDto.role());
        // Cria o novo usuário com a role associada
        ModelUser newUser = ModelUser.builder()
                .email(createUserDto.email())
                .senha(passwordEncoder.encode(createUserDto.senha()))
                .operador(createUserDto.operador())
                .nome(createUserDto.nome())
                .cpf(createUserDto.cpf())
                .dataNascimento(createUserDto.dataNascimento())
                .telefone(createUserDto.telefone())
                .telefoneEmergencia(createUserDto.telefoneEmergencia())
                .tipoSanguineo(createUserDto.tipoSanguineo())
                .ocupacao(createUserDto.ocupacao())
                .statusOperador(createUserDto.statusOperador())
                .roles(List.of(role)) // Associa a role ao usuário
                .build();
        userRepository.save(newUser);
    }

    public JwtTokenDTO autenticarUsuario(LoginUserDTO loginUserDto) {
        // Cria o token de autenticação
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginUserDto.email(), loginUserDto.senha());
        // Autentica o usuário
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        ModelUserDetailsImpl modelUserDetails = (ModelUserDetailsImpl) authentication.getPrincipal();
        // Gera o JWT token
        return new JwtTokenDTO(jwtTokenService.generateToken(modelUserDetails));
    }

    public ResponseEntity<String> atualizarUsuario(Long id, CreateUserDTO updateUserDTO) {
        try {

            ModelUser userAtual = userRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

            if (!userAtual.getEmail().equals(updateUserDTO.email())
                    && userRepository.findByEmail(updateUserDTO.email()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("E-mail ja cadastrado");
            }
            if (updateUserDTO.operador() != 0 && userRepository.findByOperador(updateUserDTO.operador()).isPresent()
                    && !userAtual.getOperador().equals(updateUserDTO.operador())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Número do operador já existe");
            }
            if (updateUserDTO.operador() < 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Número do operador inválido");
            }

            if (!updateUserDTO.senha().isEmpty()
                    && !passwordEncoder.matches(updateUserDTO.senha(), userAtual.getSenha())) {
                String novaSenhaCriptografada = passwordEncoder.encode(updateUserDTO.senha());
                userAtual.setSenha(novaSenhaCriptografada);
            }

            userAtual.setEmail(updateUserDTO.email());
            userAtual.setOperador(updateUserDTO.operador());
            userAtual.setNome(updateUserDTO.nome());
            userAtual.setCpf(updateUserDTO.cpf());
            userAtual.setDataNascimento(updateUserDTO.dataNascimento());
            userAtual.setTelefone(updateUserDTO.telefone());
            userAtual.setTelefoneEmergencia(updateUserDTO.telefoneEmergencia());
            userAtual.setTipoSanguineo(updateUserDTO.tipoSanguineo());
            userAtual.setOcupacao(updateUserDTO.ocupacao());
            userAtual.setStatusOperador(updateUserDTO.statusOperador());
            userAtual.getRoles().clear();
            userAtual.getRoles().add(roleService.getOrCreateRole(updateUserDTO.role()));
            userRepository.save(userAtual);
            return ResponseEntity.status(200).body("Operador atualizado");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    public boolean removerUsuario(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<ModelUser> listarLogins() {// retirar depois
        return userRepository.findAllExcludingStatusOperador("ADMIN");
    }
}

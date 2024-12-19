package com.br.protegemeucerrado.usuario.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.protegemeucerrado.usuario.dto.CreateUserDTO;
import com.br.protegemeucerrado.usuario.dto.LoginUserDTO;
import com.br.protegemeucerrado.usuario.errorStatus.ErrorResponse;
import com.br.protegemeucerrado.usuario.model.ModelUser;
import com.br.protegemeucerrado.usuario.model.ReturnModel;
import com.br.protegemeucerrado.usuario.repository.UserRepository;
import com.br.protegemeucerrado.usuario.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/pmc")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @SuppressWarnings("FieldMayBeFinal")
    private ReturnModel returnModel = new ReturnModel();

    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody LoginUserDTO loginUserDto) {
        try {
            Optional<ModelUser> userOptional = userRepository.findByEmail(loginUserDto.email());
            if (userOptional.get().isValidado()) {
                returnModel.setTokenDTO(userService.autenticarUsuario(loginUserDto));
                returnModel.setIdUsuario(String.valueOf(userOptional.get().getId()));
                return new ResponseEntity<>(returnModel, HttpStatus.OK);
            }
            if (!userOptional.get().isValidado())
                return new ResponseEntity<>(new ErrorResponse("Conta não autenticado"), HttpStatus.UNAUTHORIZED);

        } catch (AuthenticationException e) {
            // Autenticação falhou, retornar status 401
            return new ResponseEntity<>(new ErrorResponse("Credenciais inválidas"), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            // Outros erros
            return new ResponseEntity<>(new ErrorResponse("Erro interno do servidor"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastrarUsuario(@RequestBody CreateUserDTO createUserDTO) {
        try {

            if (userRepository.findByEmail(createUserDTO.email()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Já existe uma pessoa usando esse email!");
            }
            if (userRepository.findByCpf(createUserDTO.cpf()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF já cadastrado");
            }
            if (createUserDTO.senha().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Coloque uma senha");
            }

            userService.salvarUsuario(createUserDTO);
            return ResponseEntity.status(201).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    @GetMapping("/listar/usuario") // lista usuario atual
    public ResponseEntity<?> listarUsuarioAtual(@RequestParam("usuario") String id) {
        Long IDD = Long.valueOf(id);

        if (userRepository.findById(IDD).isPresent()) {
            ModelUser user = userRepository.findById(IDD)
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
            user.setSenha("");
            return ResponseEntity.status(200).body(user);
        } else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não encontrado");

    }

    @PutMapping("/atualizar/usuario/{id}")
    public ResponseEntity<String> atualizarUsuario(@PathVariable Long id, @RequestBody CreateUserDTO createUserDTO) {
        return userService.atualizarUsuario(id, createUserDTO);
    }

    // funcionalidade sistema
    @Transactional
    @GetMapping("/validarmail")
    public ResponseEntity<Void> validarEmail(@RequestParam("email") String email,
            @RequestParam("codigo") int codigo) {
        Optional<ModelUser> verifUSer = userRepository.findByEmailAndCodigoVerificador(email, codigo);

        if (verifUSer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            userRepository.validarEmail(email, codigo);
            String redirectUrl = "https://www.youtube.com/shorts/7fTHD07Q9Pw";
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(redirectUrl))
                    .build();
        }
    }

    // funções admin
    @DeleteMapping("/admin/remover/{id}")
    public ResponseEntity<String> removerUsuario(@PathVariable Long id) {
        if (userService.removerUsuario(id)) {
            return ResponseEntity.status(200).body(null);
        } else {
            return ResponseEntity.status(500).body("Usuário não encontrado");
        }
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/admin/listar/usuarios") // lista todos usuarios
    public ResponseEntity<List<ModelUser>> listarTodosUsuarios() {
        return ResponseEntity.status(200).body(userService.listarLogins());
    }

    @GetMapping("/admin/quantidadeUsuarios")
    public int quantidadeUsuario() {
        return userRepository.countUsers();
    }
}

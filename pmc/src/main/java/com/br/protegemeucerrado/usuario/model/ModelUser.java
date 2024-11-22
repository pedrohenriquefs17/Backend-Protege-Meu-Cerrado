package com.br.protegemeucerrado.usuario.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "user")
@Entity
public class ModelUser implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true, columnDefinition = "VARCHAR(255)")
    private String email;
    @Column(name = "senha", nullable = false, columnDefinition = "VARCHAR(255)")
    private String senha;
    @Column(name = "nome", nullable = false, columnDefinition = "VARCHAR(255)")
    private String nome;
    @Column(name = "cpf", nullable = false, unique = true, columnDefinition = "VARCHAR(15)")
    private String cpf;
    @Column(name = "data_nascimento", columnDefinition = "DATE")
    private LocalDate dataNascimento;
    @Column(name = "telefone", columnDefinition = "VARCHAR(15)")
    private String telefone;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "users_roles", // Nome da tabela de junção
            joinColumns = @JoinColumn(name = "user_id"), // Chave estrangeira para a entidade 'user'
            inverseJoinColumns = @JoinColumn(name = "role_id") // Chave estrangeira para a entidade 'role'
    )
    private List<ModelRole> roles;
}

package com.br.protegemeucerrado.usuario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.protegemeucerrado.usuario.model.ModelUser;

@Repository
public interface UserRepository extends JpaRepository<ModelUser, Long> {

    Optional<ModelUser> findByEmail(String email);

    Optional<ModelUser> findByCpf(String cpf);

    Optional<ModelUser> findByEmailAndCodigoVerificador(String email, int codigoVerificador);

    @Modifying
    @Query("UPDATE ModelUser u SET u.validado = true WHERE u.email = :email AND u.codigoVerificador = :codigo")
    void validarEmail(@Param("email") String email, @Param("codigo") int codigo);

    @Query("SELECT COUNT(u) FROM ModelUser u")
    int countUsers();
}

package com.br.protegemeucerrado.usuario.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.protegemeucerrado.usuario.model.ModelUser;

@Repository
public interface UserRepository extends JpaRepository<ModelUser, Long> {

    Optional<ModelUser> findByEmail(String email);

    Optional<ModelUser> findByOperador(int operador);

    Optional<ModelUser> findByCpf(String cpf);

    List<ModelUser> findByStatusOperador(String statusOperador);

    @Query("SELECT u FROM ModelUser u WHERE u.statusOperador <> :statusOperador")
    List<ModelUser> findAllExcludingStatusOperador(@Param("statusOperador") String statusOperador);
}

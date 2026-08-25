package com.github.carlossfelipe.api_transacao.repository;

import com.github.carlossfelipe.api_transacao.entity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ContaRepository extends JpaRepository<Conta, UUID> {
    Optional<Conta> findByChaveTransacao(String chaveTransacao);
}

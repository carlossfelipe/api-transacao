package com.github.carlossfelipe.api_transacao.repository;

import com.github.carlossfelipe.api_transacao.entity.Transacao;
import com.github.carlossfelipe.api_transacao.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Optional<Usuario> findByNome(String nome);

}

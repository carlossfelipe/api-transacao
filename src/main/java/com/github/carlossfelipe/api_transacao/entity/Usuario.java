package com.github.carlossfelipe.api_transacao.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;

    @OneToOne(mappedBy = "usuario")
    private Conta conta;


    @OneToMany(mappedBy = "remetente")
    private List<Transacao> transacoesEnviadas = new ArrayList<>();

    @OneToMany(mappedBy = "destinatario")
    private List<Transacao> transacoesRecebidas = new ArrayList<>();

}

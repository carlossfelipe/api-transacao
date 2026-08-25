package com.github.carlossfelipe.api_transacao.controller;


import com.github.carlossfelipe.api_transacao.dto.TransacaoEstatisticaResponseDTO;
import com.github.carlossfelipe.api_transacao.dto.TransacaoRequestDTO;
import com.github.carlossfelipe.api_transacao.dto.TransacaoResponseDTO;
import com.github.carlossfelipe.api_transacao.service.TransacaoService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/transacao")
@AllArgsConstructor
public class TransacaoController {
    private final TransacaoService service;

    @GetMapping("/media")
    public ResponseEntity<TransacaoEstatisticaResponseDTO> verMedia(){
        return ResponseEntity.ok(service.calcularEstatisticas());
    }

    @PostMapping
    public ResponseEntity<TransacaoResponseDTO> fazerTransacao(@RequestBody TransacaoRequestDTO transacao){
        return ResponseEntity.ok(service.transferir(transacao));
    }

    @GetMapping
    public ResponseEntity<List<TransacaoResponseDTO>> listarTudo(){
        return ResponseEntity.ok(service.listar());
    }

}

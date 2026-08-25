package com.github.carlossfelipe.api_transacao.exception.handler;

import com.github.carlossfelipe.api_transacao.dto.ErroResponse;
import com.github.carlossfelipe.api_transacao.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ContaNaoEncontradaException.class)
    public ResponseEntity<ErroResponse> contaNaoEncontrada(
            ContaNaoEncontradaException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErroResponse(
                        404,
                        "Conta não encontrada",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<ErroResponse> usuarioNaoEncontrado(
            UsuarioNaoEncontradoException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErroResponse(
                        404,
                        "Usuário não encontrado",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<ErroResponse> saldoInsuficiente(
            SaldoInsuficienteException ex) {

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErroResponse(
                        422,
                        "Saldo insuficiente",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(TransacaoInvalidaException.class)
    public ResponseEntity<ErroResponse> transacaoInvalida(
            TransacaoInvalidaException ex) {

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErroResponse(
                        422,
                        "Transação inválida",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(NenhumaTransacaoException.class)
    public ResponseEntity<ErroResponse> nenhumaTransacao(
            NenhumaTransacaoException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErroResponse(
                        404,
                        "Nenhuma transação encontrada",
                        ex.getMessage()
                ));
    }
}
package com.github.carlossfelipe.api_transacao.dto;

public record ErroResponse(
        int status,
        String error,
        String message
) {
}

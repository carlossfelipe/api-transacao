package com.github.carlossfelipe.api_transacao.dto;

import java.math.BigDecimal;

public record TransacaoEstatisticaResponseDTO(BigDecimal max,
                                              BigDecimal min,
                                              BigDecimal avg) {
}

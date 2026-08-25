package com.github.carlossfelipe.api_transacao.service;

import com.github.carlossfelipe.api_transacao.dto.TransacaoEstatisticaResponseDTO;
import com.github.carlossfelipe.api_transacao.dto.TransacaoRequestDTO;
import com.github.carlossfelipe.api_transacao.dto.TransacaoResponseDTO;
import com.github.carlossfelipe.api_transacao.entity.Conta;
import com.github.carlossfelipe.api_transacao.entity.Transacao;
import com.github.carlossfelipe.api_transacao.entity.Usuario;
import com.github.carlossfelipe.api_transacao.exception.*;
import com.github.carlossfelipe.api_transacao.repository.ContaRepository;
import com.github.carlossfelipe.api_transacao.repository.TransacaoRepository;
import com.github.carlossfelipe.api_transacao.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.List;


@Service
@AllArgsConstructor
public class TransacaoService {
    private TransacaoRepository repository;
    private ContaRepository contaRepository;
    private UsuarioRepository usuarioRepository;

    @Transactional
    public TransacaoResponseDTO transferir(TransacaoRequestDTO transacao) {

        if (transacao.valor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new TransacaoInvalidaException("O valor deve ser maior que zero");
        }

        Conta contaDestinatario = contaRepository
                .findByChaveTransacao(transacao.chaveTransacao())
                .orElseThrow(() ->
                        new ContaNaoEncontradaException("Conta não encontrada"));

        Usuario remetente = usuarioRepository
                .findByNome(transacao.remetente())
                .orElseThrow(() ->
                        new UsuarioNaoEncontradoException("Remetente não encontrado"));

        Conta contaRemetente = remetente.getConta();

        if (contaRemetente == null)
            throw new ContaNaoEncontradaException("Remetente não possui uma conta");


        if (contaRemetente.getId().equals(contaDestinatario.getId()))
            throw new TransacaoInvalidaException("Não é possível transferir para a própria conta");


        if (contaRemetente.getSaldo().compareTo(transacao.valor()) < 0)
            throw new SaldoInsuficienteException("Saldo insuficiente");


        contaRemetente.setSaldo(
                contaRemetente.getSaldo().subtract(transacao.valor())
        );

        contaDestinatario.setSaldo(
                contaDestinatario.getSaldo().add(transacao.valor())
        );

        Transacao novaTransacao = new Transacao();
        novaTransacao.setData(OffsetDateTime.now());
        novaTransacao.setValor(transacao.valor());
        novaTransacao.setRemetente(contaRemetente.getUsuario());
        novaTransacao.setDestinatario(contaDestinatario.getUsuario());

        Transacao save = repository.save(novaTransacao);

        return new TransacaoResponseDTO(
                save.getRemetente().getNome(),
                save.getValor(),
                save.getId(),
                save.getData(),
                save.getDestinatario().getNome()
        );

    }
    public TransacaoEstatisticaResponseDTO calcularEstatisticas() {

        OffsetDateTime agora = OffsetDateTime.now();
        OffsetDateTime umaHoraAtras = agora.minusHours(1);

        List<Transacao> amostra = repository.findAll().stream()
                .filter(t -> !t.getData().isBefore(umaHoraAtras))
                .filter(t -> !t.getData().isAfter(agora))
                .toList();

        if (amostra.isEmpty())
            throw new NenhumaTransacaoException("Nenhuma transação feita na última 1h");


        BigDecimal maior = amostra.stream()
                .map(Transacao::getValor)
                .max(BigDecimal::compareTo)
                .orElseThrow();

        BigDecimal menor = amostra.stream()
                .map(Transacao::getValor)
                .min(BigDecimal::compareTo)
                .orElseThrow();

        BigDecimal media = amostra.stream()
                .map(Transacao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(
                        BigDecimal.valueOf(amostra.size()),
                        2,
                        RoundingMode.HALF_UP
                );

        return new TransacaoEstatisticaResponseDTO(
                maior,
                menor,
                media
        );
    }
    public List<TransacaoResponseDTO> listar (){
        return repository.findAll().stream()
                .map(transacao -> new TransacaoResponseDTO(
                        transacao.getRemetente().getNome(),
                        transacao.getValor(),
                        transacao.getId(),
                        transacao.getData(),
                        transacao.getDestinatario().getNome()
                ))
                .toList();

    }
}

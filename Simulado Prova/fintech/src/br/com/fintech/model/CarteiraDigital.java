package br.com.fintech.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import br.com.fintech.exception.LimiteTransacoesExcedidoException;

public class CarteiraDigital {

    private final String codigoCarteira;
    private final int capacidadeMaxima;
    private final List<Transacao> transacoes;

    public CarteiraDigital(String codigoCarteira, int capacidadeMaxima) {

        if (codigoCarteira == null || codigoCarteira.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Codigo da carteira nao pode ser nulo ou vazio."
            );
        }

        if (capacidadeMaxima <= 0) {
            throw new IllegalArgumentException(
                    "Capacidade maxima deve ser estritamente positiva."
            );
        }

        this.codigoCarteira = codigoCarteira;
        this.capacidadeMaxima = capacidadeMaxima;
        this.transacoes = new ArrayList<>();
    }

    public String getCodigoCarteira() {
        return codigoCarteira;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public List<Transacao> getTransacoes() {
        return Collections.unmodifiableList(transacoes);
    }

    public void adicionarTransacao(Transacao transacao)
            throws LimiteTransacoesExcedidoException {

        Objects.requireNonNull(
                transacao,
                "A transacao nao pode ser nula."
        );

        if (this.transacoes.size() >= this.capacidadeMaxima) {

            throw new LimiteTransacoesExcedidoException(
                    String.format(
                            "Carteira '%s' atingiu o limite maximo de %d transacoes.",
                            this.codigoCarteira,
                            this.capacidadeMaxima
                    )
            );
        }

        this.transacoes.add(transacao);
    }

    public double calcularTarifaTotal() {

        return this.transacoes.stream()
                .mapToDouble(Transacao::getTarifa)
                .sum();
    }

    public long contarPorTipo(String tipo) {

        return this.transacoes.stream()
                .filter(t -> tipo != null && tipo.equalsIgnoreCase(t.getTipo()))
                .count();
    }

    public double calcularTarifaPorTipo(String tipo) {

        return this.transacoes.stream()
                .filter(t -> tipo != null && tipo.equalsIgnoreCase(t.getTipo()))
                .mapToDouble(Transacao::getTarifa)
                .sum();
    }
}
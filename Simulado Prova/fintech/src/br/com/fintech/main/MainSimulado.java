package br.com.fintech.main;

import java.util.HashSet;
import java.util.Set;

import br.com.fintech.exception.LimiteTransacoesExcedidoException;
import br.com.fintech.model.CarteiraDigital;
import br.com.fintech.model.GatewayPagamentos;
import br.com.fintech.model.Transacao;

public class MainSimulado {
    public static void main(String[] args) {

        Transacao t1 = new Transacao("TX-101-BR", "PIX", 400.00, 2.00);

        Transacao t2 = new Transacao("TX-102-BR", "CARTAO", 1500.00, 45.00);

        Transacao t3 = new Transacao("TX-103-BR", "PIX", 600.00, 3.00);

        Transacao t4 = new Transacao("TX-104-BR", "BOLETO", 200.00, 2.50);

        System.out.println(t1);

        System.out.println(t4);

        Set<Transacao> auditoria = new HashSet<>();

        auditoria.add(t1);

        auditoria.add(new Transacao("TX-101-BR", "PIX", 990.00, 4.95));

        auditoria.add(t2);

        System.out.println("Tamanho da auditoria (HashSet): " + auditoria.size());

        try {

            new Transacao("", "PIX", 10.0, 0.1);

        } catch (IllegalArgumentException e) {

            System.out.println("Construtor validado: " + e.getMessage());
        }

        CarteiraDigital carteira = new CarteiraDigital("WALLET-01", 3);

        try {

            carteira.adicionarTransacao(t1);

            System.out.println("Transacao " + t1.getIdTransacao() + " adicionada com sucesso.");

            carteira.adicionarTransacao(t2);

            System.out.println("Transacao " + t2.getIdTransacao() + " adicionada com sucesso.");

            carteira.adicionarTransacao(t3);

            System.out.println("Transacao " + t3.getIdTransacao() + " adicionada com sucesso.");

        } catch (LimiteTransacoesExcedidoException e) {

            System.out.println("Falha inesperada: " + e.getMessage());
        }

        // P05: Disparo de Exceção Checada

        try {

            carteira.adicionarTransacao(t4);

        } catch (LimiteTransacoesExcedidoException e) {

            System.out.println("Excecao capturada: " + e.getMessage());
        }

        // P06: Pipelines Declarativos (Streams API)

        System.out.printf(
                "Tarifa total da carteira: R$ %.2f%n",
                carteira.calcularTarifaTotal()
        );

        System.out.println(
                "Transacoes PIX: " + carteira.contarPorTipo("PIX")
        );

        // P07: Stream Filtrada com Redução

        System.out.printf(
                "Tarifa apurada para PIX: R$ %.2f%n",
                carteira.calcularTarifaPorTipo("PIX")
        );

        // P08: Gerenciamento Associativo no Gateway

        GatewayPagamentos gateway = new GatewayPagamentos();

        gateway.cadastrarCarteira(carteira);

        System.out.println(
                "Carteira '" + carteira.getCodigoCarteira()
                + "' cadastrada no gateway."
        );

        CarteiraDigital recuperada = gateway.buscarCarteira("WALLET-01");

        System.out.println(
                "Carteira recuperada do gateway: "
                + recuperada.getCodigoCarteira()
        );
    }
}
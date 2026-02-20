package br.com.esports.arenas.service;

import org.springframework.stereotype.Component;

/**
 * Classe responsável pela lógica de negócio do cálculo de pontos.
 * Separar esta lógica atende aos requisitos de responsabilidades separadas da Etapa 9.
 * * @author Micro
 */
@Component
public class CalculadoraPontuacao {

    // Constantes para facilitar a manutenção futura
    private static final int PONTOS_VITORIA = 3;
    private static final int PONTOS_EMPATE = 1;
    private static final int PONTOS_DERROTA = 0;

    /**
     * Calcula a pontuação total de um time com base nos resultados.
     * * @param vitorias Quantidade de vitórias
     * @param empates Quantidade de empates
     * @param derrotas Quantidade de derrotas
     * @return Pontuação total calculada
     * @throws IllegalArgumentException se algum valor for negativo
     */
    public int calcularPontuacao(int vitorias, int empates, int derrotas) {
        // Validação de segurança (Etapa 9)
        if (vitorias < 0 || empates < 0 || derrotas < 0) {
            throw new IllegalArgumentException("Os valores de vitórias, empates ou derrotas não podem ser negativos.");
        }

        // Lógica de cálculo: (V * 3) + (E * 1)
        return (vitorias * PONTOS_VITORIA) + (empates * PONTOS_EMPATE) + (derrotas * PONTOS_DERROTA);
    }
}
package br.com.ramos.unifor.caixeiro;

import java.util.Arrays;

public class CaixeiroViajante {
    private int[][] dist; // Matriz de distâncias
    private int[][] memo; // Tabela de memoização
    private int n;        // Número de cidades
    private int VISITED_ALL;

    public CaixeiroViajante(int[][] dist) {
        this.dist = dist;
        this.n = dist.length;
        this.VISITED_ALL = (1 << n) - 1;
        this.memo = new int[n][1 << n];

        // Inicializa a tabela de memoização com -1
        for (int i = 0; i < n; i++) {
            Arrays.fill(memo[i], -1);
        }
    }

    public int findMinCost() {
        // Começa da cidade 0, com máscara 0001 (apenas cidade 0 visitada)
        return tsp(0, 1);
    }

    private int tsp(int pos, int mask) {
        // Caso base: todas as cidades foram visitadas
        if (mask == VISITED_ALL) {
            return dist[pos][0]; // Volta para a cidade inicial
        }

        // Verifica se já calculamos este estado
        if (memo[pos][mask] != -1) {
            return memo[pos][mask];
        }

        int minCost = Integer.MAX_VALUE;

        // Visita todas as cidades não visitadas
        for (int city = 0; city < n; city++) {
            if ((mask & (1 << city)) == 0) { // Se a cidade não foi visitada
                int newCost = dist[pos][city] + tsp(city, mask | (1 << city));
                minCost = Math.min(minCost, newCost);
            }
        }

        // Armazena o resultado na tabela de memoização
        return memo[pos][mask] = minCost;
    }

    public static void main(String[] args) {
        int[][] dist = {
                {0, 10, 15, 20},
                {10, 0, 35, 25},
                {15, 35, 0, 30},
                {20, 25, 30, 0}
        };

        CaixeiroViajante tsp = new CaixeiroViajante(dist);
        System.out.println("Custo mínimo do TSP: " + tsp.findMinCost());
    }
}
package br.com.ramos.unifor.mochila;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mochila {
    private int[] values;
    private int[] weights;
    private int capacity;
    private int[][] dp;

    public Mochila(int[] values, int[] weights, int capacity) {
        this.values = values;
        this.weights = weights;
        this.capacity = capacity;
        this.dp = new int[values.length + 1][capacity + 1];
    }

    public int solve() {
        int n = values.length;

        // Construindo a tabela dp de baixo para cima
        for (int i = 0; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                if (i == 0 || w == 0) {
                    dp[i][w] = 0;
                } else if (weights[i-1] <= w) {
                    dp[i][w] = Math.max(
                            values[i-1] + dp[i-1][w - weights[i-1]],
                            dp[i-1][w]
                    );
                } else {
                    dp[i][w] = dp[i-1][w];
                }
            }
        }

        return dp[n][capacity];
    }

    public List<Integer> getSelectedItems() {
        List<Integer> selected = new ArrayList<>();
        int w = capacity;

        for (int i = values.length; i > 0; i--) {
            if (dp[i][w] != dp[i-1][w]) {
                selected.add(i-1); // Indice do item selecionado
                w -= weights[i-1];
            }
        }

        Collections.reverse(selected); // Para manter a ordem original
        return selected;
    }

    public static void main(String[] args) {
        int[] values = {60, 100, 120};
        int[] weights = {10, 20, 30};
        int capacity = 50;

        Mochila knapsack = new Mochila(values, weights, capacity);
        System.out.println("Valor máximo: " + knapsack.solve());
        System.out.println("Itens selecionados: " + knapsack.getSelectedItems());
    }
}
package br.com.ramos.unifor.multiplicacao;

public class MultiplcacaoCadeiaMatrix {
    private int[] p; // Dimensões das matrizes
    private int[][] m; // Tabela de custos mínimos
    private int[][] s; // Tabela de pontos de divisão

    public MultiplcacaoCadeiaMatrix(int[] dimensions) {
        this.p = dimensions;
        int n = p.length - 1; // Número de matrizes
        this.m = new int[n][n];
        this.s = new int[n][n];
    }

    public int solve() {
        int n = p.length - 1;

        // Cadeias de tamanho 1 têm custo 0
        for (int i = 0; i < n; i++) {
            m[i][i] = 0;
        }

        // l é o comprimento da cadeia
        for (int l = 2; l <= n; l++) {
            for (int i = 0; i < n - l + 1; i++) {
                int j = i + l - 1;
                m[i][j] = Integer.MAX_VALUE;

                // Testa todas as possíveis divisões
                for (int k = i; k < j; k++) {
                    int cost = m[i][k] + m[k+1][j] + p[i]*p[k+1]*p[j+1];
                    if (cost < m[i][j]) {
                        m[i][j] = cost;
                        s[i][j] = k;
                    }
                }
            }
        }

        return m[0][n-1];
    }

    public String getParenthesization() {
        return printOptimalParenthesization(0, p.length - 2);
    }

    private String printOptimalParenthesization(int i, int j) {
        if (i == j) {
            return "A" + (i + 1);
        } else {
            return "(" + printOptimalParenthesization(i, s[i][j]) + " × " +
                    printOptimalParenthesization(s[i][j] + 1, j) + ")";
        }
    }

    public static void main(String[] args) {
        // Exemplo: 4 matrizes com dimensões 5×4, 4×6, 6×2, 2×7
        int[] dimensions = {5, 4, 6, 2, 7};
        MultiplcacaoCadeiaMatrix mcm = new MultiplcacaoCadeiaMatrix(dimensions);

        System.out.println("Número mínimo de multiplicações: " + mcm.solve());
        System.out.println("Parentização ótima: " + mcm.getParenthesization());
    }
}
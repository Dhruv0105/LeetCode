class Solution {
    static final long MOD = 1_000_000_007L;
    public int numberOfSets(int n, int k) {
        int N = n + k - 1;
        int R = 2 * k;
        long[][] comb = new long[N + 1][R + 1];
        for (int i = 0; i <= N; i++) {
            comb[i][0] = 1;
            for (int j = 1; j <= Math.min(i, R); j++) {
                if (j == i)
                    comb[i][j] = 1;
                else
                    comb[i][j] = (comb[i - 1][j - 1] + comb[i - 1][j]) % MOD;
            }
        }

        return (int) comb[N][R];
    }
}
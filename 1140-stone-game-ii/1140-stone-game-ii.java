class Solution {
    int[][] memo;
    int[] suffix;
    int n;

    public int stoneGameII(int[] piles) {
        n = piles.length;
        suffix = new int[n + 1];
        for (int i = n - 1; i >= 0; i--) {
            suffix[i] = suffix[i + 1] + piles[i];
        }

        memo = new int[n][n + 1];
        return solve(0, 1);
    }

    private int solve(int i, int M) {
        if (i >= n) {
            return 0;
        }

        if (memo[i][M] != 0) {
            return memo[i][M];
        }

        if (i + 2 * M >= n) {
            return memo[i][M] = suffix[i];
        }

        int best = 0;

        for (int X = 1; X <= 2 * M; X++) {
            int opponent = solve(i + X, Math.max(M, X));
            best = Math.max(best, suffix[i] - opponent);
        }

        return memo[i][M] = best;
    }
}
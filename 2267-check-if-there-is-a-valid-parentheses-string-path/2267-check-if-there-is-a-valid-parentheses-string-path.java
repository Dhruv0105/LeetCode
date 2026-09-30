import java.util.*;

class Solution {
    public boolean hasValidPath(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        if ((m + n - 1) % 2 != 0) {
            return false;
        }
        Set<Integer>[][] dp = new HashSet[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = new HashSet<>();
            }
        }

        dp[0][0].add(grid[0][0] == '(' ? 1 : -1);

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                dp[i][j].removeIf(x -> x < 0);

                for (int balance : new HashSet<>(dp[i][j])) {

                    if (i + 1 < m) {
                        int next = balance + (grid[i + 1][j] == '(' ? 1 : -1);
                        if (next >= 0) {
                            dp[i + 1][j].add(next);
                        }
                    }

                    if (j + 1 < n) {
                        int next = balance + (grid[i][j + 1] == '(' ? 1 : -1);
                        if (next >= 0) {
                            dp[i][j + 1].add(next);
                        }
                    }
                }
            }
        }

        return dp[m - 1][n - 1].contains(0);
    }
}
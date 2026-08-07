import java.util.*;

class Solution {
    private static final int[] PRIMES = {2, 3, 5, 7};
    private static final int[][] FACTOR = new int[10][4];
    static {
        for (int dg = 1; dg <= 9; dg++) {
            int x = dg;
            for (int p = 0; p < 4; p++) {
                while (x % PRIMES[p] == 0) { FACTOR[dg][p]++; x /= PRIMES[p]; }
            }
        }
    }

    public String smallestNumber(String num, long t) {
        int[] need = new int[4];
        for (int i = 0; i < 4; i++) {
            while (t % PRIMES[i] == 0) { need[i]++; t /= PRIMES[i]; }
        }
        if (t != 1) return "-1";

        int n = num.length();
        int[][][][] minSteps = buildMinSteps(need);

        int cap0 = need[0], cap1 = need[1], cap2 = need[2], cap3 = need[3];
        int minTotalDigits = minSteps[cap0][cap1][cap2][cap3];

        String res = tryLength(num.toCharArray(), need, n, true, minSteps);
        if (res != null) return res;

        int len = Math.max(n + 1, minTotalDigits);
        while (true) {
            char[] base = new char[len];
            Arrays.fill(base, '1');
            String r = tryLength(base, need, len, false, minSteps);
            if (r != null) return r;
            len++;
        }
    }

    private int[][][][] buildMinSteps(int[] need) {
        int L0 = need[0] + 1, L1 = need[1] + 1, L2 = need[2] + 1, L3 = need[3] + 1;
        int[][][][] minSteps = new int[L0][L1][L2][L3];
        for (int[][][] x : minSteps) for (int[][] y : x) for (int[] z : y) Arrays.fill(z, -1);
        minSteps[0][0][0][0] = 0;

        ArrayDeque<int[]> q = new ArrayDeque<>();
        q.add(new int[]{0,0,0,0});
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int A=cur[0], B=cur[1], C=cur[2], D=cur[3];
            int steps = minSteps[A][B][C][D];
            for (int digit = 1; digit <= 9; digit++) {
                int pa = Math.min(A + FACTOR[digit][0], need[0]);
                int pb = Math.min(B + FACTOR[digit][1], need[1]);
                int pc = Math.min(C + FACTOR[digit][2], need[2]);
                int pd = Math.min(D + FACTOR[digit][3], need[3]);
                if (minSteps[pa][pb][pc][pd] == -1) {
                    minSteps[pa][pb][pc][pd] = steps + 1;
                    q.add(new int[]{pa,pb,pc,pd});
                }
            }
        }
        return minSteps;
    }

    private String tryLength(char[] s, int[] need, int n, boolean tightAllowed, int[][][][] minSteps) {
        if (minSteps[need[0]][need[1]][need[2]][need[3]] > n) return null;
        char[] ans = new char[n];
        if (build(s, 0, tightAllowed, need[0], need[1], need[2], need[3], minSteps, ans)) {
            return new String(ans);
        }
        return null;
    }

    private boolean build(char[] s, int pos, boolean tight, int a, int b, int c, int d,
                           int[][][][] minSteps, char[] ans) {
        int n = s.length;
        if (pos == n) return a == 0 && b == 0 && c == 0 && d == 0;

        int remLen = n - pos - 1;
        int start = tight ? (s[pos] - '0') : 1;

        for (int digit = Math.max(start, 1); digit <= 9; digit++) {
            int na = Math.max(0, a - FACTOR[digit][0]);
            int nb = Math.max(0, b - FACTOR[digit][1]);
            int nc = Math.max(0, c - FACTOR[digit][2]);
            int nd = Math.max(0, d - FACTOR[digit][3]);
            int steps = minSteps[na][nb][nc][nd];
            if (steps != -1 && steps <= remLen) {
                ans[pos] = (char) ('0' + digit);
                boolean nextTight = tight && (digit == start);
                if (build(s, pos + 1, nextTight, na, nb, nc, nd, minSteps, ans)) {
                    return true;
                }
            }
        }
        return false;
    }
}
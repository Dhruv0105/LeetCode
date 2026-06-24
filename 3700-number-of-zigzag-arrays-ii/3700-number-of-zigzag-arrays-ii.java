class Solution {
    static final long MOD = 1_000_000_007L;
    public int zigZagArrays(int n, int l, int r) {
        int m = r-l+1;
        int size = 2*m;
        long[] base = new long[size];

        for (int j=0; j<m; j++) {
            base[j] = j;
            base[m+j] = m-1-j;
        }

        if (n==2) {
            long ans = 0;
            for (long x:base) ans = (ans+x) % MOD;
            return (int) ans;
        }

        long[][] T = new long[size][size];

        for (int j=0; j<m; j++) {
            for (int i=0; i<j; i++) {
                T[j][m+i] = 1;
            }

            for (int i=j+1; i<m; i++) {
                T[m+j][i] = 1;
            }
        }

        long[][] P = matrixPower(T, n-2);
        long[] result = multiply(P, base);

        long ans = 0;
        for (long x:result) {
            ans = (ans + x) % MOD;
        }

        return (int) ans;
    }

    private long[] multiply(long[][] A, long[] v) {
        int n = A.length;
        long[] res = new long[n];

        for (int i=0; i<n; i++) {
            long sum = 0;
            for (int j=0; j<n; j++) {
                sum = (sum + A[i][j] * v[j]) % MOD;
            }
            res[i] = sum;
        }
        return res;
    }

    private long[][] matrixPower(long[][] mat, long exp) {
        int n = mat.length;

        long[][] res = new long[n][n];
        for (int i=0; i<n; i++) {
            res[i][i] = 1;
        }

        while (exp >0) {
            if ((exp & 1) == 1) {
                res = multiply(res, mat);
            }

            mat = multiply(mat, mat);
            exp >>= 1;
        }
        return res;
    }

    private long[][] multiply(long[][] A, long[][] B) {
        int n = A.length;
        long[][] res = new long[n][n];

        for (int i=0; i<n; i++) {
            for (int k=0; k<n; k++) {
                if (A[i][k] == 0) continue;

                long val = A[i][k];

                for (int j=0; j<n; j++) {
                    if (B[k][j] == 0) continue;

                    res[i][j] = (res[i][j] + val * B[k][j]) % MOD;
                }
            }
        }

        return res;
    }
}
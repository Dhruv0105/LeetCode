class Solution {
public:
    static constexpr int MOD = 1'000'000'007;

    int zigZagArrays(int n, int l, int r) {
        int m = r - l + 1;

        vector<int> up(m + 1), down(m + 1);
        vector<int> nu(m + 1), nd(m + 1);
        vector<int> pref(m + 1);

        // length = 2
        for (int v = 1; v <= m; v++) {
            up[v] = v - 1;
            down[v] = m - v;
        }

        for (int len = 3; len <= n; len++) {

            pref[0] = 0;
            for (int i = 1; i <= m; i++) {
                pref[i] = (pref[i - 1] + down[i]) % MOD;
            }

            for (int v = 1; v <= m; v++) {
                nu[v] = pref[v - 1];
            }

            pref[0] = 0;
            for (int i = 1; i <= m; i++) {
                pref[i] = (pref[i - 1] + up[i]) % MOD;
            }

            long long total = pref[m];

            for (int v = 1; v <= m; v++) {
                nd[v] = (total - pref[v] + MOD) % MOD;
            }

            swap(up, nu);
            swap(down, nd);
        }

        long long ans = 0;
        for (int v = 1; v <= m; v++) {
            ans += up[v];
            ans += down[v];
        }

        return (int)(ans % MOD);
    }
};
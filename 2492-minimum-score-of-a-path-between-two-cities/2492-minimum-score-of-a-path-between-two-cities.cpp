class Solution {
public:
    int minScore(int n, vector<vector<int>>& roads) {
        vector<vector<pair<int, int>>> adj(n + 1);

        for (auto &e : roads) {
            adj[e[0]].push_back({e[1], e[2]});
            adj[e[1]].push_back({e[0], e[2]});
        }

        vector<bool> vis(n + 1, false);
        int ans = INT_MAX;

        function<void(int)> dfs = [&](int u) {
            vis[u] = true;

            for (auto &[v, w] : adj[u]) {
                ans = min(ans, w);
                if (!vis[v])
                    dfs(v);
            }
        };

        dfs(1);
        return ans;
    }
};
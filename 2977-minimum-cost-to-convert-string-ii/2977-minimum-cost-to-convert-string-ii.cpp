class Solution {
public:
    long long minimumCost(
        string source,
        string target,
        vector<string>& original,
        vector<string>& changed,
        vector<int>& cost
    ) {
        const long long INF = 1e18;
        int n = source.size();
        int m = original.size();
        unordered_map<int, vector<int>> byLen;
        for (int i = 0; i < m; i++) {
            byLen[original[i].size()].push_back(i);
        }
        unordered_map<int, unordered_map<string, unordered_map<string, long long>>> best;

        for (auto& [len, ids] : byLen) {
            unordered_map<string, int> id;
            vector<string> nodes;

            auto getId = [&](const string& s) {
                if (!id.count(s)) {
                    id[s] = nodes.size();
                    nodes.push_back(s);
                }
                return id[s];
            };

            for (int i : ids) {
                getId(original[i]);
                getId(changed[i]);
            }

            int V = nodes.size();
            vector<vector<long long>> dist(V, vector<long long>(V, INF));
            for (int i = 0; i < V; i++) dist[i][i] = 0;

            for (int i : ids) {
                int u = id[original[i]];
                int v = id[changed[i]];
                dist[u][v] = min(dist[u][v], (long long)cost[i]);
            }
            for (int k = 0; k < V; k++)
                for (int i = 0; i < V; i++)
                    for (int j = 0; j < V; j++)
                        if (dist[i][k] < INF && dist[k][j] < INF)
                            dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j]);

            for (int i = 0; i < V; i++)
                for (int j = 0; j < V; j++)
                    if (dist[i][j] < INF)
                        best[len][nodes[i]][nodes[j]] = dist[i][j];
        }

        vector<long long> dp(n + 1, INF);
        dp[n] = 0;

        for (int i = n - 1; i >= 0; i--) {
            if (source[i] == target[i])
                dp[i] = dp[i + 1];

            for (auto& [len, mp] : best) {
                if (i + len > n) continue;
                string s = source.substr(i, len);
                string t = target.substr(i, len);
                if (mp.count(s) && mp[s].count(t)) {
                    dp[i] = min(dp[i], mp[s][t] + dp[i + len]);
                }
            }
        }

        return dp[0] >= INF ? -1 : dp[0];
    }
};

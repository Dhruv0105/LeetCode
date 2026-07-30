class Solution {
public:
    vector<vector<pair<int,int>>> graph;
    vector<int> values;
    int ans = 0;
    vector<int> visited;

    void dfs(int node, int time, int score, int maxTime) {
        if (time > maxTime)
            return;
        if (node == 0)
            ans = max(ans, score);

        for (auto &[next, travelTime] : graph[node]) {
            if (time + travelTime <= maxTime) {
                bool isNew = false;

                if (visited[next] == 0) {
                    visited[next] = 1;
                    score += values[next];
                    isNew = true;
                }

                dfs(next, time + travelTime, score, maxTime);

                if (isNew) {
                    visited[next] = 0;
                    score -= values[next];
                }
            }
        }
    }

    int maximalPathQuality(vector<int>& values, vector<vector<int>>& edges, int maxTime) {
        this->values = values;
        int n = values.size();

        graph.assign(n, {});

        for (auto &e : edges) {
            int u = e[0], v = e[1], t = e[2];
            graph[u].push_back({v, t});
            graph[v].push_back({u, t});
        }

        visited.assign(n, 0);

        visited[0] = 1;
        dfs(0, 0, values[0], maxTime);

        return ans;
    }
};
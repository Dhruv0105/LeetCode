class Solution {
public:
    vector<vector<pair<int,int>>> graph;
    vector<int> ans;

    void dfs1(int node, int parent) {
        for (auto &[next, cost] : graph[node]) {
            if (next == parent) continue;
            ans[0] += cost;
            dfs1(next, node);
        }
    }

    void dfs2(int node, int parent) {
        for (auto &[next, cost] : graph[node]) {
            if (next == parent) continue;

            if (cost == 0)
                ans[next] = ans[node] + 1;
            else
                ans[next] = ans[node] - 1;

            dfs2(next, node);
        }
    }

    vector<int> minEdgeReversals(int n, vector<vector<int>>& edges) {
        graph.assign(n, {});
        ans.assign(n, 0);

        for (auto &e : edges) {
            int u = e[0], v = e[1];
            graph[u].push_back({v, 0});
            graph[v].push_back({u, 1});
        }

        dfs1(0, -1);
        dfs2(0, -1);

        return ans;
    }
};
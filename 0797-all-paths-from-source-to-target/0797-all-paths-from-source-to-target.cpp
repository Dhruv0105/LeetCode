class Solution {
public:
    vector<vector<int>> ans;

    void dfs(vector<vector<int>>& graph, int node, vector<int>& path) {
        int n = graph.size();

        if (node == n-1) {
            ans.push_back(path);
            return;
        }
        for (int next : graph[node]) {
            path.push_back(next);

            dfs(graph, next, path);

            path.pop_back();
        }
    }
    vector<vector<int>> allPathsSourceTarget(vector<vector<int>>& graph) {
        vector<int> path;

        path.push_back(0);
        dfs(graph, 0, path);

        return ans;
    }
};
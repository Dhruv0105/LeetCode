class Solution {
public:
    vector<int> remainingMethods(int n, int k, vector<vector<int>>& invocations) {
        vector<vector<int>> graph(n);

        for (auto &e : invocations) {
            graph[e[0]].push_back(e[1]);
        }

        vector<int> suspicious(n, 0);
        queue<int> q;
        q.push(k);
        suspicious[k] = 1;

        while (!q.empty()) {
            int node = q.front();
            q.pop();

            for (int nxt : graph[node]) {
                if (!suspicious[nxt]) {
                    suspicious[nxt] = 1;
                    q.push(nxt);
                }
            }
        }

        for (auto &e : invocations) {
            int a = e[0], b=e[1];
            if (!suspicious[a] && suspicious[b]) {
                vector<int> ans(n);
                iota(ans.begin(), ans.end(), 0);
                return ans;
            }
        }

        vector<int> ans;
        for (int i=0; i<n; i++) {
            if (!suspicious[i])
                ans.push_back(i);
        }

        return ans;
    }
};
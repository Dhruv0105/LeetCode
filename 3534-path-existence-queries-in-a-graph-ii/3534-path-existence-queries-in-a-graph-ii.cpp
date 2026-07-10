class Solution {
public:
    vector<int> pathExistenceQueries(int n, vector<int>& nums, int maxDiff, vector<vector<int>>& queries) {
        vector<pair<int, int>> arr;
        for (int i=0; i<n; i++)
            arr.push_back({nums[i], i});

        sort(arr.begin(), arr.end());

        vector<int> rank(n), comp(n);

        int cid = 0;
        for (int i=0; i<n; i++) {
            rank[arr[i].second] = i;
            if (i>0 && arr[i].first - arr[i-1].first > maxDiff)
                cid++;
            comp[i] = cid;
        }

        vector<int> far(n);
        int j = 0;
        for (int i=0; i<n; i++) {
            while (j+1<n && arr[j+1].first - arr[i].first <= maxDiff)
                j++;
            far[i] = j;
        }

        int LOG = 1;
        while ((1 << LOG) <= n) LOG++;
        vector<vector<int>> up(LOG, vector<int>(n));

        for (int i=0; i<n; i++)
            up[0][i] = far[i];

        for (int k=1; k<LOG; k++) {
            for (int i=0; i<n; i++) {
                up[k][i] = up[k-1][up[k-1][i]];
            }
        }

        vector<int> ans;

        for (auto &q : queries) {
            int u = q[0], v = q[1];

            if (u == v) {
                ans.push_back(0);
                continue;
            }

            int ru = rank[u], rv = rank[v];

            if (comp[ru] != comp[rv]) {
                ans.push_back(-1);
                continue;
            }

            if (ru > rv) swap(ru, rv);

            int pos = ru;
            int steps = 0;

            for (int k = LOG-1; k>=0; k--) {
                if (up[k][pos] < rv) {
                    pos = up[k][pos];
                    steps += (1 << k);
                }
            }

            if (pos < rv) steps++;
            ans.push_back(steps);
        }

        return ans;
    }
};
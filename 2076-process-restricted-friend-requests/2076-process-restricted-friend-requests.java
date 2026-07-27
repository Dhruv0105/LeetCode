class Solution {
    int[] parent;

    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    public boolean[] friendRequests(int n, int[][] restrictions, int[][] requests) {
        parent = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }

        boolean[] ans = new boolean[requests.length];

        for (int i = 0; i < requests.length; i++) {
            int u = requests[i][0];
            int v = requests[i][1];

            int pu = find(u);
            int pv = find(v);

            if (pu == pv) {
                ans[i] = true;
                continue;
            }

            boolean possible = true;
            for (int[] r : restrictions) {
                int a = find(r[0]);
                int b = find(r[1]);

                if ((a == pu && b == pv) || (a == pv && b == pu)) {
                    possible = false;
                    break;
                }
            }

            if (possible) {
                parent[pu] = pv;
                ans[i] = true;
            }
        }

        return ans;
    }
}
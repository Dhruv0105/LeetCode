
class Solution {

    int[] parent;

    int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    boolean union(int u, int v) {
        int pu = find(u);
        int pv = find(v);

        if (pu == pv) return false;

        parent[pv] = pu;
        return true;
    }

    public int[] findRedundantDirectedConnection(int[][] edges) {

        int n = edges.length;

        int[] parentNode = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            parentNode[i] = -1;
        }

        int[] first = null;
        int[] second = null;

        for (int[] edge : edges) {

            int u = edge[0];
            int v = edge[1];

            if (parentNode[v] == -1) {
                parentNode[v] = u;
            } else {
                first = new int[]{parentNode[v], v};
                second = edge;
                break;
            }
        }

        parent = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            parent[i] = i;
        }

        for (int[] edge : edges) {

            if (edge == second) continue;

            if (!union(edge[0], edge[1])) {

                if (first == null) {
                    return edge;
                }

                return first;
            }
        }

        return second;
    }
}

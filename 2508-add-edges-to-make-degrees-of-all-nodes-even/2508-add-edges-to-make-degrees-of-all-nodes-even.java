class Solution {
    public boolean isPossible(int n, List<List<Integer>> edges) {
        int[] degree = new int[n + 1];

        Set<String> graph = new HashSet<>();

        for (List<Integer> edge : edges) {
            int u = edge.get(0);
            int v = edge.get(1);

            degree[u]++;
            degree[v]++;

            graph.add(u + "#" + v);
            graph.add(v + "#" + u);
        }

        List<Integer> odd = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            if (degree[i] % 2 == 1) {
                odd.add(i);
            }
        }

        if (odd.size() == 0) {
            return true;
        }

        if (odd.size() > 4) {
            return false;
        }

        java.util.function.BiPredicate<Integer, Integer> hasEdge = 
            (a, b) -> graph.contains(a + "#" + b);

        if (odd.size() == 2) {
            int a = odd.get(0);
            int b = odd.get(1);

            if (!hasEdge.test(a, b)) {
                return true;
            }

            for (int i = 1; i <= n; i++) {
                if (i != a && i != b &&
                    !hasEdge.test(a, i) &&
                    !hasEdge.test(b, i)) {
                    return true;
                }
            }

            return false;
        }

        if (odd.size() == 4) {
            int a = odd.get(0);
            int b = odd.get(1);
            int c = odd.get(2);
            int d = odd.get(3);

            if (canPair(a, b, c, d, hasEdge)) {
                return true;
            }

            return false;
        }

        return false;
    }

    private boolean canPair(
            int a, int b, int c, int d,
            java.util.function.BiPredicate<Integer, Integer> hasEdge) {

        return (!hasEdge.test(a, b) && !hasEdge.test(c, d)) ||
               (!hasEdge.test(a, c) && !hasEdge.test(b, d)) ||
               (!hasEdge.test(a, d) && !hasEdge.test(b, c));
    }
}
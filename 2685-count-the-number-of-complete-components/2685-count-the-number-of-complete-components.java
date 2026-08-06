import java.util.*;

class Solution {
    public int countCompleteComponents(int n, int[][] edges) {
        List<Integer>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];

            graph[u].add(v);
            graph[v].add(u);
        }

        boolean[] visited = new boolean[n];
        int result = 0;

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                int[] count = new int[2];
                dfs(i, graph, visited, count);

                int vertices = count[0];
                int edgeCount = count[1] / 2; 
                if (edgeCount == vertices * (vertices - 1) / 2) {
                    result++;
                }
            }
        }

        return result;
    }

    private void dfs(int node, List<Integer>[] graph, boolean[] visited, int[] count) {
        visited[node] = true;
        count[0]++; 
        count[1] += graph[node].size(); 
        for (int neighbor : graph[node]) {
            if (!visited[neighbor]) {
                dfs(neighbor, graph, visited, count);
            }
        }
    }
}
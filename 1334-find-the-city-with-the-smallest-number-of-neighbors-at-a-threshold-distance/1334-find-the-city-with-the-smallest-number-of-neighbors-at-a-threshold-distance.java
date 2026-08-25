class Solution {
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        int INF = 1000000000;
        int[][] dist = new int[n][n];
        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                if (i==j) {
                    dist[i][j] = 0;
                } else {
                    dist[i][j] = INF;
                }
            }
        }

        for (int[] edge:edges) {
            int u=edge[0];
            int v=edge[1];
            int w=edge[2];

            dist[u][v] = w;
            dist[v][u] = w;
        }

        for (int k=0; k<n; k++) {
            for (int i=0; i<n; i++) {
                for (int j=0; j<n; j++) {
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

        int answer = -1;
        int minCount = Integer.MAX_VALUE;
        for (int i=0; i<n; i++) {
            int count = 0;

            for (int j=0; j<n; j++) {
                if (i != j && dist[i][j] <= distanceThreshold) {
                    count++;
                }
            }

            if (count <= minCount) {
                minCount = count;
                answer = i;
            }
        }

        return answer;
    }
}
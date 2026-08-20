import java.util.*;

class Solution {

    static class Edge {
        int to;
        long weight;

        Edge(int to, long weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    public long minimumWeight(int n, int[][] edges, int src1, int src2, int dest) {

        List<Edge>[] graph = new ArrayList[n];
        List<Edge>[] reverseGraph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
            reverseGraph[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            int u = e[0];
            int v = e[1];
            int w = e[2];

            graph[u].add(new Edge(v, w));
            reverseGraph[v].add(new Edge(u, w));
        }

        long[] dist1 = dijkstra(src1, graph);
        long[] dist2 = dijkstra(src2, graph);
        long[] distDest = dijkstra(dest, reverseGraph);

        long ans = Long.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            if (dist1[i] == Long.MAX_VALUE ||
                dist2[i] == Long.MAX_VALUE ||
                distDest[i] == Long.MAX_VALUE) {
                continue;
            }

            ans = Math.min(ans, dist1[i] + dist2[i] + distDest[i]);
        }

        return ans == Long.MAX_VALUE ? -1 : ans;
    }


    private long[] dijkstra(int src, List<Edge>[] graph) {

        int n = graph.length;

        long[] dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);

        PriorityQueue<long[]> pq =
                new PriorityQueue<>(Comparator.comparingLong(a -> a[1]));

        dist[src] = 0;
        pq.offer(new long[]{src, 0});

        while (!pq.isEmpty()) {

            long[] curr = pq.poll();

            int node = (int) curr[0];
            long d = curr[1];

            if (d > dist[node])
                continue;

            for (Edge edge : graph[node]) {

                if (dist[node] + edge.weight < dist[edge.to]) {

                    dist[edge.to] = dist[node] + edge.weight;

                    pq.offer(new long[]{
                            edge.to,
                            dist[edge.to]
                    });
                }
            }
        }

        return dist;
    }
}
class Graph {

    private List<int[]>[] graph;
    private int n;

    public Graph(int n, int[][] edges) {
        this.n = n;
        graph = new ArrayList[n];

        for (int i=0; i<n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge:edges) {
            graph[edge[0]].add(new int[]{edge[1], edge[2]});
        }
    }
    
    public void addEdge(int[] edge) {
        graph[edge[0]].add(new int[]{edge[1], edge[2]});
    }
    
    public int shortestPath(int node1, int node2) {
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);

        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[1] - b[1]);
        dist[node1] = 0;
        pq.offer(new int[]{node1, 0});

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int node = curr[0];
            int cost = curr[1];

            if (node == node2) {
                return cost;
            }

            if (cost > dist[node]) {
                continue;
            }

            for (int[] edge : graph[node]) {
                int next = edge[0];
                int newCost = cost + edge[1];

                if (newCost < dist[next]) {
                    dist[next] = newCost;
                    pq.offer(new int[]{next, newCost});
                }
            }
        }

        return -1;
    }
}

/**
 * Your Graph object will be instantiated and called as such:
 * Graph obj = new Graph(n, edges);
 * obj.addEdge(edge);
 * int param_2 = obj.shortestPath(node1,node2);
 */
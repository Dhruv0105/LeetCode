class Solution {
    public int networkDelayTime(int[][] times, int n, int k) {
        List<int[]>[] graph = new ArrayList[n+1];
        for (int i=1; i<=n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] time : times) {
            int u=time[0];
            int v=time[1];
            int w=time[2];

            graph[u].add(new int[]{v,w});
        }
        int[] dist = new int[n+1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[k] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[0]-b[0]);

        pq.offer(new int[]{0, k});
        while (!pq.isEmpty()) {
            int[] current = pq.poll();

            int time = current[0];
            int node = current[1];

            if (time > dist[node]) {
                continue;
            }

            for (int[] edge : graph[node]) {
                int nextNode = edge[0];
                int weight = edge[1];

                if (dist[node] + weight < dist[nextNode]) {
                    dist[nextNode] = dist[node] + weight;
                    pq.offer(new int[]{dist[nextNode], nextNode});
                }
            }
        }

        int answer = 0;
        for (int i=1; i<=n; i++) {
            if (dist[i] == Integer.MAX_VALUE) {
                return -1;
            }
            answer = Math.max(answer, dist[i]);
        }
        return answer;
    }
}
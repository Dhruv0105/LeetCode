import java.util.*;

class Solution {
    public int[] shortestAlternatingPaths(int n, int[][] redEdges, int[][] blueEdges) {
        List<List<Integer>> redAdj = new ArrayList<>();
        List<List<Integer>> blueAdj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            redAdj.add(new ArrayList<>());
            blueAdj.add(new ArrayList<>());
        }
        for (int[] e : redEdges) {
            redAdj.get(e[0]).add(e[1]);
        }
        for (int[] e : blueEdges) {
            blueAdj.get(e[0]).add(e[1]);
        }

        int[][] dist = new int[n][2]; 
        for (int[] row : dist) {
            Arrays.fill(row, -1);
        }

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0}); 
        queue.offer(new int[]{0, 1}); 
        dist[0][0] = 0;
        dist[0][1] = 0;

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int node = curr[0];
            int colorState = curr[1]; 

            if (colorState == 0) {
                for (int next : blueAdj.get(node)) {
                    if (dist[next][1] == -1) {
                        dist[next][1] = dist[node][0] + 1;
                        queue.offer(new int[]{next, 1});
                    }
                }
            } else {
                for (int next : redAdj.get(node)) {
                    if (dist[next][0] == -1) {
                        dist[next][0] = dist[node][1] + 1;
                        queue.offer(new int[]{next, 0});
                    }
                }
            }
        }

        int[] answer = new int[n];
        for (int i = 0; i < n; i++) {
            if (dist[i][0] == -1 && dist[i][1] == -1) {
                answer[i] = -1;
            } else if (dist[i][0] == -1) {
                answer[i] = dist[i][1];
            } else if (dist[i][1] == -1) {
                answer[i] = dist[i][0];
            } else {
                answer[i] = Math.min(dist[i][0], dist[i][1]);
            }
        }

        return answer;
    }
}
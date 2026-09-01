import java.util.*;

class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();
        int startR = 0, startC = 0;
        Map<String, Integer> litterMap = new HashMap<>();
        int litterCount = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char ch = classroom[i].charAt(j);
                if (ch == 'S') {
                    startR = i;
                    startC = j;
                }
                if (ch == 'L') {
                    litterMap.put(i + "," + j, litterCount++);
                }
            }
        }
        int targetMask = (1 << litterCount) - 1;
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startR, startC, energy, 0, 0});
        boolean[][][][] visited = new boolean[m][n][energy + 1][1 << litterCount];
        visited[startR][startC][energy][0] = true;
        int[][] dirs = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1}
        };
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];
            int e = curr[2];
            int mask = curr[3];
            int moves = curr[4];
            if (mask == targetMask) {
                return moves;
            }
            for (int[] d : dirs) {
                int nr = r + d[0];
                int nc = c + d[1];
                if (nr < 0 || nc < 0 || nr >= m || nc >= n)
                    continue;
                char cell = classroom[nr].charAt(nc);
                if (cell == 'X')
                    continue;
                int newEnergy = e - 1;
                if (newEnergy < 0)
                    continue;
                int newMask = mask;
                if (cell == 'L') {
                    int idx = litterMap.get(nr + "," + nc);
                    newMask |= (1 << idx);
                }
                if (cell == 'R') {
                    newEnergy = energy;
                }
                if (!visited[nr][nc][newEnergy][newMask]) {
                    visited[nr][nc][newEnergy][newMask] = true;
                    queue.offer(new int[]{
                        nr, nc, newEnergy, newMask, moves + 1
                    });
                }
            }
        }

        return -1;
    }
}
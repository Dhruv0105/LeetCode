class Solution {
    public int[][] highestPeak(int[][] isWater) {
        int m = isWater.length;
        int n = isWater[0].length;
        int[][] height = new int[m][n];
        int[] queueX = new int[m * n];
        int[] queueY = new int[m * n];
        int head = 0, tail = 0;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (isWater[i][j] == 1) {
                    height[i][j] = 0;
                    queueX[tail] = i;
                    queueY[tail] = j;
                    tail++;
                } else {
                    height[i][j] = -1;
                }
            }
        }
        
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        
        while (head < tail) {
            int x = queueX[head];
            int y = queueY[head];
            head++;
            
            for (int d = 0; d < 4; d++) {
                int nx = x + dx[d];
                int ny = y + dy[d];
                
                if (nx >= 0 && nx < m && ny >= 0 && ny < n && height[nx][ny] == -1) {
                    height[nx][ny] = height[x][y] + 1;
                    queueX[tail] = nx;
                    queueY[tail] = ny;
                    tail++;
                }
            }
        }
        
        return height;
    }
}
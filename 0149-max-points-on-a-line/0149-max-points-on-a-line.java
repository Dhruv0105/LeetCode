class Solution {
    public int maxPoints(int[][] points) {
        int n = points.length;
        if (n<=2) {
            return n;
        }
        int answer =0;

        for (int i=0; i<n; i++) {
            Map<String, Integer> slopeCount = new HashMap<>();
            int maxOnLine = 0;

            for (int j=i+1; j<n; j++) {
                int dx = points[j][0] - points[i][0];
                int dy = points[j][1] - points[i][1];

                int g = gcd(dx, dy);
                dx /= g;
                dy /= g;

                if (dx <0) {
                    dx = -dx;
                    dy = -dy;
                } else if (dx == 0) {
                    dy = 1;
                } else if (dy == 0) {
                    dx = 1;
                }

                String key = dy + "/" + dx;
                int count = slopeCount.getOrDefault(key, 0) + 1;
                slopeCount.put(key, count);

                maxOnLine = Math.max(maxOnLine, count);
            }

            answer = Math.max(answer, maxOnLine + 1);
        }

        return answer;
    }

    private int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);

        while (b != 0) {
            int temp = a%b;
            a = b;
            b = temp;
        }

        return a;
    }
}
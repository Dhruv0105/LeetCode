class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        long[] diff = new long[nums1.length];
        long totalOps = (long) k1 + k2;
        long max = 0;
        for (int i = 0; i < nums1.length; i++) {
            diff[i] = Math.abs(nums1[i] - nums2[i]);
            max = Math.max(max, diff[i]);
        }

        long low = 0, high = max;
        while (low < high) {
            long mid = low + (high - low) / 2;
            long needed = 0;

            for (long d : diff) {
                if (d > mid) {
                    needed += d - mid;
                }
            }

            if (needed <= totalOps) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        long limit = low;
        long remaining = totalOps;
        long answer = 0;

        for (long d : diff) {
            if (d > limit) {
                remaining -= d - limit;
                d = limit;
            }
            answer += d * d;
        }
        if (remaining > 0) {
            long[] reduced = new long[diff.length];

            for (int i = 0; i < diff.length; i++) {
                reduced[i] = Math.min(diff[i], limit);
            }

            java.util.Arrays.sort(reduced);

            answer = 0;
            for (int i = 0; i < reduced.length; i++) {
                long d = reduced[i];

                if (remaining > 0 && d == limit && d > 0) {
                    d--;
                    remaining--;
                }

                answer += d * d;
            }
        }

        return answer;
    }
}

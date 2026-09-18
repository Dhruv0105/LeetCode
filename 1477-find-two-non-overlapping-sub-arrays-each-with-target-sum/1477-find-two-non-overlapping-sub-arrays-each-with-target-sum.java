class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;
        int[] minLen = new int[n];
        Arrays.fill(minLen, Integer.MAX_VALUE);

        int left = 0;
        int sum = 0;

        int ans = Integer.MAX_VALUE;
        int best = Integer.MAX_VALUE;

        for (int right = 0; right < n; right++) {
            sum += arr[right];

            while (sum > target && left <= right) {
                sum -= arr[left++];
            }

            if (sum == target) {
                int len = right - left + 1;

                if (left > 0 && minLen[left - 1] != Integer.MAX_VALUE) {
                    ans = Math.min(ans, len + minLen[left - 1]);
                }

                best = Math.min(best, len);
            }

            if (right > 0) {
                minLen[right] = Math.min(minLen[right - 1], best);
            } else {
                minLen[right] = best;
            }
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}
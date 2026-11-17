class Solution {
    public int splitArray(int[] nums, int k) {
        int low = 0, high = 0;
        for (int x : nums) {
            low = Math.max(low, x);
            high += x;
        }
        while (low < high) {
            int mid = low + (high - low) / 2;
            int parts = 1, sum = 0;
            for (int x : nums) {
                if (sum > mid - x) {
                    parts++;
                    sum = x;
                } else {
                    sum += x;
                }
            }
            if (parts > k) low = mid + 1;
            else high = mid;
        }
        return low;
    }
}
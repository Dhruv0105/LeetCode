class Solution {
    public int largestInteger(int[] nums, int k) {
        int[] count = new int[51];
        int n = nums.length;

        for (int i = 0; i <= n - k; i++) {
            boolean[] seen = new boolean[51];

            for (int j = i; j < i + k; j++) {
                seen[nums[j]] = true;
            }

            for (int x = 0; x <= 50; x++) {
                if (seen[x]) {
                    count[x]++;
                }
            }
        }

        for (int i = 50; i >= 0; i--) {
            if (count[i] == 1) {
                return i;
            }
        }

        return -1;
    }
}
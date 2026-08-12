class Solution {
    public int maxSubarrayLength(int[] nums, int k) {
        Map<Integer, Integer> count = new HashMap<>();
        int left = 0, ans = 0;
        for (int right=0; right<nums.length; right++) {
            count.merge(nums[right], 1, Integer::sum);
            while (count.get(nums[right]) > k) {
                int val = nums[left];
                count.put(val, count.get(val) - 1);
                left++;
            }

            ans = Math.max(ans, right-left+1);
        }

        return ans;
    }
}
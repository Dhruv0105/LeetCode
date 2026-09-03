class Solution {
    public boolean uniformArray(int[] nums1) {
        int min = Integer.MAX_VALUE;
        boolean hasOdd = false;

        for (int num : nums1) {
            min = Math.min(min, num);
            if ((num & 1) == 1) {
                hasOdd = true;
            }
        }
        if ((min & 1) == 1) {
            return true;
        }
        
        return !hasOdd;
    }
}
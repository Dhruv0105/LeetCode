import java.util.*;

class Solution {
    public int maximumLength(int[] nums) {
        Map<Long, Integer> freq = new HashMap<>();
        for (int num : nums) {
            freq.put((long) num, freq.getOrDefault((long) num, 0) + 1);
        }

        int maxLen = 1;

        for (long x : freq.keySet()) {
            if (x == 1) {
                int count = freq.get(x);
                if (count % 2 == 0) count--; 
                maxLen = Math.max(maxLen, count);
                continue;
            }

            long curr = x;
            int length = 0;

            while (freq.getOrDefault(curr, 0) >= 2) {
                length += 2;
                curr = curr * curr;

                if (curr > 1e9) break; 
            }

            if (freq.getOrDefault(curr, 0) >= 1) {
                length += 1;
            } else {
                length -= 1; 
            }

            maxLen = Math.max(maxLen, length);
        }

        return maxLen;
    }
}
import java.util.*;
class Solution {
    TreeMap<Integer, Integer> left = new TreeMap<>();
    TreeMap<Integer, Integer> right = new TreeMap<>();
    int leftSize = 0, rightSize = 0;
    long leftSum = 0, rightSum = 0;
    void add(TreeMap<Integer, Integer> map, int val) {
        map.put(val, map.getOrDefault(val, 0) + 1);
    }
    void remove(TreeMap<Integer, Integer> map, int val) {
        int count = map.get(val);
        if (count == 1) map.remove(val);
        else map.put(val, count - 1);
    }
    void insert(int val) {
        if (left.isEmpty() || val <= left.lastKey()) {
            add(left, val);
            leftSize++;
            leftSum += val;
        } else {
            add(right, val);
            rightSize++;
            rightSum += val;
        }

        balance();
    }
    void erase(int val) {
        if (left.containsKey(val)) {
            remove(left, val);
            leftSize--;
            leftSum -= val;
        } else {
            remove(right, val);
            rightSize--;
            rightSum -= val;
        }

        balance();
    }
    void balance() {
        int target = (leftSize + rightSize + 1) / 2;
        while (leftSize > target) {
            int val = left.lastKey();
            remove(left, val);
            leftSize--;
            leftSum -= val;
            add(right, val);
            rightSize++;
            rightSum += val;
        }
        while (leftSize < target) {
            int val = right.firstKey();
            remove(right, val);
            rightSize--;
            rightSum -= val;
            add(left, val);
            leftSize++;
            leftSum += val;
        }
    }
    long getCost() {
        long median = left.lastKey();
        return median * leftSize - leftSum
             + rightSum - median * rightSize;
    }
    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;
        long[] cost = new long[n - x + 1];
        for (int i = 0; i < n; i++) {
            insert(nums[i]);
            if (i >= x) {
                erase(nums[i - x]);
            }
            if (i >= x - 1) {
                cost[i - x + 1] = getCost();
            }
        }

        long INF = Long.MAX_VALUE / 4;
        long[][] dp = new long[k + 1][n + 1];
        for (int j = 1; j <= k; j++) {
            Arrays.fill(dp[j], INF);
        }
        for (int j = 1; j <= k; j++) {
            for (int i = 1; i <= n; i++) {
                dp[j][i] = dp[j][i - 1];
                if (i >= x && dp[j - 1][i - x] != INF) {
                    dp[j][i] = Math.min(
                        dp[j][i],
                        dp[j - 1][i - x] + cost[i - x]
                    );
                }
            }
        }

        return dp[k][n];
    }
}

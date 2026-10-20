class Solution {
    public int sortableIntegers(int[] nums) {
        int n = nums.length;

        // counting sort (values <= 1e5)
        int[] cnt = new int[100002];
        for (int x : nums) cnt[x]++;
        int[] sorted = new int[n];
        int idx = 0;
        for (int v = 0; v < cnt.length; v++) {
            while (cnt[v]-- > 0) sorted[idx++] = v;
        }

        int ans = 0;
        for (int k = 1; k <= n; k++) {
            if (n % k != 0) continue;
            boolean ok = true;
            for (int s = 0; s < n && ok; s += k) {
                int descents = 0, d = -1;
                for (int j = 0; j < k; j++) {
                    int nx = (j + 1 == k) ? 0 : j + 1;
                    if (nums[s + j] > nums[s + nx]) {
                        descents++;
                        d = j;
                        if (descents > 1) break;
                    }
                }
                if (descents > 1) {
                    ok = false;
                    break;
                }
                int off = (descents == 0) ? 0 : (d + 1) % k;
                int pos = off;
                for (int j = 0; j < k; j++) {
                    if (nums[s + pos] != sorted[s + j]) {
                        ok = false;
                        break;
                    }
                    pos++;
                    if (pos == k) pos = 0;
                }
            }
            if (ok) ans += k;
        }
        return ans;
    }
}
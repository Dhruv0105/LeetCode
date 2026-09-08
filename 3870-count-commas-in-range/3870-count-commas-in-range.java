class Solution {
    public int countCommas(int n) {
        int ans = 0;
        for (int x = 1000; x <= n; x *= 1000) {
            ans += n - x + 1;
        }
        return ans;
    }
}
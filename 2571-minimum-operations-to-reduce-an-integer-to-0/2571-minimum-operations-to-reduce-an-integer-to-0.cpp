class Solution {
public:
    unordered_map<int, int> memo;
    int minOperations(int n) {
        if ((n & (n-1)) == 0) return 1;

        if (memo.count(n)) return memo[n];

        int p=1;
        while ((p<<1) < n) {
            p <<= 1;
        }

        return memo[n] = 1+min(minOperations(n-p), minOperations((p<<1)-n));
    }
};
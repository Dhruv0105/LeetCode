class Solution {
public:
    int uniqueXorTriplets(vector<int>& nums) {
        int n = nums.size();

        if (n == 1) return 1;
        if (n == 2) return 2;

        int p = 1;
        while (p < n) {
            p <<= 1;
        }

        if (p == n) {
            return p << 1;   // n is power of 2
        }

        return p;
    }
};
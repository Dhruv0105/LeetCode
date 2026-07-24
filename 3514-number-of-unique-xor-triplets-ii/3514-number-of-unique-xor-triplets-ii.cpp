class Solution {
public:
    int uniqueXorTriplets(vector<int>& nums) {
        vector<bool> values(2048, false);

        for (int x : nums) {
            values[x] = true;
        }

        vector<int> distinct;
        for (int i = 0; i < 2048; i++) {
            if (values[i])
                distinct.push_back(i);
        }

        vector<bool> twoXor(2048, false);

        for (int a : distinct) {
            for (int b : distinct) {
                twoXor[a ^ b] = true;
            }
        }

        vector<bool> result(2048, false);

        for (int x = 0; x < 2048; x++) {
            if (twoXor[x]) {
                for (int c : distinct) {
                    result[x ^ c] = true;
                }
            }
        }

        int ans = 0;
        for (bool x : result) {
            if (x)
                ans++;
        }

        return ans;
    }
};
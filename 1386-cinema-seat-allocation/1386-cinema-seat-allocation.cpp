class Solution {
public:
    int maxNumberOfFamilies(int n, vector<vector<int>>& reservedSeats) {
        unordered_map<int, int> rows;

        for (auto &seat : reservedSeats) {
            int row = seat[0];
            int col = seat[1];

            rows[row] |= (1 << (col - 1));
        }

        long long ans = (long long)(n - rows.size()) * 2;

        int leftMask  = (1 << 1) | (1 << 2) | (1 << 3) | (1 << 4);
        int midMask   = (1 << 3) | (1 << 4) | (1 << 5) | (1 << 6); 
        int rightMask = (1 << 5) | (1 << 6) | (1 << 7) | (1 << 8); 

        for (auto &[row, mask] : rows) {
            bool left  = (mask & leftMask) == 0;
            bool mid   = (mask & midMask) == 0;
            bool right = (mask & rightMask) == 0;

            if (left && right) {
                ans += 2;
            } else if (left || mid || right) {
                ans += 1;
            }
        }

        return (int)ans;
    }
};
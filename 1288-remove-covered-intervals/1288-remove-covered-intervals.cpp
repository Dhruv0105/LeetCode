class Solution {
public:
    int removeCoveredIntervals(vector<vector<int>>& intervals) {
        sort(intervals.begin(), intervals.end(), [](const vector<int>& a, const vector<int>& b) {
            if (a[0] == b[0])
                return a[1] > b[1];
            return a[0] < b[0];
        });

    int ans = 0;
    int maxRight = -1;

    for (auto &interval : intervals) {
        if (interval[1] > maxRight) {
            ans++;
            maxRight = interval[1];
        }
    }

    return ans;
    }
};
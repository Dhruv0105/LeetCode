class Solution {
public:
    vector<int> findMissingElements(vector<int>& nums) {
        int mn = *min_element(nums.begin(), nums.end());
        int mx = *max_element(nums.begin(), nums.end());

        vector<bool> present(mx - mn + 1, false);

        for (int num : nums) {
            present[num - mn] = true;
        }

        vector<int> ans;

        for (int i = 0; i < present.size(); i++) {
            if (!present[i]) {
                ans.push_back(i + mn);
            }
        }

        return ans;
    }
};
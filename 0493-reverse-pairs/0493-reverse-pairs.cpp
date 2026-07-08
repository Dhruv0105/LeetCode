class Solution {
public:
    int merge(vector<int>& nums, int l, int mid, int r) {
        int cnt =0;
        int j = mid+1;

        for (int i=l; i<=mid; i++) {
            while (j <= r && (long long)nums[i] > 2LL * (long long)nums[j])
            j++;
            cnt += j-(mid+1);
        }

        vector<int> temp;
        int i = l;
        j = mid + 1;

        while (i <= mid && j<= r) {
            if (nums[i] <= nums[j])
            temp.push_back(nums[i++]);
            else
            temp.push_back(nums[j++]);
        }

        while (i <= mid)
            temp.push_back(nums[i++]);

        while (j <= r)
            temp.push_back(nums[j++]);

        for (int k=l; k<=r; k++)
            nums[k] = temp[k-l];

        return cnt;
    }

    int mergeSort(vector<int>& nums, int l, int r) {
        if (l>=r)
        return 0;

        int mid = l+(r-l)/2;

        int cnt = mergeSort(nums, l, mid);
        cnt += mergeSort(nums, mid+1, r);
        cnt += merge(nums, l, mid, r);

        return cnt;
    }
    int reversePairs(vector<int>& nums) {
       if (nums.empty()) return 0;
       return mergeSort(nums, 0, nums.size()-1);
    }
};
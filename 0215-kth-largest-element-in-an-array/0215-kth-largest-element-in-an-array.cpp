class Solution {
public:
    int partition(vector<int>& nums, int l, int r) {
        int pivotIdx = l + rand() % (r-l+1);
        swap(nums[pivotIdx], nums[r]);

        int pivot = nums[r];
        int i = l;

        for (int j=l; j<r; j++) {
            if (nums[j] <= pivot)
                swap(nums[i++], nums[j]);
        }

        swap(nums[i], nums[r]);
        return i;
    }

    int quickSelect(vector<int>& nums, int l, int r, int k) {
        while (l<=r) {
            int p = partition(nums, l, r);

            if (p==k)
                return nums[p];
            else if (p<k)
                l = p+1;
            else
                r = p-1;
        }

        return -1;
    }
    int findKthLargest(vector<int>& nums, int k) {
        nth_element(nums.begin(), nums.end()-k, nums.end());
        return nums[nums.size()-k];
    }
};
class Solution {

    class Fenwick {
        int[] bit;

        Fenwick(int n) {
            bit = new int[n+2];
        }

        void update(int idx, int val) {
            while (idx < bit.length) {
                bit[idx] += val;
                idx += idx & -idx;
            }
        }

        int query(int idx) {
            int sum = 0;
            while (idx>0) {
                sum += bit[idx];
                idx -= idx & -idx;
            }
            return sum;
        }
    }
    public int countMajoritySubarrays(int[] nums, int target) {
        int n = nums.length;

        int offset = n+1;
        Fenwick ft = new Fenwick(2*n+5);

        int prefix = 0;
        int ans = 0;

        ft.update(offset+1, 1);

        for (int num:nums) {
            if (num == target)
            prefix++;
        else
            prefix--;

        ans += ft.query(prefix + offset);

        ft.update(prefix + offset + 1, 1);
        }

        return ans;
    }
}
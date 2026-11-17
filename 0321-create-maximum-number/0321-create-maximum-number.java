class Solution {
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int[] ans = new int[k];
        for (int i=Math.max(0, k-nums2.length); i <= Math.min(k, nums1.length); i++) {
            int[] a = pick(nums1, i);
            int[] b = pick(nums2, k-i);
            int[] c = merge(a, b);
            if (greater(c, 0, ans, 0)) ans = c;
        }
        return ans;
    }
    int[] pick(int[] nums, int k) {
        int[] st = new int[k];
        int top =0, drop = nums.length-k;
        for (int x:nums) {
            while (top >0 && drop>0 && st[top-1] < x) {
                top--;
                drop--;
            }
            if (top<k) st[top++] = x;
            else drop--;
        }
        return st;
    }
    int[] merge(int[] a, int[] b) {
        int[] c = new int[a.length + b.length];
        int i=0, j=0;
        for (int p=0; p<c.length; p++)
        c[p] = greater(a, i, b, j) ? a[i++] : b[j++];
        return c;
    }
    boolean greater(int[] a, int i, int[] b, int j) {
        while (i < a.length && j<b.length && a[i] == b[j]) {
            i++;
            j++;
        }
        return j==b.length || (i<a.length && a[i] > b[j]);
    }
}
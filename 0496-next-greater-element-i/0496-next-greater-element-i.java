class Solution {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int[] ans = new int[nums1.length];
        Stack<Integer> st = new Stack<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int x:nums2) {
            while (!st.isEmpty() && st.peek() < x)
            map.put(st.pop(), x);
            st.push(x);
        }
        for (int i=0; i<nums1.length; i++)
        ans[i] = map.getOrDefault(nums1[i], -1);
        return ans;
    }
}
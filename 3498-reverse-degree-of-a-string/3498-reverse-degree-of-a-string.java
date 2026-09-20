class Solution {
    public int reverseDegree(String s) {
        int ans = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            int reversePos = 'z' - ch + 1;
            int index = i + 1;
            ans += reversePos * index;
        }

        return ans;
    }
}
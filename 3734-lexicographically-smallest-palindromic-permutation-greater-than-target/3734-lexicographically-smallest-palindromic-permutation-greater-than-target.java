class Solution {
    public String lexPalindromicPermutation(String s, String target) {

        int n = s.length();
        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        int odd = 0;
        char mid = 0;

        for (int i = 0; i < 26; i++) {
            if ((freq[i] & 1) == 1) {
                odd++;
                mid = (char) ('a' + i);
            }
        }

        if (odd > 1) {
            return "";
        }

        int[] half = new int[26];

        for (int i = 0; i < 26; i++) {
            half[i] = freq[i] / 2;
        }

        int len = n / 2;

        String targetLeft = target.substring(0, len);


        // 1. Try using the same left half as target
        if (canMake(targetLeft, half)) {

            String candidate = makePalindrome(targetLeft, mid, n);

            if (candidate.compareTo(target) > 0) {
                return candidate;
            }
        }


        // 2. Otherwise find next greater left half
        String bigger = nextGreater(targetLeft, half);

        if (bigger != null) {
            return makePalindrome(bigger, mid, n);
        }

        return "";
    }


    private boolean canMake(String str, int[] count) {

        int[] temp = count.clone();

        for (char c : str.toCharArray()) {

            if (--temp[c - 'a'] < 0) {
                return false;
            }
        }

        return true;
    }


    private String nextGreater(String target, int[] count) {

        int n = target.length();

        for (int pos = n - 1; pos >= 0; pos--) {

            int[] remain = count.clone();

            boolean ok = true;

            for (int i = 0; i < pos; i++) {

                int c = target.charAt(i) - 'a';

                if (--remain[c] < 0) {
                    ok = false;
                    break;
                }
            }

            if (!ok)
                continue;


            int current = target.charAt(pos) - 'a';

            for (int c = current + 1; c < 26; c++) {

                if (remain[c] > 0) {

                    StringBuilder ans = new StringBuilder();

                    ans.append(target.substring(0, pos));

                    ans.append((char) ('a' + c));

                    remain[c]--;

                    for (int j = 0; j < 26; j++) {
                        while (remain[j] > 0) {
                            ans.append((char) ('a' + j));
                            remain[j]--;
                        }
                    }

                    return ans.toString();
                }
            }
        }

        return null;
    }


    private String makePalindrome(String left, char mid, int n) {

        StringBuilder ans = new StringBuilder();

        ans.append(left);

        if ((n & 1) == 1) {
            ans.append(mid);
        }

        ans.append(new StringBuilder(left).reverse());

        return ans.toString();
    }
}
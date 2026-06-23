class Solution {
    public char processStr(String s, long k) {
        int n = s.length();
        long[] len = new long[n+1];

        for (int i=0; i<n; i++) {
            char ch = s.charAt(i);

            if (ch >= 'a' && ch <= 'z') {
                len[i+1] = len[i] + 1;
            } else if (ch == '*') {
                len[i+1] = Math.max(0, len[i] - 1);
            } else if (ch == '#') {
                len[i+1] = len[i] * 2;
            } else { // '%'
                len[i+1] = len[i];
            }
        }

        long finalLen = len[n];
        if (k<0 || k>= finalLen) {
            return '.';
        }

        for (int i=n-1; i>=0; i--) {
            char ch = s.charAt(i);

            if (ch >= 'a' && ch <= 'z') {
                long before = len[i];
                long after = before + 1;

                if (k == after - 1) {
                    return ch;
                }
            } else if (ch == '#') {
                long before = len[i];
                if (before > 0) {
                    k %= before;
                }
            } else if (ch == '%') {
                long before = len[i];
                k = before - 1 - k;
            } else {
                // '*'
            }
        }

        return '.';
    }
}
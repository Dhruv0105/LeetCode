class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int n = s.length();
        int[] freq = new int[26];

        for (char c:s.toCharArray()) {
            freq[c-'a']++;
        }
        char[] ans = new char[n];
        for (int i=0; i<n; i++) {
            int ch = target.charAt(i) - 'a';

            if (freq[ch]>0) {
                ans[i] = target.charAt(i);
                freq[ch]--;
            } else {
                return makeGreater(ans, i, freq, target.charAt(i));
            }
        }
        for (int i=n-1; i>=0; i--) {
            freq[ans[i] - 'a']++;
            for (int c=ans[i] - 'a' + 1; c<26; c++) {
                if (freq[c] > 0) {
                    ans[i] = (char) ('a'+c);
                    freq[c]--;

                    int idx = i+1;
                    for (int j=0; j<26; j++) {
                        while (freq[j] > 0) {
                            ans[idx++] = (char) ('a'+j);
                            freq[j]--;
                        }
                    }

                    return new String(ans);
                }
            }
        }

        return "";
    }

    private String makeGreater(char[] ans, int pos, int[] freq, char limit) {
        for (int c=limit-'a'+1; c<26; c++) {
            if (freq[c]>0) {
                ans[pos] = (char) ('a'+c);
                freq[c]--;

                int idx = pos+1;
                for (int j=0; j<26; j++) {
                    while (freq[j]>0) {
                        ans[idx++] = (char) ('a'+j);
                        freq[j]--;
                    }
                }

                return new String(ans);
            }
        }

        for (int i=pos-1; i>=0; i--) {
            freq[ans[i]-'a']++;

            for (int c=ans[i]-'a'+1; c<26; c++) {
                if (freq[c]>0) {
                    ans[i] = (char) ('a'+c);
                    freq[c]--;

                    int idx = i+1;
                    for (int j=0; j<26; j++) {
                        while (freq[j]>0) {
                            ans[idx++] = (char) ('a'+j);
                            freq[j]--;
                        }
                    }

                    return new String(ans);
                }
            }
        }

        return "";
    }
}
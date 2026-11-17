class Solution {
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> ans = new ArrayList<>();
        int i=0, n=words.length;

        while (i<n) {
            int j=i+1, len=words[i].length();
            while (j<n && len+words[j].length() + (j-i) <= maxWidth)
            len += words[j++].length();

            int gaps = j-i-1;
            StringBuilder s = new StringBuilder();
            if (j==n || gaps == 0) {
                for (int k=i; k<j; k++) {
                    if (k>i) s.append(" ");
                    s.append(words[k]);
                }
                while (s.length() < maxWidth) s.append(" ");
            } else {
                int spaces = (maxWidth-len) / gaps;
                int extra = (maxWidth-len) % gaps;
                for (int k=i; k<j; k++) {
                    s.append(words[k]);
                    if (k<j-1) {
                        for (int x=0; x<spaces + (k-i < extra ? 1:0); x++)
                        s.append(" ");
                    }
                }
            }

            ans.add(s.toString());
            i=j;
        }
        return ans;
    }
}
class Solution {
public:
    string minRemoveToMakeValid(string s) {
        int n = s.size();
        vector<bool> keep(n, true);

        int open = 0;

        for (int i = 0; i < n; i++) {
            if (s[i] == '(') {
                open++;
            }
            else if (s[i] == ')') {
                if (open > 0) {
                    open--;
                } else {
                    keep[i] = false;
                }
            }
        }

        for (int i = n - 1; i >= 0 && open > 0; i--) {
            if (s[i] == '(') {
                keep[i] = false;
                open--;
            }
        }

        string ans;
        for (int i = 0; i < n; i++) {
            if (keep[i]) {
                ans += s[i];
            }
        }

        return ans;
    }
};
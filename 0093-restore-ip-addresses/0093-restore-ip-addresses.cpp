class Solution {
public:
    vector<string> ans;

    void backtrack(string &s, int idx, int parts, string curr) {
        if (parts == 4) {
            if (idx == s.size()) {
                curr.pop_back();           
                ans.push_back(curr);
            }
            return;
        }
        for (int len = 1; len <= 3 && idx + len <= s.size(); len++) {
            string part = s.substr(idx, len);

            if (part.size() > 1 && part[0] == '0')
                break;

            int num = stoi(part);
            if (num > 255)
                continue;
            backtrack(s, idx + len, parts + 1, curr + part + ".");
        }
    }

    vector<string> restoreIpAddresses(string s) {
        backtrack(s, 0, 0, "");
        return ans;
    }
};
class Solution {
public:
    string removeDuplicates(string s, int k) {
        vector<pair<char, int>> st;

        for (char c:s) {
            if (!st.empty() && st.back().first == c) {
                st.back().second++;

                if (st.back().second == k) {
                    st.pop_back();
                }
            } else {
                st.push_back({c, 1});
            }
        }

        string ans;

        for (auto &[ch, cnt] : st) {
            ans.append(cnt, ch);
        }

        return ans;
    }
};
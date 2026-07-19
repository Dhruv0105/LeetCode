class Solution {
public:
    string ans = "";
    int cnt = 0;

    void dfs(string &curr, int n, int k) {
        if (!ans.empty()) return;

        if (curr.size() == n) {
            cnt++;
            if (cnt == k)
                ans = curr;
            return;
        }

        for (char ch = 'a'; ch <='c'; ch++) {
            if (curr.empty() || curr.back() != ch) {
                curr.push_back(ch);
                dfs(curr, n, k);
                curr.pop_back();
            }
        }
    }
    string getHappyString(int n, int k) {
        string curr;
        dfs(curr, n, k);
        return ans;
    }
};
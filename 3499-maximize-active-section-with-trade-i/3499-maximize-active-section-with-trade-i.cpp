class Solution {
public:
    int maxActiveSectionsAfterTrade(string s) {
        string t = "1" + s + "1";
        vector<pair<char, int>> runs;
        for (int i = 0; i < (int)t.size();) {
            int j = i;
            while (j < (int)t.size() && t[j] == t[i])
                j++;
            runs.push_back({t[i], j - i});
            i = j;
        }

        int ans = count(s.begin(), s.end(), '1');

        vector<pair<int, int>> zeroRuns;
        for (int i = 0; i < (int)runs.size(); i++) {
            if (runs[i].first == '0')
                zeroRuns.push_back({runs[i].second, i});
        }

        sort(zeroRuns.rbegin(), zeroRuns.rend());

        int bestGain = 0;

        for (int i = 1; i + 1 < (int)runs.size(); i++) {
            if (runs[i].first == '1') {
                int leftZero = i - 1;
                int rightZero = i + 1;

                bestGain = max(bestGain,
                               runs[leftZero].second + runs[rightZero].second);
                for (auto [len, idx] : zeroRuns) {
                    if (idx != leftZero && idx != rightZero) {
                        bestGain = max(bestGain, len - runs[i].second);
                        break;
                    }
                }
            }
        }

        return ans + bestGain;
    }
};
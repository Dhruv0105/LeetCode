class Solution {
public:
    string smallestPalindrome(string s, long long k) {
        int n = s.size();
        vector<long long> cnt(26, 0);
        for (char c : s) cnt[c - 'a']++;

        vector<int> half(26, 0);
        int oddChar = -1;
        for (int i = 0; i < 26; i++) {
            half[i] = cnt[i] / 2;
            if (cnt[i] % 2 == 1) oddChar = i;
        }

        int halfLen = n / 2;
        const long long CAP = 2000000000LL; // generous cap, well above k's max of 1e6

        // Compute multinomial coefficient len! / (c0! c1! ... c25!) incrementally,
        // capping intermediate values (BEFORE multiplying) to avoid overflow.
        auto countPerms = [&](vector<int>& counts, int len) -> long long {
            long long result = 1;
            int remaining = len;
            for (int i = 0; i < 26; i++) {
                int c = counts[i];
                // multiply by C(remaining, c) = remaining!/(c!(remaining-c)!)
                long long comb = 1;
                for (int j = 1; j <= c; j++) {
                    comb = comb * (remaining - c + j) / j;
                    if (comb > CAP) { comb = CAP; break; }
                }
                // cap result before multiplying to avoid overflow
                if (result > CAP || comb > CAP) {
                    result = CAP;
                } else {
                    // safe to multiply: both result and comb are <= CAP (2e9),
                    // product could be up to 4e18 which fits in long long (max ~9.2e18)
                    result = result * comb;
                    if (result > CAP) result = CAP;
                }
                remaining -= c;
                if (result >= CAP) return CAP;
            }
            return result;
        };

        vector<int> curHalf = half;
        long long totalHalfPerms = countPerms(curHalf, halfLen);

        if (k > totalHalfPerms) return "";

        vector<int> remCount = half;
        string result = "";
        int remLen = halfLen;

        for (int pos = 0; pos < halfLen; pos++) {
            bool placed = false;
            for (int c = 0; c < 26 && !placed; c++) {
                if (remCount[c] == 0) continue;
                remCount[c]--;
                long long perms = countPerms(remCount, remLen - 1);
                if (k <= perms) {
                    result += ('a' + c);
                    remLen--;
                    placed = true;
                } else {
                    k -= perms;
                    remCount[c]++;
                }
            }
        }

        string full = result;
        if (oddChar != -1) full += ('a' + oddChar);
        string rev = result;
        reverse(rev.begin(), rev.end());
        full += rev;

        return full;
    }
};
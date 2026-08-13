import java.util.*;

class Solution {
    private int[] sz, pref, suf, maxLen;
    private char[] lc, rc;
    private char[] arr;
    private int n;

    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {
        n = s.length();
        arr = s.toCharArray();
        int k = queryCharacters.length();

        sz = new int[4 * n];
        pref = new int[4 * n];
        suf = new int[4 * n];
        maxLen = new int[4 * n];
        lc = new char[4 * n];
        rc = new char[4 * n];

        build(1, 0, n - 1);

        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            int idx = queryIndices[i];
            char c = queryCharacters.charAt(i);
            arr[idx] = c;
            update(1, 0, n - 1, idx, c);
            result[i] = maxLen[1];
        }
        return result;
    }

    private void build(int node, int l, int r) {
        if (l == r) {
            sz[node] = 1;
            pref[node] = 1;
            suf[node] = 1;
            maxLen[node] = 1;
            lc[node] = arr[l];
            rc[node] = arr[l];
            return;
        }
        int mid = (l + r) / 2;
        int left = 2 * node, right = 2 * node + 1;
        build(left, l, mid);
        build(right, mid + 1, r);
        merge(node, left, right);
    }

    private void update(int node, int l, int r, int idx, char c) {
        if (l == r) {
            lc[node] = c;
            rc[node] = c;
            return;
        }
        int mid = (l + r) / 2;
        int left = 2 * node, right = 2 * node + 1;
        if (idx <= mid) {
            update(left, l, mid, idx, c);
        } else {
            update(right, mid + 1, r, idx, c);
        }
        merge(node, left, right);
    }

    private void merge(int node, int left, int right) {
        sz[node] = sz[left] + sz[right];
        lc[node] = lc[left];
        rc[node] = rc[right];

        if (pref[left] == sz[left] && rc[left] == lc[right]) {
            pref[node] = sz[left] + pref[right];
        } else {
            pref[node] = pref[left];
        }

        if (suf[right] == sz[right] && lc[right] == rc[left]) {
            suf[node] = sz[right] + suf[left];
        } else {
            suf[node] = suf[right];
        }

        int crossLen = 0;
        if (rc[left] == lc[right]) {
            crossLen = suf[left] + pref[right];
        }
        maxLen[node] = Math.max(Math.max(maxLen[left], maxLen[right]), crossLen);
    }
}
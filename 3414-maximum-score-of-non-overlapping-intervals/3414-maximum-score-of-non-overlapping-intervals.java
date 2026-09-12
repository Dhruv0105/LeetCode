import java.util.*;

class Solution {
    static class Node {
        long score;
        ArrayList<Integer> list;
        Node(long score, ArrayList<Integer> list) {
            this.score = score;
            this.list = list;
        }
    }
    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();
        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++) order[i] = i;
        Arrays.sort(order, (a, b) -> 
            intervals.get(a).get(1) - intervals.get(b).get(1)
        );

        int[] end = new int[n];
        for (int i = 0; i < n; i++) {
            end[i] = intervals.get(order[i]).get(1);
        }

        Node[][] dp = new Node[n + 1][5];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= 4; j++) {
                dp[i][j] = new Node(Long.MIN_VALUE, new ArrayList<>());
            }
        }

        for (int j = 0; j <= 4; j++) {
            dp[0][j] = new Node(0, new ArrayList<>());
        }

        for (int i = 1; i <= n; i++) {
            int id = order[i - 1];
            int prev = lowerBound(end, intervals.get(id).get(0));
            for (int k = 0; k <= 4; k++) {
                dp[i][k] = better(dp[i][k], dp[i - 1][k]);
                if (k > 0 && dp[prev][k - 1].score != Long.MIN_VALUE) {
                    ArrayList<Integer> cur = new ArrayList<>(dp[prev][k - 1].list);
                    cur.add(id);
                    Collections.sort(cur);
                    Node take = new Node(
                        dp[prev][k - 1].score + intervals.get(id).get(2),
                        cur
                    );

                    dp[i][k] = better(dp[i][k], take);
                }
            }
        }

        Node ans = new Node(0, new ArrayList<>());
        for (int k = 0; k <= 4; k++) {
            ans = better(ans, dp[n][k]);
        }

        int[] res = new int[ans.list.size()];
        for (int i = 0; i < res.length; i++)
            res[i] = ans.list.get(i);

        return res;
    }

    private Node better(Node a, Node b) {
        if (a.score != b.score)
            return a.score > b.score ? a : b;
        int n = Math.min(a.list.size(), b.list.size());
        for (int i = 0; i < n; i++) {
            if (!a.list.get(i).equals(b.list.get(i)))
                return a.list.get(i) < b.list.get(i) ? a : b;
        }

        return a.list.size() <= b.list.size() ? a : b;
    }

    private int lowerBound(int[] arr, int target) {
        int l = 0, r = arr.length;
        while (l < r) {
            int m = (l + r) / 2;
            if (arr[m] >= target)
                r = m;
            else
                l = m + 1;
        }

        return l;
    }
}
class Solution {
    int[] parent;
    int[] size;

    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    public void union(int a, int b) {
        int pa = find(a);
        int pb = find(b);

        if (pa == pb) return;

        if (size[pa] < size[pb]) {
            int temp = pa;
            pa = pb;
            pb = temp;
        }

        parent[pb] = pa;
        size[pa] += size[pb];
    }

    public int[] getFactors(int n) {
        ArrayList<Integer> list = new ArrayList<>();

        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                list.add(i);

                while (n % i == 0) {
                    n /= i;
                }
            }
        }

        if (n > 1) {
            list.add(n);
        }

        int[] factors = new int[list.size()];

        for (int i = 0; i < list.size(); i++) {
            factors[i] = list.get(i);
        }

        return factors;
    }

    public int largestComponentSize(int[] nums) {
        int n = nums.length;

        parent = new int[n];
        size = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }

        HashMap<Integer, Integer> factorMap = new HashMap<>();

        for (int i = 0; i < n; i++) {
            int[] factors = getFactors(nums[i]);

            for (int factor : factors) {
                if (factorMap.containsKey(factor)) {
                    union(i, factorMap.get(factor));
                } else {
                    factorMap.put(factor, i);
                }
            }
        }

        int ans = 0;

        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, size[find(i)]);
        }

        return ans;
    }
}
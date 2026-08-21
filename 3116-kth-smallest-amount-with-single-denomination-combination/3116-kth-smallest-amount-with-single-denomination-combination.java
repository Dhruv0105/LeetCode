class Solution {
    public long findKthSmallest(int[] coins, int k) {
        long left = 1, right = 1L * coins[0] * k;

        for (int c : coins) {
            right = Math.min(right, 1L * c * k);
        }

        while (left < right) {
            long mid = left + (right - left) / 2;

            if (count(mid, coins) >= k) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    private long count(long x, int[] coins) {
        int n = coins.length;
        long total = 0;
         for (int mask = 1; mask < (1 << n); mask++) {
            long lcm = 1;
            int bits = 0;
            boolean overflow = false;

            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    bits++;

                    lcm = lcm(lcm, coins[i]);

                    if (lcm > x) {
                        overflow = true;
                        break;
                    }
                }
            }

            if (!overflow) {
                long add = x / lcm;

                if ((bits & 1) == 1) {
                    total += add;
                } else {
                    total -= add;
                }
            }
        }

        return total;
    }

    private long lcm(long a, long b) {
        return a / gcd(a, b) * b;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }
}
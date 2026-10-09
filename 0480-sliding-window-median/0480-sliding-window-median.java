import java.util.*;

class Solution {
    PriorityQueue<Integer> small =
        new PriorityQueue<>(Collections.reverseOrder());
    PriorityQueue<Integer> large = new PriorityQueue<>();
    Map<Integer, Integer> delayed = new HashMap<>();

    int smallSize = 0, largeSize = 0;

    public double[] medianSlidingWindow(int[] nums, int k) {
        small.clear();
        large.clear();
        delayed.clear();
        smallSize = 0;
        largeSize = 0;

        double[] ans = new double[nums.length - k + 1];

        for (int i = 0; i < nums.length; i++) {
            add(nums[i]);

            if (i >= k) {
                remove(nums[i - k]);
            }

            if (i >= k - 1) {
                ans[i - k + 1] = getMedian(k);
            }
        }

        return ans;
    }

    private void add(int num) {
        if (small.isEmpty() || num <= small.peek()) {
            small.offer(num);
            smallSize++;
        } else {
            large.offer(num);
            largeSize++;
        }
        balance();
    }

    private void remove(int num) {
        delayed.put(num, delayed.getOrDefault(num, 0) + 1);

        if (num <= small.peek()) {
            smallSize--;
            if (num == small.peek()) {
                prune(small);
            }
        } else {
            largeSize--;
            if (!large.isEmpty() && num == large.peek()) {
                prune(large);
            }
        }

        balance();
    }

    private void balance() {
        if (smallSize > largeSize + 1) {
            large.offer(small.poll());
            smallSize--;
            largeSize++;
            prune(small);
        } else if (smallSize < largeSize) {
            small.offer(large.poll());
            smallSize++;
            largeSize--;
            prune(large);
        }
    }

    private void prune(PriorityQueue<Integer> heap) {
        while (!heap.isEmpty()) {
            int num = heap.peek();
            int count = delayed.getOrDefault(num, 0);

            if (count == 0) break;

            heap.poll();
            if (count == 1) {
                delayed.remove(num);
            } else {
                delayed.put(num, count - 1);
            }
        }
    }

    private double getMedian(int k) {
        prune(small);
        prune(large);

        if (k % 2 == 1) {
            return small.peek();
        }

        return ((long) small.peek() + large.peek()) / 2.0;
    }
}
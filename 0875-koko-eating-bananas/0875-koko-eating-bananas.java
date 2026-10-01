class Solution {
    public int minEatingSpeed(int[] piles, int h) {
        int left = 1;
        int right = 0;
        for(int pile : piles) {
            right = Math.max(right, pile);
        }
        int result = right;
        while (left <= right) {
            int mid = left + (right - left) /2;
            if (canFinish(piles, h, mid)) {
                result = mid;
                right = mid -1;
            } else {
                left = mid+1;
            }
        }
        return result;
    }
    private boolean canFinish(int[] piles, int h, int speed) {
        int hours=0;
        for (int pile:piles) {
            hours += ((long) pile+speed-1)/speed;
            if (hours>h) {
                return false;
            }
        }
        return hours <= h;
    }
}
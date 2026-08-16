class Solution {
    public int numBusesToDestination(int[][] routes, int source, int target) {
        if (source == target) return 0;
        Map<Integer, List<Integer>> stopToBus = new HashMap<>();
        for (int i=0; i<routes.length; i++) {
            for (int stop:routes[i]) {
                stopToBus
                        .computeIfAbsent(stop, k-> new ArrayList<>()).add(i);
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        boolean[] visitedBus = new boolean[routes.length];
        Set<Integer> visitedStop = new HashSet<>();

        for (int bus : stopToBus.getOrDefault(source, new ArrayList<>())) {
            queue.offer(bus);
            visitedBus[bus] = true;
        }

        int busesTaken = 1;

        while (!queue.isEmpty()) {
            int size = queue.size();

            while (size-- > 0) {
                int bus = queue.poll();

                for (int stop : routes[bus]) {
                    if (stop == target) {
                        return busesTaken;
                    }

                    if (visitedStop.contains(stop)) continue;
                    visitedStop.add(stop);

                    for (int nextBus : stopToBus.get(stop)) {
                        if (!visitedBus[nextBus]) {
                            visitedBus[nextBus] = true;
                            queue.offer(nextBus);
                        }
                    }
                }
            }

            busesTaken++;
        }

        return -1;
    }
}
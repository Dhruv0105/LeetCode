import java.util.*;

class Solution {
    public List<String> watchedVideosByFriends(List<List<String>> watchedVideos,
                                               int[][] friends,
                                               int id,
                                               int level) {

        int n = friends.length;

        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[n];

        queue.offer(id);
        visited[id] = true;

        int currentLevel = 0;
        List<Integer> people = new ArrayList<>();

        while (!queue.isEmpty()) {
            int size = queue.size();
            currentLevel++;

            for (int i = 0; i < size; i++) {
                int person = queue.poll();

                for (int friend : friends[person]) {
                    if (!visited[friend]) {
                        visited[friend] = true;

                        if (currentLevel == level) {
                            people.add(friend);
                        } else {
                            queue.offer(friend);
                        }
                    }
                }
            }

            if (currentLevel == level) {
                break;
            }
        }

        Map<String, Integer> freq = new HashMap<>();

        for (int person : people) {
            for (String video : watchedVideos.get(person)) {
                freq.put(video, freq.getOrDefault(video, 0) + 1);
            }
        }

        List<String> result = new ArrayList<>(freq.keySet());

        Collections.sort(result, (a, b) -> {
            if (!freq.get(a).equals(freq.get(b))) {
                return freq.get(a) - freq.get(b);
            }
            return a.compareTo(b);
        });

        return result;
    }
}
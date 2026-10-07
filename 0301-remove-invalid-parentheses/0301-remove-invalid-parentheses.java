import java.util.*;

class Solution {
    public List<String> removeInvalidParentheses(String s) {
        List<String> result = new ArrayList<>();

        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.offer(s);
        visited.add(s);

        boolean found = false;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                String cur = queue.poll();

                if (isValid(cur)) {
                    result.add(cur);
                    found = true;
                }

                if (found)
                    continue;

                for (int j = 0; j < cur.length(); j++) {
                    char c = cur.charAt(j);

                    if (c != '(' && c != ')')
                        continue;

                    String next = cur.substring(0, j)
                            + cur.substring(j + 1);

                    if (visited.add(next)) {
                        queue.offer(next);
                    }
                }
            }

            if (found)
                break;
        }

        return result;
    }

    private boolean isValid(String s) {
        int balance = 0;

        for (char c : s.toCharArray()) {
            if (c == '(') {
                balance++;
            } else if (c == ')') {
                balance--;

                if (balance < 0)
                    return false;
            }
        }

        return balance == 0;
    }
}
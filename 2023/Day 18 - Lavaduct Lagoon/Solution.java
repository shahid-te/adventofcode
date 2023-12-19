import java.io.*;
import java.util.*;

class Solution {
    // Returns area that lies on vertical line `x`
    private int area(Set<Integer> carried_over_y, Map<Integer, Map<Integer, List<Integer>>> vertical, int x) {
        var all_y = new TreeSet<>(carried_over_y);
        var v = vertical.get(x);
        for (var entry: v.entrySet()) {
            all_y.add(entry.getKey());
            all_y.add(entry.getValue().get(0));
        }
        boolean in = false;         // tracks whether we're inside or outside the loop or polygon
        int prev_y = 0, res = 0;    // prev_y = last value of `y` which is inside
        var it = all_y.iterator();
        while (it.hasNext()) {
            int y = it.next();
            var value = v.get(y);
            if (value != null) {    // vertical line
                int next_y = value.get(0);
                boolean uTurn = value.get(1) == 1;
                if (uTurn) {        // similar to intersection with a point, `in` doesn't change
                    if (!in)
                        res += next_y - y + 1;
                } else {            // similar to intersection with a line, `in` alternates
                    if (in)
                        res += next_y - prev_y + 1;
                    else
                        prev_y = y;
                    in = !in;
                }
                it.next();  // skip next `y` since we've already covered it
            } else {        // interesection with a horizontal line
                if (in)
                    res += y - prev_y + 1;
                else
                    prev_y = y;
                in = !in;
            }
        }
        return res;
    }

    // Returns common vertical area sweeping from previous `x` to current
    private int height(Set<Integer> y) {
        var it = y.iterator();
        int sum = 0;
        while (it.hasNext())
            sum += - it.next() + it.next() + 1;
        return sum;
    }

    /** Returns 1 if vertical line at `i` is U-shaped:
     *  __         __
     * |             |
     * |     or      |
     * |__         __|
     */
    private int isUTurn(List<Integer> direction, int i) {
        int prev = i - 1, next = i + 1;
        if (prev < 0) prev = direction.size() - 1;
        if (next == direction.size()) next = 0;
        return direction.get(prev) != direction.get(next) ? 1 : 0;
    }

    public long solve(List<Integer> direction, List<Integer> edge) {
        // Vertical lines map: <x, <min_y, {max_y, is_this_vertical_line_u_shaped}>>
        Map<Integer, Map<Integer, List<Integer>>> vertical = new TreeMap<>();
        // For horizontal line (min_x, y) -> (max_x, y), horizontalStart = <min_x, {y}> and horizontalEnd = <max_x, {y}>
        Map<Integer, List<Integer>> horizontalStart = new HashMap<>(), horizontalEnd = new HashMap<>();
        for (int i = 0, x = 0, y = 0; i < direction.size(); i++) {
            int e = edge.get(i);
            switch (direction.get(i)) {
                case 0:
                    horizontalStart.computeIfAbsent(x, k -> new ArrayList<>()).add(y);
                    horizontalEnd.computeIfAbsent(x + e, k -> new ArrayList<>()).add(y);
                    x += e;
                    break;
                case 1:
                    vertical.computeIfAbsent(x, k -> new HashMap<>()).put(y - e, List.of(y, isUTurn(direction, i)));
                    y -= e;
                    break;
                case 2:
                    horizontalStart.computeIfAbsent(x - e, k -> new ArrayList<>()).add(y);
                    horizontalEnd.computeIfAbsent(x, k -> new ArrayList<>()).add(y);
                    x -= e;
                    break;
                case 3:
                    vertical.computeIfAbsent(x, k -> new HashMap<>()).put(y, List.of(y + e, isUTurn(direction, i)));
                    y += e;
            }
        }
        long ans = 0;
        int last_x = 0;
        Set<Integer> y = new TreeSet<>();
        for (int x: vertical.keySet()) {
            ans += (long) (x - last_x - 1) * height(y) + area(y, vertical, x);
            y.removeAll(horizontalEnd.getOrDefault(x, new ArrayList<>()));
            y.addAll(horizontalStart.getOrDefault(x, new ArrayList<>()));
            last_x = x;
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<Integer> direction = new ArrayList<>(), edge = new ArrayList<>();
        List<String> color = new ArrayList<>();
        String line = null;
        while ((line = br.readLine()) != null) {
            String[] part = line.split("\\s+");
            char d = part[0].charAt(0);
            direction.add(d == 'R' ? 0 : (d == 'D' ? 1 : (d == 'L' ? 2 : 3)));
            edge.add(Integer.valueOf(part[1]));
            color.add(part[2]);
        }
        Solution s = new Solution();
        long ans1 = s.solve(direction, edge);
        direction.clear();
        edge.clear();
        for (String c: color) {
            direction.add(c.charAt(7) - '0');
            edge.add(Integer.valueOf(c.substring(2, 7), 16));
        }
        long ans2 = s.solve(direction, edge);
        System.out.println("ans1: " + ans1 + " ans2: " + ans2);
        br.close();
    }
}
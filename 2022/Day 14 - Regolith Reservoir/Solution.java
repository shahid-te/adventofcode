import java.io.*;
import java.util.*;

class Solution {

    enum Result {
        LEAKED,
        OCCUPIED,
        ADDED
    }

    private Result drop(Map<Integer, TreeSet<Integer>> points, int x, int y, int max) {
        if (points.containsKey(x) && points.get(x).contains(y)) return Result.OCCUPIED;
        if (!points.containsKey(x) || points.get(x).higher(y) == null) {
            if (y < max) {
                add(points, x, max - 1);
                return Result.ADDED;
            }
            return y == max ? Result.OCCUPIED : Result.LEAKED;
        }
        y = points.get(x).higher(y) - 1;
        Result res;
        if ((res = drop(points, x - 1, y + 1, max)) != Result.OCCUPIED) return res;
        if ((res = drop(points, x + 1, y + 1, max)) != Result.OCCUPIED) return res;
        add(points, x, y);
        return Result.ADDED;
    }

    public int solve(Map<Integer, TreeSet<Integer>> points, int max) {
        int ans = 0;
        while (drop(points, 500, 0, max) == Result.ADDED) ans++;
        return ans;
    }

    private static void add(Map<Integer, TreeSet<Integer>> points, int x, int y) {
        points.computeIfAbsent(x, k -> new TreeSet<>()).add(y);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Map<Integer, TreeSet<Integer>> points = new HashMap<>();
        int max = 0;
        String line;
        while ((line = br.readLine()) != null) {
            String[] part = line.split(" -> ");
            String[] cur = part[0].split(",");
            int x1 = Integer.parseInt(cur[0]), y1 = Integer.parseInt(cur[1]);
            max = Math.max(max, y1);
            add(points, x1, y1);
            for (int i = 1; i < part.length; i++) {
                cur = part[i].split(",");
                int x2 = Integer.parseInt(cur[0]), y2 = Integer.parseInt(cur[1]), min_x = Math.min(x1, x2), max_x = Math.max(x1, x2), min_y = Math.min(y1, y2), max_y = Math.max(y1, y2);
                max = Math.max(max, y2);
                for (int x = min_x; x <= max_x; x++)
                    for (int y = min_y; y <= max_y; y++)
                        add(points, x, y);
                x1 = x2;
                y1 = y2;
            }
        }
        br.close();
        Map<Integer, TreeSet<Integer>> points2 = new HashMap<>();
        for (var entry: points.entrySet())
            points2.put(entry.getKey(), new TreeSet<>(entry.getValue()));
        Solution s = new Solution();
        int ans1 = s.solve(points, -1), ans2 = s.solve(points2, max + 2);
        System.out.println("ans1: " + ans1 + " ans2: " + ans2);
    }
}
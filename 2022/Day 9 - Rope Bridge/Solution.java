import java.io.*;
import java.util.*;

class Point {
    public int x, y;
    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    Point(Point p) {
        this.x = p.x;
        this.y = p.y;
    }
    @Override
    public int hashCode() {
        return 9973 * x + y;
    }
    @Override
    public boolean equals(Object o) {
        if ( o == null || !(o instanceof Point) ) return false;
        Point p = (Point) o;
        return x == p.x && y == p.y;
    }
}

class Solution {
    private static final int[][] dir = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

    private void updateFollower(Point head, Point tail) {
        int dx = Math.abs(head.x - tail.x), dy = Math.abs(head.y - tail.y);
        if (dx + dy <= 1 || (dx == 1 && dy == 1)) return;
        if (dx == 2) {
            tail.x = head.x + (head.x > tail.x ? -1 : 1);
            if (dy <= 1) {
                tail.y = head.y;
            } else {    // (±2, ±2) case only possible in part 2
                tail.y = head.y + (head.y > tail.y ? -1 : 1);
            }
        } else { // dy == 2
            tail.y = head.y + (head.y > tail.y ? -1 : 1);
            if (dx <= 1) {
                tail.x = head.x;
            } else {    // (±2, ±2) case only possible in part 2
                tail.x = head.x + (head.x > tail.x ? -1 : 1);
            }
        }
    }

    public int solve(List<int[]> moves, int L) {
        List<Point> rope = new ArrayList<>();
        for (int i = 0; i < L; i++)
            rope.add(new Point(0, 0));
        Set<Point> set = new HashSet<>();
        set.add(new Point(rope.get(L - 1)));
        for (int[] move: moves) {
            int[] d = dir[move[0]];
            for (int t = 0; t < move[1]; t++) {
                Point head = rope.get(0);
                head.x += d[0];
                head.y += d[1];
                for (int i = 1; i < L; i++)
                    updateFollower(rope.get(i - 1), rope.get(i));
                set.add(new Point(rope.get(L - 1)));
            }
        }
        return set.size();
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<int[]> list = new ArrayList<>();
        String line;
        while ( (line = br.readLine()) != null ) {
            String[] part = line.split("\\s+");
            int dir = "L".equals(part[0]) ? 0 : (("R".equals(part[0]) ? 1 : ("U".equals(part[0]) ? 2 : 3)));
            list.add(new int[] {dir, Integer.parseInt(part[1])});
        }
        Solution s = new Solution();
        int ans1 = s.solve(list, 2), ans2 = s.solve(list, 10);
        System.out.println("ans1: " + ans1 + " ans2: " + ans2);
        br.close();
    }
}
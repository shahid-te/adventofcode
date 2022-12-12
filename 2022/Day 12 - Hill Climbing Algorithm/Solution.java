import java.io.*;
import java.util.*;

class Solution {
    private static final int[] dir = {1, 0, -1, 0, 1};

    public int bfs(List<char[]> grid, List<int[]> start, int[] end) {
        int R = grid.size(), C = grid.get(0).length, dist = 0;
        boolean[][] visited = new boolean[R][C];
        Deque<int[]> Q = new ArrayDeque<>(start);
        for (int[] p: start)
            visited[p[0]][p[1]] = true;
        while(!Q.isEmpty()) {
            dist++;
            for (int size = Q.size(); size-- > 0;) {
                int[] p = Q.poll();
                for (int i = 0; i < 4; i++) {
                    int x = p[0] + dir[i], y = p[1] + dir[i + 1];
                    if (x >= 0 && x < R && y >= 0 && y < C && !visited[x][y] && grid.get(x)[y] <= grid.get(p[0])[p[1]] + 1) {
                        if (x == end[0] && y == end[1]) return dist;
                        visited[x][y] = true;
                        Q.add(new int[] {x, y});
                    }
                }
            }
        }
        return dist;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<char[]> grid = new ArrayList<>();
        int[] start = new int[2], end = new int[2];
        List<int[]> starts = new ArrayList<>();
        String line;
        for (int i = 0; (line = br.readLine()) != null; i++) {
            char[] c = line.toCharArray();
            for (int j = 0; j < c.length; j++) {
                if (c[j] == 'S') {
                    c[j] = 'a';
                    start[0] = i;
                    start[1] = j;
                }
                if (c[j] == 'a')
                    starts.add(new int[] {i , j});
                if (c[j] == 'E') {
                    c[j] = 'z';
                    end[0] = i;
                    end[1] = j;
                }
            }
            grid.add(c);
        }
        br.close();
        Solution s = new Solution();
        int ans1 = s.bfs(grid, Arrays.asList(start), end), ans2 = s.bfs(grid, starts, end);
        System.out.println("ans1: " + ans1 + " ans2: " + ans2);
    }
}
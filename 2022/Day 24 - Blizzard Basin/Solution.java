import java.io.*;
import java.util.*;

class Solution {
    private static final int UP = 1 << 0, LEFT = 1 << 1, DOWN = 1 << 2, RIGHT = 1 << 3;
    private static final int[][] dir = { {-1, 0}, {0, -1}, {1, 0}, {0, 1}, {0, 0} };

    private void print(int v) {
        if (v == -1)
            System.out.print("#");
        else if (v == 0)
            System.out.print(".");
        else if (v == UP)
            System.out.print("^");
        else if (v == DOWN)
            System.out.print("v");
        else if (v == LEFT)
            System.out.print("<");
        else if (v == RIGHT)
            System.out.print(">");
        else {
            int count = 0;
            while (v != 0) {
                v &= v - 1;
                count++;
            }
            System.out.print(count);
        }
    }

    private void print(int[][] G) {
        for (int i = 0; i < G.length; i++) {
            for (int j = 0; j < G[0].length; j++)
                print(G[i][j]);
            System.out.println();
        }
    }

    private void next(int[][] G) {
        int R = G.length, C = G[0].length;
        int[][] next = new int[R][C];
        for (int i = 1; i < R - 1; i++)
            for (int j = 1; j < C - 1; j++) {
                int y = j - 1;
                next[i][j] |= (y >= 1 ? G[i][y] : G[i][C - 2]) & RIGHT;
                y = j + 1;
                next[i][j] |= (y < C - 1 ? G[i][y] : G[i][1]) & LEFT;
                int x = i - 1;
                next[i][j] |= (x >= 1 ? G[x][j] : G[R - 2][j]) & DOWN;
                x = i + 1;
                next[i][j] |= (x < R - 1 ? G[x][j] : G[1][j]) & UP;
            }
        for (int i = 1; i < R - 1; i++)
            for (int j = 1; j < C - 1; j++)
                G[i][j] = next[i][j];
    }

    public int fastestTrip(int[][]G, int[] start, int[] end) {
        int R = G.length, C = G[0].length;
        Deque<int[]> Q = new ArrayDeque<>();
        Q.add(start);
        for (int T = 1, max = R * C * R * C; T <= max; T++) {
            next(G);
            //System.out.println("Time: " + T); print(G); System.out.println();
            boolean[][] visited = new boolean[R][C];
            for (int s = Q.size() - 1; s >= 0; s--) {
                int[] p = Q.poll();
                for (int d = 0; d < 5; d++) {
                    int x = p[0] + dir[d][0], y = p[1] + dir[d][1];
                    if (x >= 0 && x < R && y >= 0 && y < C && !visited[x][y] && G[x][y] == 0) {
                        if (x == end[0] && y == end[1]) return T;
                        visited[x][y] = true;
                        Q.add(new int[] {x, y});
                    }
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<String> lines = new ArrayList<>();
        for (String line; (line = br.readLine()) != null;)
            lines.add(line);
        br.close();
        int R = lines.size(), C = lines.get(0).length();
        int[][] G = new int[R][C];
        for (int i = 0; i < R; i++)
            for (int j = 0; j < C; j++) {
                char c = lines.get(i).charAt(j);
                G[i][j] = c == '#' ? -1 : (c == '.' ? 0 : (c == '^' ? UP : (c == '<' ? LEFT : (c == 'v' ? DOWN : RIGHT))));
            }
        int[] start = new int[2], end = new int[2];
        for (int i = 0; i < C; i++) {
            if (G[0][i] == 0)
                start[1] = i;
            if (G[R - 1][i] == 0) {
                end[0] = R - 1;
                end[1] = i;
            }
        }
        Solution s = new Solution();
        int ans1 = s.fastestTrip(G, start, end), ans2 = ans1 + s.fastestTrip(G, end, start) + s.fastestTrip(G, start, end);
        System.out.println("ans1: " + ans1 + " ans2: " + ans2);
    }
}
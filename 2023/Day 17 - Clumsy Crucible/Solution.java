import java.io.*;
import java.util.*;

class Solution {
    private final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;
    private final int INF = 1_000_000_000;
    private final int[][] dir = { {0, -1}, {0, 1}, {-1, 0}, {1, 0} };

    private boolean valid(int[][] G, int i, int j) {
        return i >= 0 && i < G.length && j >= 0 && j < G[0].length;
    }

    private void relax(int[][] G, int[][][] dist, PriorityQueue<int[]> pq, int x, int y, int d, int curDist, int minSteps, int maxSteps) {
        int edge = 0;
        for (int step = 1; step <= maxSteps; step++) {
            x += dir[d][0];
            y += dir[d][1];
            if (valid(G, x, y)) {
                edge += G[x][y];
                if (step >= minSteps) {
                    int alt = curDist + edge;
                    if (alt < dist[x][y][d]) {
                        dist[x][y][d] = alt;
                        pq.add(new int[] {x, y, d, alt});
                    }
                }
            }
        }
    }

    public int solve(int[][] G, int minSteps, int maxSteps) {
        int R = G.length, C = G[0].length;
        int[][][] dist = new int[R][C][4];
        for (int i = 0; i < R; i++)
            for (int j = 0; j < C; j++)
                for (int d = 0; d < 4; d++)
                    dist[i][j][d] = INF;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[3] - b[3]);
        relax(G, dist, pq, 0, 0, RIGHT, 0, minSteps, maxSteps);
        relax(G, dist, pq, 0, 0, DOWN, 0, minSteps, maxSteps);
        while (!pq.isEmpty()) {
            int[] t = pq.poll();
            int i = t[0], j = t[1], d = t[2], curDist = t[3];
            if (i == R - 1 && j == C - 1) return curDist;
            if (d == LEFT || d == RIGHT) {
                relax(G, dist, pq, i, j, UP, curDist, minSteps, maxSteps);
                relax(G, dist, pq, i, j, DOWN, curDist, minSteps, maxSteps);
            } else {
                relax(G, dist, pq, i, j, LEFT, curDist, minSteps, maxSteps);
                relax(G, dist, pq, i, j, RIGHT, curDist, minSteps, maxSteps);
            }
        }
        return -1;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<String> list = new ArrayList<>();
        String line = null;
        while ((line = br.readLine()) != null)
            list.add(line);
        int[][] G = new int[list.size()][];
        for (int i = 0; i < G.length; i++) {
            G[i] = new int[list.get(i).length()];
            for (int j = 0; j < G[i].length; j++)
                G[i][j] = list.get(i).charAt(j) - '0';
        }
        Solution s = new Solution();
        int ans1 = s.solve(G, 1, 3), ans2 = s.solve(G, 4, 10);
        System.out.println("ans1: " + ans1 + " ans2: " + ans2);
        br.close();
    }
}
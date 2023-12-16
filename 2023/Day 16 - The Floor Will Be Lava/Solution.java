import java.io.*;
import java.util.*;

class Solution {
    private final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;

    private void dfs(char[][] G, int i, int j, int d, boolean[][][] visited) {
        if (i < 0 || i >= G.length || j < 0 || j >= G[i].length || visited[i][j][d]) return;
        visited[i][j][d] = true;
        switch (d) {
            case LEFT:
                if (G[i][j] == '.' || G[i][j] == '-') {
                    dfs(G, i, j - 1, LEFT, visited);
                } else if (G[i][j] == '/') {
                    dfs(G, i + 1, j, DOWN, visited);
                } else if (G[i][j] == '\\') {
                    dfs(G, i - 1, j, UP, visited);
                } else if (G[i][j] == '|') {
                    dfs(G, i - 1, j, UP, visited);
                    dfs(G, i + 1, j, DOWN, visited);
                }
                break;
            case RIGHT:
                if (G[i][j] == '.' || G[i][j] == '-') {
                    dfs(G, i, j + 1, RIGHT, visited);
                } else if (G[i][j] == '/') {
                    dfs(G, i - 1, j, UP, visited);
                } else if (G[i][j] == '\\') {
                    dfs(G, i + 1, j, DOWN, visited);
                } else if (G[i][j] == '|') {
                    dfs(G, i - 1, j, UP, visited);
                    dfs(G, i + 1, j, DOWN, visited);
                }
                break;
            case UP:
                if (G[i][j] == '.' || G[i][j] == '|') {
                    dfs(G, i - 1, j, UP, visited);
                } else if (G[i][j] == '/') {
                    dfs(G, i, j + 1, RIGHT, visited);
                } else if (G[i][j] == '\\') {
                    dfs(G, i, j - 1, LEFT, visited);
                } else if (G[i][j] == '-') {
                    dfs(G, i, j - 1, LEFT, visited);
                    dfs(G, i, j + 1, RIGHT, visited);
                }
                break;
            case DOWN:
                if (G[i][j] == '.' || G[i][j] == '|') {
                    dfs(G, i + 1, j, DOWN, visited);
                } else if (G[i][j] == '/') {
                    dfs(G, i, j - 1, LEFT, visited);
                } else if (G[i][j] == '\\') {
                    dfs(G, i, j + 1, RIGHT, visited);
                } else if (G[i][j] == '-') {
                    dfs(G, i, j - 1, LEFT, visited);
                    dfs(G, i, j + 1, RIGHT, visited);
                }
        }
    }

    private int solve(char[][] G, int start_i, int start_j, int dir) {
        int r = G.length, c = G[0].length;
        boolean[][][] visited = new boolean[r][c][4];
        dfs(G, start_i, start_j, dir, visited);
        int count = 0;
        for (int i = 0; i < r; i++)
            for (int j = 0; j < c; j++)
                for (int d = 0; d < 4; d++)
                    if (visited[i][j][d]) {
                        count++;
                        break;
                    }
        return count;
    }

    public int solvePart1(char[][] G) {
        return solve(G, 0, 0, RIGHT);
    }

    public int solvePart2(char[][] G) {
        int ans = 0;
        for (int i = 0; i < G.length; i++)
            for (int j = 0; j < G[i].length; j++)
                for (int d = 0; d < 4; d++)
                    ans = Math.max(ans, solve(G, i, j, d));
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<String> list = new ArrayList<>();
        String line = null;
        while ((line = br.readLine()) != null)
            list.add(line);
        char[][] G = new char[list.size()][];
        for (int i = 0; i < G.length; i++)
            G[i] = list.get(i).toCharArray();
        Solution s = new Solution();
        int ans1 = s.solvePart1(G), ans2 = s.solvePart2(G);
        System.out.println("ans1: " + ans1 + " ans2: " + ans2);
        br.close();
    }
}
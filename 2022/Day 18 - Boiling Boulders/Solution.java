import java.io.*;
import java.util.*;

class Solution {
    private static final int[][] dir = {{-1, 0, 0}, {1, 0, 0}, {0, -1, 0}, {0, 1, 0}, {0, 0, -1}, {0, 0, 1}};

    public int solvePart1(List<int[]> cubes) {
        Set<List<Integer>> set = new HashSet<>();
        for (int[] p: cubes)
            set.add(Arrays.asList(p[0], p[1], p[2]));
        int ans = 6 * cubes.size();
        for (int[] p: cubes) {
            if (set.contains(Arrays.asList(p[0] + 1, p[1], p[2]))) ans -= 2;
            if (set.contains(Arrays.asList(p[0], p[1] + 1, p[2]))) ans -= 2;
            if (set.contains(Arrays.asList(p[0], p[1], p[2] + 1))) ans -= 2;
        }
        return ans;
    }

    private int dfs(int[][][] g, int x, int y, int z) {
        if (x < 0 || x >= g.length || y < 0 || y >= g[0].length || z < 0 || z >= g[0][0].length || g[x][y][z] == 2) return 0;
        if (g[x][y][z] == 1) return 1;
        g[x][y][z] = 2;
        int ans = 0;
        for (int[] d: dir)
            ans += dfs(g, x + d[0], y + d[1], z + d[2]);
        return ans;
    }

    public int solvePart2(List<int[]> cubes) {
        int X = 0, Y = 0, Z = 0;
        for (int[] p: cubes) {
            X = Math.max(X, p[0]);
            Y = Math.max(Y, p[1]);
            Z = Math.max(Z, p[2]);
        }
        int[][][] g = new int[X + 3][Y + 3][Z + 3]; // 0: unvisited, 1: blocked, 2: visited
        for (int[] p: cubes)
            g[p[0] + 1][p[1] + 1][p[2] + 1] = 1;
        return dfs(g, 0, 0, 0);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<int[]> cubes = new ArrayList<>();
        String line = null;
        while ((line = br.readLine()) != null) {
            String[] part = line.split(",");
            cubes.add(new int[] {Integer.parseInt(part[0]), Integer.parseInt(part[1]), Integer.parseInt(part[2])});
        }
        br.close();
        Solution s = new Solution();
        int ans1 = s.solvePart1(cubes), ans2 = s.solvePart2(cubes);
        System.out.println("ans1: " + ans1 + " ans2: " + ans2);
    }
}
import java.io.*;
import java.util.*;

class Solution {
    private static final int INFINITY = Integer.MAX_VALUE / 2;

    private int[] val, time;
    private int[][] G;
    private int N, best, best2;

    public Solution(List<Integer> nodes, List<List<Integer>> adjList, int start) {
        int n = nodes.size();
        int[][] dist = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], INFINITY);
            dist[i][i] = 0;
            for (int j: adjList.get(i))
                dist[i][j] = 1;
        }
        for (int k = 0; k < n; k++)
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    if (dist[i][j] > dist[i][k] + dist[k][j])
                        dist[i][j] = dist[i][k] + dist[k][j];
        boolean[] remove = new boolean[n];
        this.N = n;
        for (int i = 0; i < n; i++)
            if (nodes.get(i) == 0 || dist[start][i] == INFINITY) {
                remove[i] = true;
                N--;
            }
        this.val = new int[N];
        this.time = new int[N];
        for (int i = 0, i1 = 0; i < n; i++)
            if (!remove[i]) {
                val[i1] = nodes.get(i);
                time[i1] = dist[start][i];
                i1++;
            }
        this.G = new int[N][N];
        for (int i = 0, i1 = 0; i < n; i++) {
            if (remove[i]) continue;
            for (int j = 0, j1 = 0; j < n; j++)
                if (!remove[j])
                    G[i1][j1++] = dist[i][j];
            i1++;
        }
    }

    // Branch and bound approach. Explore a path only if there is potential for a better solution
    private void boundedSearch(int u, int t, int cur, boolean[] visited, boolean[] done) {
        if (--t <= 0) return;
        visited[u] = true;
        cur += done != null && done[u] ? 0 : val[u] * t;
        best = Math.max(best, cur);
        int remaining = 0, min_time = INFINITY;
        for (int i = 0; i < N; i++)
            if (!visited[i] && G[u][i] != INFINITY) {
                remaining += done != null && done[i] ? 0 : val[i];
                min_time = Math.min(min_time, G[u][i]);
            }
        if (cur + remaining * (t - min_time) > best)
            for (int i = 0; i < N; i++)
                if (!visited[i] && G[u][i] != INFINITY)
                    boundedSearch(i, t - G[u][i], cur, visited, done);
        visited[u] = false;
    }

    private void search(int u, int t, int cur, boolean[] visited, boolean[] done, int T) {
        if (--t <= 0) return;
        done[u] = true;
        cur += val[u] * t;
        best = 0;
        for (int i = 0; i < N; i++)
            boundedSearch(i, T - time[i], 0, visited, done);
        best2 = Math.max(best2, cur + best);
        for (int i = 0; i < N; i++)
            if (!done[i] && G[u][i] != INFINITY)
                search(i, t - G[u][i], cur, visited, done, T);
        done[u] = false;
    }

    public int solvePart1(int T) {
        best = 0;
        boolean[] visited = new boolean[N];
        for (int i = 0; i < N; i++)
            boundedSearch(i, T - time[i], 0, visited, null);
        return best;
    }

    public int solvePart2(int T) {
        best2 = 0;
        boolean[] visited = new boolean[N], done = new boolean[N];
        for (int i = 0; i < N; i++)
            search(i, T - time[i], 0, visited, done, T);
        return best2;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Map<String, Integer> map = new HashMap<>();
        List<Integer> nodes = new ArrayList<>();
        List<List<Integer>> adjList = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            String[] part = line.split("(; )|(, )|( )");
            if (!map.containsKey(part[1])) {
                map.put(part[1], map.size());
                nodes.add(0);
                adjList.add(new ArrayList<>());
            }
            int u = map.get(part[1]);
            nodes.set(u, Integer.parseInt(part[4].substring(5)));
            for (int i = 9; i < part.length; i++) {
                if (!map.containsKey(part[i])) {
                    map.put(part[i], map.size());
                    nodes.add(0);
                    adjList.add(new ArrayList<>());
                }
                adjList.get(u).add(map.get(part[i]));
            }
        }
        br.close();
        Solution s = new Solution(nodes, adjList, map.get("AA"));
        int ans1 = s.solvePart1(30), ans2 = s.solvePart2(26);
        System.out.println("ans1: " + ans1 + " ans2: " + ans2);
    }
}
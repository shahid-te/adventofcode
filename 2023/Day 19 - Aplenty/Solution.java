import java.io.*;
import java.util.*;

class Solution {
    private boolean isConditionTrue(String condition, int[] a) {
        if ("".equals(condition)) return true;
        int i = "xmas".indexOf(condition.charAt(0));
        int num = Integer.parseInt(condition.substring(2));
        return condition.charAt(1) == '>' ? (a[i] > num) : (a[i] < num);
    }

    private String dfs(Map<String, List<List<String>>> G, String node, int[] a, Set<String> visited) {
        if ("R".equals(node) || "A".equals(node) || !visited.add(node)) return node;
        for (List<String> edge: G.get(node))
            if (isConditionTrue(edge.get(0), a))
                return dfs(G, edge.get(1), a, visited);
        return node;
    }

    public int solvePart1(Map<String, List<List<String>>> G, List<int[]> A) {
        int ans = 0;
        for (int[] a: A) {
            String end = dfs(G, "in", a, new HashSet<>());
            if ("A".equals(end))
                ans += a[0] + a[1] + a[2] + a[3];
        }
        return ans;
    }

    private long combinations(int[][] A) {
        long ans = 1;
        for (int i = 0; i < 4; i++) {
            if (A[i][0] > A[i][1]) return 0;
            ans *= A[i][1] - A[i][0] + 1;
        }
        return ans;
    }

    private int[][] applyCondition(String condition, int[][] A, boolean opposite) {
        int[][] B = new int[4][2];
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 2; j++)
                B[i][j] = A[i][j];
        if ("".equals(condition)) return B;
        int i = "xmas".indexOf(condition.charAt(0));
        int num = Integer.parseInt(condition.substring(2));
        if (condition.charAt(1) == '>') {
            if (opposite)
                B[i][1] = Math.min(B[i][1], num);
            else
                B[i][0] = Math.max(B[i][0], num + 1);
        } else {
            if (opposite)
                B[i][0] = Math.max(B[i][0], num);
            else
                B[i][1] = Math.min(B[i][1], num - 1);
        }
        return B;
    }

    private long dfs(Map<String, List<List<String>>> G, String node, int[][] A, Set<String> visited) {
        long res = combinations(A);
        if ("R".equals(node) || res == 0) return 0;
        if ("A".equals(node)) return res;
        if (visited.contains(node)) return 0;
        visited.add(node);
        res = 0;
        int[][] B = new int[4][2];
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 2; j++)
                B[i][j] = A[i][j];
        for (List<String> edge: G.get(node)) {
            res += dfs(G, edge.get(1), applyCondition(edge.get(0), B, false), visited);
            B = applyCondition(edge.get(0), B, true);
        }
        visited.remove(node);
        return res;
    }

    public long solvePart2(Map<String, List<List<String>>> G) {
        int[][] A = new int[4][];
        for (int i = 0; i < 4; i++)
            A[i] = new int[] {1, 4000};
        return dfs(G, "in", A, new HashSet<>());
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Map<String, List<List<String>>> G = new HashMap<>();
        String line = null;
        while (!"".equals(line = br.readLine())) {
            int p = line.indexOf("{");
            String node = line.substring(0, p);
            String[] part = line.substring(p + 1, line.length() - 1).split(",");
            List<List<String>> next = new ArrayList<>();
            for (int i = 0; i < part.length - 1; i++) {
                String[] s = part[i].split(":");
                next.add(List.of(s[0], s[1]));
            }
            next.add(List.of("", part[part.length - 1]));
            G.put(node, next);
        }
        List<int[]> A = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            String[] part = line.split(",");
            A.add(new int[] {
                Integer.valueOf(part[0].substring(3)),
                Integer.valueOf(part[1].substring(2)),
                Integer.valueOf(part[2].substring(2)),
                Integer.valueOf(part[3].substring(2, part[3].length() - 1))
            });
        }
        Solution s = new Solution();
        int ans1 = s.solvePart1(G, A);
        long ans2 = s.solvePart2(G);
        System.out.println("ans1: " + ans1 + " ans2: " + ans2);
        br.close();
    }
}
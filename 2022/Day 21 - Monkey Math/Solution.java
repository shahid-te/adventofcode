import java.io.*;
import java.util.*;

class Solution {
    private long dfs(int u, long[] value, boolean[] done, int[][] operation) {
        if (done[u]) return value[u];
        long r1 = dfs(operation[u][0], value, done, operation), r2 = dfs(operation[u][2], value, done, operation);
        int op = operation[u][1];
        done[u] = true;
        value[u] = op == 0 ? r1 + r2 : (op == 1 ? r1 - r2 : (op == 2 ? r1 * r2 : r1 / r2));
        return value[u];
    }

    private String dfs1(int u, String[] value, boolean[] done, int[][] operation, int node_x) {
        if (u == node_x) return "x";
        if (done[u]) return value[u];
        String r1 = dfs1(operation[u][0], value, done, operation, node_x), r2 = dfs1(operation[u][2], value, done, operation, node_x);
        int op = operation[u][1];
        done[u] = true;
        if (r1.indexOf("x") == -1 && r2.indexOf("x") == -1) {
            long a = Long.parseLong(r1), b = Long.parseLong(r2);
            value[u] = "" + (op == 0 ? a + b : (op == 1 ? a - b : (op == 2 ? a * b : a / b)));
        } else {
            value[u] = "(" + (op == 0 ? r1 + "+" + r2 : (op == 1 ? r1 + "-" + r2 : (op == 2 ? r1 + "*" + r2 : r1 + "/" + r2))) + ")";
        }
        return value[u];
    }

    public String solvePart2(int u, String[] value, boolean[] done, int[][] operation, int node_x) {
        return dfs1(operation[u][0], value, done, operation, node_x) + " = " + dfs1(operation[u][2], value, done, operation, node_x);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<String> lines = new ArrayList<>();
        for (String line; (line = br.readLine()) != null;)
            lines.add(line);
        br.close();
        int n = lines.size();
        Map<String, Integer> index = new HashMap<>();
        int[][] operation = new int[n][];
        boolean[] done = new boolean[n], done2 = new boolean[n];
        long[] value = new long[n];
        String[] exp = new String[n];
        for (String line: lines) {
            String[] part = line.split("(: )|( )");
            int i = index.computeIfAbsent(part[0], k -> index.size());
            if (part.length == 2) {
                done2[i] = done[i] = true;
                value[i] = Long.parseLong(part[1]);
                exp[i] = "" + value[i];
            } else {
                int x = index.computeIfAbsent(part[1], k -> index.size()), y = index.computeIfAbsent(part[3], k -> index.size());
                operation[i] = new int[] { x, "+-*/".indexOf(part[2]), y };
            }
        }
        Solution s = new Solution();
        long ans1 = s.dfs(index.get("root"), value, done, operation);
        String ans2 = s.solvePart2(index.get("root"), exp, done2, operation, index.get("humn"));
        System.out.println("ans1: " + ans1 + " ans2: (solve equation for x)\n" + ans2);
    }
}
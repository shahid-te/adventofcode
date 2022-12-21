import java.io.*;
import java.util.*;

class Solution {
    private String dfs(int u, String[] value, boolean[] done, int[][] operation, int node_x) {
        if (u == node_x) return "x";
        if (operation[u] == null || done[u]) return value[u];
        String x = dfs(operation[u][0], value, done, operation, node_x), y = dfs(operation[u][2], value, done, operation, node_x);
        int op = operation[u][1];
        done[u] = true;
        if (x.indexOf("x") == -1 && y.indexOf("x") == -1) {
            long a = Long.parseLong(x), b = Long.parseLong(y);
            return value[u] = "" + (op == 0 ? a + b : (op == 1 ? a - b : (op == 2 ? a * b : a / b)));
        }
        return value[u] = "(" + (op == 0 ? x + "+" + y : (op == 1 ? x + "-" + y : (op == 2 ? x + "*" + y : x + "/" + y))) + ")";
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
        String[] value = new String[n];
        for (String line: lines) {
            String[] part = line.split("(: )|( )");
            int i = index.computeIfAbsent(part[0], k -> index.size());
            if (part.length == 2) {
                value[i] = part[1];
            } else {
                int x = index.computeIfAbsent(part[1], k -> index.size()), y = index.computeIfAbsent(part[3], k -> index.size());
                operation[i] = new int[] { x, "+-*/".indexOf(part[2]), y };
            }
        }
        Solution s = new Solution();
        int u = index.get("root"), node_x = index.get("humn");
        String ans1 = s.dfs(u, value, new boolean[n], operation, -1), ans2 = s.dfs(operation[u][0], value, new boolean[n], operation, node_x) + " = " + s.dfs(operation[u][2], value, new boolean[n], operation, node_x);
        System.out.println("ans1: " + ans1 + " ans2: (solve equation for x)\n" + ans2);
    }
}
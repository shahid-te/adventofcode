import java.io.*;
import java.util.*;

class Solution {
    private long gcd(long a, long b) {
        while (b != 0) {
            long c = a % b;
            a = b;
            b = c;
        }
        return a;
    }

    private long lcm(List<Integer> A) {
        long res = A.get(0);
        for (int i = 1; i < A.size(); i++)
            res = res * A.get(i) / gcd(res, A.get(i));
        return res;
    }

    public int solvePart1(int[] dir, Map<String, Integer> V, Map<Integer, int[]> E) {
        if (!V.containsKey("AAA") || !V.containsKey("ZZZ")) return -1;
        int ans = 0, cur = V.get("AAA"), end = V.get("ZZZ");
        for (int i = 0; cur != end; ans++) {
            cur = E.get(cur)[dir[i++]];
            if (i == dir.length) i = 0;
        }
        return ans;
    }

    public long solvePart2(int[] dir, Map<String, Integer> V, Map<Integer, int[]> E) {
        List<Integer> start = new ArrayList<>();
        Set<Integer> end = new HashSet<>();
        for (Map.Entry<String, Integer> entry: V.entrySet()) {
            if (entry.getKey().charAt(2) == 'A')
                start.add(entry.getValue());
            if (entry.getKey().charAt(2) == 'Z')
                end.add(entry.getValue());
        }
        List<Integer> cycles = new ArrayList<>();
        for (int cur: start) {
            int time = 0;
            boolean canReach = false;
            for (int i = 0; i < V.size(); i++) {
                for (int d: dir) {
                    cur = E.get(cur)[d];
                    time++;
                    if (end.contains(cur)) {
                        canReach = true;
                        break;
                    }
                }
                if (canReach) break;
            }
            if (!canReach) return -1;
            cycles.add(time);
        }
        return lcm(cycles);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        int[] dir = new int[line.length()];
        for (int i = 0; i < dir.length; i++)
            dir[i] = line.charAt(i) == 'L' ? 0 : 1;
        br.readLine();
        Map<String, Integer> V = new HashMap<>();
        Map<Integer, int[]> E = new HashMap<>();
        while ((line = br.readLine()) != null) {
            String[] part = line.split("\\s+");
            String a = part[0], b = part[2].substring(1, part[2].length() - 1), c = part[3].substring(0, part[3].length() - 1);
            int a1 = V.getOrDefault(a, V.size());
            V.put(a, a1);
            int b1 = V.getOrDefault(b, V.size());
            V.put(b, b1);
            int c1 = V.getOrDefault(c, V.size());
            V.put(c, c1);
            E.put(a1, new int[] {b1, c1});
        }
        Solution s = new Solution();
        int ans1 = s.solvePart1(dir, V, E);
        long ans2 = s.solvePart2(dir, V, E);
        System.out.println("ans1: " + ans1 + " ans2: " + ans2);
        br.close();
    }
}
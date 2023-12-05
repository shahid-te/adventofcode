import java.io.*;
import java.util.*;

class Solution {
    public long solvePart1(List<Long> A, List<List<long[]>> maps) {
        long ans = Long.MAX_VALUE;
        for (long a: A) {
            for (List<long[]> map: maps)
                for (long[] m: map)
                    if (a >= m[0] && a <= m[1]) {
                        a = a - m[0] + m[2];
                        break;
                    }
            ans = Math.min(ans, a);
        }
        return ans;
    }

    public long solvePart2(List<Long> A, List<List<long[]>> maps) {
        for (List<long[]> map: maps) {
            Collections.sort(map, (a, b) -> Long.compare(a[0], b[0]));
            List<Long> next = new ArrayList<>();
            for (int i = 0; i < A.size(); i += 2) {
                long L = A.get(i), R = L + A.get(i + 1) - 1;
                // Check intersection of [L, R] among all [m[0], m[1]] intervals (sorted) and map accordingly
                for (long[] m: map) {
                    long start = Math.max(L, m[0]), end = Math.min(R, m[1]);
                    if (start <= end) {     // [start, end] is common
                        // [L, start - 1] => map as is
                        if (L <= start - 1) {
                            next.add(L);
                            next.add(start - L);
                        }
                        // [start, end] => map using current mapping
                        next.add(start - m[0] + m[2]);
                        next.add(end - start + 1);
                        // [end + 1, R] remains
                        L = end + 1;
                        if (L > R) break;
                    } else if (m[0] > R) {     // no intersection and [L, R] lies before [m[0], m[1]]
                        // [L, R] => map as is
                        next.add(L);
                        next.add(R - L + 1);
                        break;
                    }
                }
                // Leftover interval => map as is
                if (L <= R) {
                    next.add(L);
                    next.add(R - L + 1);
                }
            }
            A = next;
        }
        long ans = Long.MAX_VALUE;
        for (int i = 0; i < A.size(); i += 2)
            ans = Math.min(ans, A.get(i));
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        String[] part = line.split("\\s+");
        List<Long> A = new ArrayList<>();
        for (int i = 1; i < part.length; i++)
            A.add(Long.valueOf(part[i]));
        br.readLine();
        List<List<long[]>> maps = new ArrayList<>();
        while (br.readLine() != null) {
            List<long[]> map = new ArrayList<>();
            while ((line = br.readLine()) != null && !"".equals(line)) {
                part = line.split("\\s+");
                long dest = Long.parseLong(part[0]), src = Long.parseLong(part[1]), range = Long.parseLong(part[2]);
                map.add(new long[] {src, src + range - 1, dest});
            }
            maps.add(map);
        }
        Solution s = new Solution();
        long ans1 = s.solvePart1(A, maps), ans2 = s.solvePart2(A, maps);
        System.out.println("ans1: " + ans1 + " ans2: " + ans2);
        br.close();
    }
}
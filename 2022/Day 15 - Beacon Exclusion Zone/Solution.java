import java.io.*;
import java.util.*;

class Solution {
    public int solvePart1(List<int[]> source, List<int[]> pos, int Y) {
        List<int[]> range = new ArrayList<>();
        for (int i = 0; i < source.size(); i++) {
            int x = source.get(i)[0], y = source.get(i)[1], dist = Math.abs(pos.get(i)[0] - x) + Math.abs(pos.get(i)[1] - y), t = dist - Math.abs(y - Y);
            if (t >= 0) {
                int l = x - t, r = x + t;
                if (pos.get(i)[1] == Y) {
                    if (pos.get(i)[0] == l) l++;
                    if (pos.get(i)[0] == r) r--;
                }
                if (l <= r)
                    range.add(new int[] {l, r});
            }
        }
        if (range.isEmpty()) return 0;
        Collections.sort(range, (a, b) -> a[0] == b[0] ? (a[1] - b[1]) : (a[0] - b[0]));
        int ans = 0, l = range.get(0)[0], r = range.get(0)[1];
        for (int i = 1; i < range.size(); i++) {
            if (range.get(i)[0] <= r) {
                r = Math.max(r, range.get(i)[1]);
            } else {
                ans += r - l + 1;
                l = range.get(i)[0];
                r = range.get(i)[1];
            }
        }
        return ans += r - l + 1;
    }

    public long solvePart2(List<int[]> source, List<int[]> pos, int N) {
        for (int Y = 0; Y <= N; Y++) {
            List<int[]> range = new ArrayList<>();
            for (int i = 0; i < source.size(); i++) {
                int x = source.get(i)[0], y = source.get(i)[1], dist = Math.abs(pos.get(i)[0] - x) + Math.abs(pos.get(i)[1] - y), t = dist - Math.abs(y - Y);
                if (t >= 0) {
                    int l = x - t, r = x + t;
                    if (l <= N && r >= 0) {
                        l = Math.max(0, l);
                        r = Math.min(N, r);
                        range.add(new int[] {l, r});
                    }
                }
            }
            if (range.isEmpty()) return Y; //4000000 * 0 + Y;
            Collections.sort(range, (a, b) -> a[0] == b[0] ? (a[1] - b[1]) : (a[0] - b[0]));
            if (range.get(0)[0] != 0) return Y;   //4000000 * 0 + Y;
            int ans = 0, X = range.get(0)[1] + 1;
            for (int i = 1; i < range.size(); i++) {
                if (range.get(i)[0] > X)
                    return 4000000L * X + Y;
                X = Math.max(X, range.get(i)[1] + 1);
            }
            if (X <= N) return 4000000L * X + Y;
        }
        return -1;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<int[]> source = new ArrayList<>(), pos = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            String[] part = line.split(": ");
            String[] p = part[0].split(", ");
            int x = Integer.parseInt(p[0].substring(12)), y = Integer.parseInt(p[1].substring(2));
            source.add(new int[] {x, y});
            p = part[1].split(", ");
            x = Integer.parseInt(p[0].substring(23)); y = Integer.parseInt(p[1].substring(2));
            pos.add(new int[] {x, y});
        }
        br.close();
        Solution s = new Solution();
        //int Y = 10, N = 20; // sample input
        int Y = 2000000, N = 4000000;
        int ans1 = s.solvePart1(source, pos, Y);
        long ans2 = s.solvePart2(source, pos, N);
        System.out.println("ans1: " + ans1 + " ans2: " + ans2);
    }
}
import java.io.*;
import java.util.*;

class Solution {
    public int solvePart1(List<Integer> time, List<Integer> dist) {
        int ans = 1;
        for (int i = 0; i < time.size(); i++) {
            int c = 0;
            for (int j = 1; j < time.get(i); j++)
                if (j * (time.get(i) - j) > dist.get(i))
                    c++;
            ans *= c;
        }
        return ans;
    }

    private long readAsOne(List<Integer> A) {
        StringBuilder s = new StringBuilder();
        for (int a: A) s.append(a);
        return Long.parseLong(s.toString());
    }

    public int solvePart2(List<Integer> time, List<Integer> dist) {
        long T = readAsOne(time), D = readAsOne(dist);
        // Solve: x * (T - x) > D
        // => x lies in ((T - sqrt(T^2 - 4D)) / 2, (T + sqrt(T^2 - 4D)) / 2)
        long disc = T * T - 4 * D;
        if (disc < 0) return 0;
        double s = Math.sqrt(disc);
        int x1 = (int) Math.ceil((T - s) / 2), x2 = (int) Math.floor((T + s) / 2);
        long sq = (long) s;
        if (sq * sq == disc) {
            x1 = (int) ((T - sq) / 2) + 1;
            x2 = (int) ((T + sq) / 2) - 1;
        }
        return x2 - x1 + 1;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] part = br.readLine().split("\\s+");
        List<Integer> time = new ArrayList<>();
        for (int i = 1; i < part.length; i++)
            time.add(Integer.valueOf(part[i]));
        part = br.readLine().split("\\s+");
        List<Integer> dist = new ArrayList<>();
        for (int i = 1; i < part.length; i++)
            dist.add(Integer.valueOf(part[i]));
        Solution s = new Solution();
        int ans1 = s.solvePart1(time, dist), ans2 = s.solvePart2(time, dist);
        System.out.println("ans1: " + ans1 + " ans2: " + ans2);
        br.close();
    }
}
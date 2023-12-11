import java.io.*;
import java.util.*;

class Solution {
    public long solve(List<String> A, long factor) {
        boolean[] row = new boolean[A.size()], col = new boolean[A.get(0).length()];
        List<int[]> P = new ArrayList<>();
        for (int i = 0; i < A.size(); i++)
            for (int j = 0; j < A.get(0).length(); j++)
                if (A.get(i).charAt(j) == '#') {
                    row[i] = true;
                    col[j] = true;
                    P.add(new int[] {i, j});
                }
        long ans = 0;
        for (int i = 0; i < P.size(); i++)
            for (int j = i + 1; j < P.size(); j++) {
                int count = 0, min = Math.min(P.get(i)[0], P.get(j)[0]), max = Math.max(P.get(i)[0], P.get(j)[0]);
                for (int t = min + 1; t < max; t++)
                    if (!row[t])
                        count++;
                ans += max - min - count + count * factor;
                count = 0; min = Math.min(P.get(i)[1], P.get(j)[1]); max = Math.max(P.get(i)[1], P.get(j)[1]);
                for (int t = min + 1; t < max; t++)
                    if (!col[t])
                        count++;
                ans += max - min - count + count * factor;
            }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<String> list = new ArrayList<>();
        String line = null;
        while ((line = br.readLine()) != null)
            list.add(line);
        Solution s = new Solution();
        long ans1 = s.solve(list, 2), ans2 = s.solve(list, 1000000);
        System.out.println("ans1: " + ans1 + " ans2: " + ans2);
        br.close();
    }
}
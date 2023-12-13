import java.io.*;
import java.util.*;

class Solution {
    private int reflection(List<String> G, int mismatch) {
        int R = G.size(), C = G.get(0).length();
        for (int i = 0; i < R - 1; i++) {
            boolean ok = true;
            int count = 0;
            for (int a = i, b = i + 1; a >= 0 && b < R; a--, b++) {
                for (int j = 0; j < C; j++)
                    if (G.get(a).charAt(j) != G.get(b).charAt(j)) {
                        if (count++ == mismatch) {
                            ok = false;
                            break;
                        }
                    }
                if (!ok) break;
            }
            if (ok && count == mismatch) return 100 * (i + 1);
        }
        for (int j = 0; j < C - 1; j++) {
            boolean ok = true;
            int count = 0;
            for (int a = j, b = j + 1; a >= 0 && b < C; a--, b++) {
                for (int i = 0; i < R; i++)
                    if (G.get(i).charAt(a) != G.get(i).charAt(b)) {
                        if (count++ == mismatch) {
                            ok = false;
                            break;
                        }
                    }
                if (!ok) break;
            }
            if (ok && count == mismatch) return (j + 1);
        }
        return 0;
    }

    public int solve(List<List<String>> input, int mismatch) {
        int ans = 0;
        for (List<String> list: input)
            ans += reflection(list, mismatch);
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<List<String>> input = new ArrayList<>();
        List<String> list = new ArrayList<>();
        String line = null;
        while ((line = br.readLine()) != null) {
            if ("".equals(line)) {
                input.add(list);
                list = new ArrayList<>();
            } else {
                list.add(line);
            }
        }
        input.add(list);
        Solution s = new Solution();
        int ans1 = s.solve(input, 0), ans2 = s.solve(input, 1);
        System.out.println("ans1: " + ans1 + " ans2: " + ans2);
        br.close();
    }
}
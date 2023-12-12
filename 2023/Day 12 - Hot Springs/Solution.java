import java.io.*;
import java.util.*;

class Solution {
    private long solve(String s, List<Integer> A) {
        s = s + ".";
        int m = s.length(), n = A.size();
        // dp[i][j][k] = number of ways s[0..i-1] matches with A[0..j-1] and has k '#' in the end (to match with A.get(j))
        long[][][] dp = new long[m + 1][n + 1][m];
        dp[0][0][0] = 1;
        for (int i = 1; i <= m; i++) {
            if (s.charAt(i - 1) == '.') {
                for (int j = 0; j <= n; j++)
                    dp[i][j][0] = dp[i - 1][j][0];
                for (int j = 0; j < n; j++)
                    dp[i][j + 1][0] += dp[i - 1][j][A.get(j)];
            } else if (s.charAt(i - 1) == '#') {
                for (int j = 0; j < n; j++)
                    for (int c = 0; c < A.get(j); c++)
                        dp[i][j][c + 1] = dp[i - 1][j][c];
            } else {    // s.charAt(i - 1) == '?'
                // if we choose s.charAt(i - 1) to be '.'
                for (int j = 0; j <= n; j++)
                    dp[i][j][0] = dp[i - 1][j][0];
                for (int j = 0; j < n; j++)
                    dp[i][j + 1][0] += dp[i - 1][j][A.get(j)];
                // if we choose s.charAt(i - 1) to be '#'
                for (int j = 0; j < n; j++)
                    for (int c = 0; c < A.get(j); c++)
                        dp[i][j][c + 1] = dp[i - 1][j][c];
            }
        }
        return dp[m][n][0];
    }
    public long solvePart1(List<String> list, List<List<Integer>> nums) {
        long ans = 0;
        for (int i = 0; i < list.size(); i++)
            ans += solve(list.get(i), nums.get(i));
        return ans;
    }

    public long solvePart2(List<String> list, List<List<Integer>> nums) {
        long ans = 0;
        for (int i = 0; i < list.size(); i++) {
            List<Integer> A = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 5; j++) {
                sb.append(list.get(i));
                if (j < 4)
                    sb.append('?');
                for (int a: nums.get(i))
                    A.add(a);
            }
            ans += solve(sb.toString(), A);
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<String> list = new ArrayList<>();
        List<List<Integer>> nums = new ArrayList<>();
        String line = null;
        while ((line = br.readLine()) != null) {
            String[] part = line.split("\\s+");
            list.add(part[0]);
            part = part[1].split(",");
            List<Integer> A = new ArrayList<>();
            for (String s: part)
                A.add(Integer.valueOf(s));
            nums.add(A);
        }
        Solution s = new Solution();
        long ans1 = s.solvePart1(list, nums), ans2 = s.solvePart2(list, nums);
        System.out.println("ans1: " + ans1 + " ans2: " + ans2);
        br.close();
    }
}
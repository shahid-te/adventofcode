import java.io.*;
import java.util.*;

class Solution {
    private static final int[][] score = {
        {4, 8, 3},
        {1, 5, 9},
        {7, 2, 6}
    };

    private static final int[][] target = {
        {2, 0, 1},
        {0, 1, 2},
        {1, 2, 0}
    };

    public int solvePart1(List<Integer> p1, List<Integer> p2) {
        int ans = 0;
        for (int i = 0; i < p1.size(); i++) {
            ans += score[p1.get(i)][p2.get(i)];
        }
        return ans;
    }

    public int solvePart2(List<Integer> p1, List<Integer> p2) {
        int ans = 0;
        for (int i = 0; i < p1.size(); i++) {
            ans += score[p1.get(i)][target[p1.get(i)][p2.get(i)]];
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        List<Integer> p1 = new ArrayList(), p2 = new ArrayList();
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("\\s+");
            p1.add(parts[0].charAt(0) - 'A');
            p2.add(parts[1].charAt(0) - 'X');
        }
        Solution s = new Solution();
        int ans1 = s.solvePart1(p1, p2), ans2 = s.solvePart2(p1, p2);
        System.out.println("ans1: " + ans1 + " ans2: " + ans2);
        br.close();
    }
}
import java.io.*;
import java.util.*;

class Solution {
    public int solvePart1(List<int[]> p1, List<int[]> p2) {
        int ans = 0, n = p1.size();
        for (int i = 0; i < n; i++) {
            int l = p1.get(i)[0], r = p1.get(i)[1], L = p2.get(i)[0], R = p2.get(i)[1];
            if ( (l <= L && R <= r) || (L <= l && r <= R) ) ans++;
        }
        return ans;
    }

    public int solvePart2(List<int[]> p1, List<int[]> p2) {
        int ans = 0, n = p1.size();
        for (int i = 0; i < n; i++) {
            int l = p1.get(i)[0], r = p1.get(i)[1], L = p2.get(i)[0], R = p2.get(i)[1];
            if ( !(r < L || R < l) ) ans++;
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        List<int[]> p1 = new ArrayList<>(), p2 = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            String[] part = line.split(",");
            String[] p = part[0].split("-");
            p1.add(new int[] {Integer.parseInt(p[0]), Integer.parseInt(p[1])});
            p = part[1].split("-");
            p2.add(new int[] {Integer.parseInt(p[0]), Integer.parseInt(p[1])});
        }
        Solution s = new Solution();
        int ans1 = s.solvePart1(p1, p2), ans2 = s.solvePart2(p1, p2);
        System.out.println("ans1: " + ans1 + " ans2: " + ans2);
        br.close();
    }
}
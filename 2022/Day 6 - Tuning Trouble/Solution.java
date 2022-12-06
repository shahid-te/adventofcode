import java.io.*;
import java.util.*;
import java.util.regex.*;

class Solution {
    public int solve(String s, int l) {
        int ans = 0;
        char[] c = s.toCharArray();
        for (int i = 0; i < c.length - l; i++) {
            Set<Character> set = new HashSet<>();
            for (int j = 0; j < l; j++) {
                set.add(c[i + j]);
            }
            if (set.size() == l) return i + l;
        }
        return -1;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        String line = br.readLine();
        Solution s = new Solution();
        int ans1 = s.solve(line, 4), ans2 = s.solve(line, 14);
        System.out.println("ans1: " + ans1 + " ans2: " + ans2);
        br.close();
    }
}
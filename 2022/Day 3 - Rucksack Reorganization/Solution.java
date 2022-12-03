import java.io.*;
import java.util.*;

class Solution {
    public int solvePart1(List<String> list) {
        int ans = 0;
        for (String s: list) {
            boolean[] present = new boolean[128];
            char[] chars = s.toCharArray();
            for (int i = 0; i < chars.length / 2; i++) {
                present[chars[i]] = true;
            }
            for (int i = chars.length / 2; i < chars.length; i++) {
                if (present[chars[i]]) {
                    ans += chars[i] >= 'a' ? (chars[i] - 'a' + 1) : (chars[i] - 'A' + 27);
                    break;
                }
            }
        }
        return ans;
    }

    public int solvePart2(List<String> list) {
        int ans = 0;
        for (int i = 0; i < list.size() / 3; i++) {
            boolean[] present1 = new boolean[128], present2 = new boolean[128];
            for (char c: list.get(3 * i).toCharArray())
                present1[c] = true;
            for (char c: list.get(3 * i + 1).toCharArray())
                present2[c] = true;
            for (char c: list.get(3 * i + 2).toCharArray())
                if (present1[c] && present2[c]) {
                    ans += c >= 'a' ? (c - 'a' + 1) : (c - 'A' + 27);
                    break;
                }
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        List<Integer> p1 = new ArrayList(), p2 = new ArrayList();
        List<String> list = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            list.add(line);
        }
        Solution s = new Solution();
        int ans1 = s.solvePart1(list), ans2 = s.solvePart2(list);
        System.out.println("ans1: " + ans1 + " ans2: " + ans2);
        br.close();
    }
}
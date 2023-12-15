import java.io.*;
import java.util.*;

class Solution {
    private int hash(String s) {
        int h = 0;
        for (int i = 0; i < s.length(); i++)
            h = (17 * (h + s.charAt(i))) & 255;
        return h;
    }

    public int solvePart1(String[] A) {
        int ans = 0;
        for (String s: A)
            ans += hash(s);
        return ans;
    }

    public int solvePart2(String[] A) {
        Map<String, Integer>[] maps = new Map[256];
        for (int i = 0; i < maps.length; i++)
            maps[i] = new LinkedHashMap<String, Integer>();
        for (String s: A) {
            if (s.charAt(s.length() - 1) == '-') {
                String key = s.substring(0, s.length() - 1);
                maps[hash(key)].remove(key);
            } else {
                String key = s.substring(0, s.length() - 2);
                int value = s.charAt(s.length() - 1) - '0';
                maps[hash(key)].put(key, value);
            }
        }
        int ans = 0;
        for (int i = 0; i < maps.length; i++) {
            int j = 0;
            for (Map.Entry<String, Integer> entry: maps[i].entrySet())
                ans += (i + 1) * (++j) * entry.getValue();
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input = br.readLine().split(",");
        Solution s = new Solution();
        int ans1 = s.solvePart1(input), ans2 = s.solvePart2(input);
        System.out.println("ans1: " + ans1 + " ans2: " + ans2);
        br.close();
    }
}
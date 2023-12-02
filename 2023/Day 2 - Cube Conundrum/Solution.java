import java.io.*;
import java.util.*;

class Solution {
    public int solvePart1(List<String> list) {
        int ans = 0;
        for (String s: list) {
            String[] parts = s.split("\\s+");
            int red = 0, green = 0, blue = 0;
            boolean okay = true;
            for (int i = 3; i < parts.length; i += 2) {
                String w = parts[i].substring(0, parts[i].length() - 1);
                if ("red".equals(w) || "red".equals(parts[i])) {
                    red = Integer.parseInt(parts[i - 1]);
                } else if ("green".equals(w) || "green".equals(parts[i])) {
                    green = Integer.parseInt(parts[i - 1]);
                } else if ("blue".equals(w) || "blue".equals(parts[i])) {
                    blue = Integer.parseInt(parts[i - 1]);
                }
                if (i == parts.length - 1 || parts[i].charAt(parts[i].length() - 1) == ';') {
                    if (red > 12 || green > 13 || blue > 14) {
                        okay = false;
                        break;
                    }
                    red = blue = green = 0;
                }
            }
            if (okay) {
                int id = Integer.parseInt(parts[1].substring(0, parts[1].length() - 1));
                ans += id;
            }
        }
        return ans;
    }

    public int solvePart2(List<String> list) {
        int ans = 0;
        for (String s: list) {
            String[] parts = s.split("\\s+");
            int R = 0, G = 0, B = 0, red = 0, blue = 0, green = 0;
            for (int i = 3; i < parts.length; i += 2) {
                String w = parts[i].substring(0, parts[i].length() - 1);
                if ("red".equals(w) || "red".equals(parts[i])) {
                    red = Integer.parseInt(parts[i - 1]);
                } else if ("green".equals(w) || "green".equals(parts[i])) {
                    green = Integer.parseInt(parts[i - 1]);
                } else if ("blue".equals(w) || "blue".equals(parts[i])) {
                    blue = Integer.parseInt(parts[i - 1]);
                }
                if (i == parts.length - 1 || parts[i].charAt(parts[i].length() - 1) == ';') {
                    R = Math.max(R, red);
                    G = Math.max(G, green);
                    B = Math.max(B, blue);
                    red = blue = green = 0;
                }
            }
            ans += R * G * B;
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
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
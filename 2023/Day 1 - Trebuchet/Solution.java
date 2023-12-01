import java.io.*;
import java.util.*;

class Solution {
    private static final String[] digits = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

    private int getDigit1(String s, int i) {
        char c = s.charAt(i);
        return (c >= '0' && c <= '9') ? (c - '0') : -1;
    }

    private int getDigit2(String s, int i) {
        int d = getDigit1(s, i);
        if (d != -1) return d;
        for (int j = 0; j < 9; j++) {
            int start = i - digits[j].length() + 1;
            if (start >= 0 && digits[j].equals(s.substring(start, i + 1))) return j + 1;
        }
        return -1;
    }

    public int solvePart1(List<String> list) {
        int ans = 0;
        for (String s: list) {
            int first = -1, last = -1;
            for (int i = 0; i < s.length(); i++) {
                int cur = getDigit1(s, i);
                if (cur != -1) {
                    if (first == -1) first = cur;
                    last = cur;
                }
            }
            ans += first * 10 + last;
        }
        return ans;
    }

    public int solvePart2(List<String> list) {
        int ans = 0;
        for (String s: list) {
            int first = -1, last = -1;
            for (int i = 0; i < s.length(); i++) {
                int cur = getDigit2(s, i);
                if (cur != -1) {
                    if (first == -1) first = cur;
                    last = cur;
                }
            }
            ans += first * 10 + last;
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
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
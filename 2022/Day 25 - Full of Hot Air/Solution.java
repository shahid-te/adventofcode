import java.io.*;
import java.util.*;

class Solution {
    private int toDecimal(char c) {
        return c == '-' ? -1 : (c == '=' ? -2 : c - '0');
    }

    private long toDecimal(String s) {
        long n = 0;
        for (int i = 0; i < s.length(); i++)
            n = 5 * n + toDecimal(s.charAt(i));
        return n;
    }

    private char base5(int r) {
        return r == 3 ? '=' : (r == 4 ? '-' : (char) ('0' + r));
    }

    private String toBase5(long n) {
        StringBuilder sb = new StringBuilder();
        while (n != 0) {
            int r = (int) (n % 5);
            sb.append(base5(r));
            n = (n + (r <= 2 ? -r : 5 - r)) / 5;
        }
        return sb.reverse().toString();
    }

    public String solvePart1(List<String> list) {
        long sum = 0;
        for (String s: list)
            sum += toDecimal(s);
        return toBase5(sum);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<String> lines = new ArrayList<>();
        for (String line; (line = br.readLine()) != null;)
            lines.add(line);
        br.close();
        Solution s = new Solution();
        String ans1 = s.solvePart1(lines), ans2 = "need 49 stars";
        System.out.println("ans1: " + ans1 + " ans2: " + ans2);
    }
}
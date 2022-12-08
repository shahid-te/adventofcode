import java.io.*;
import java.util.*;

class Solution {
    public int solvePart1(List<char[]> grid) {
        int R = grid.size(), C = grid.get(0).length, ans = 0;
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                int x = i - 1, y = j;
                char c = grid.get(i)[j];
                while (x >= 0 && c > grid.get(x)[y]) x--;
                if (x < 0) { ans++; continue; }
                x = i + 1;
                while (x < R && c > grid.get(x)[y]) x++;
                if (x == R) { ans++; continue; }
                x = i;
                y = j - 1;
                while (y >= 0 && c > grid.get(x)[y]) y--;
                if (y < 0) { ans++; continue; }
                y = j + 1;
                while (y < C && c > grid.get(x)[y]) y++;
                if (y == C) { ans++; continue; }
            }
        }
        return ans;
    }

    public int solvePart2(List<char[]> grid) {
        int R = grid.size(), C = grid.get(0).length, ans = 0;
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                int x = i - 1, y = j;
                char c = grid.get(i)[j];
                while (x >= 0 && c > grid.get(x)[y]) x--;
                int left = i - x + (x < 0 ? -1 : 0);
                x = i + 1;
                while (x < R && c > grid.get(x)[y]) x++;
                int right = x - i + (x == R ? -1 : 0);
                x = i;
                y = j - 1;
                while (y >= 0 && c > grid.get(x)[y]) y--;
                int up = j - y + (y < 0 ? -1 : 0);
                y = j + 1;
                while (y < C && c > grid.get(x)[y]) y++;
                int down = y - j + (y == C ? -1 : 0);
                ans = Math.max(ans, left * right * up * down);
            }
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        List<char[]> grid = new ArrayList<>();
        String line;
        while ( (line = br.readLine()) != null )
            grid.add(line.toCharArray());
        Solution s = new Solution();
        int ans1 = s.solvePart1(grid), ans2 = s.solvePart2(grid);
        System.out.println("ans1: " + ans1 + " ans2: " + ans2);
        br.close();
    }
}
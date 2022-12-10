import java.io.*;
import java.util.*;

class Solution {

    public int solvePart1(List<int[]> operations) {
        int ans = 0, x = 1, cycle = 0;
        for (int i = 0; i < operations.size(); i++) {
            if (operations.get(i)[0] == 0) {
                cycle++;
                if ((cycle - 20) % 40 == 0) {
                    ans += cycle * x;
                }
            } else {
                cycle += 2;
                if ((cycle - 20) % 40 == 0 || (cycle - 20) % 40 == 1) {
                    int cy = cycle - ((cycle - 20) % 40);
                    ans += cy * x;
                }
                x += operations.get(i)[1];
            }
        }
        return ans;
    }

    private void draw(char[][] s, int pos, int x) {
        int R = s.length, C = s[0].length;
        pos--;
        if (pos >= 0 && pos < R * C) {
            int c = pos % C, r = pos / C;
            s[r][c] = Math.abs(c - x) <= 1 ? '#' : '.';
        }
    }

    public char[][] solvePart2(List<int[]> operations) {
        char[][] screen = new char[6][40];
        int x = 1, cycle = 0;
        for (int i = 0; i < operations.size(); i++) {
            if (operations.get(i)[0] == 0) {
                cycle++;
                draw(screen, cycle, x);
            } else {
                cycle += 2;
                draw(screen, cycle - 1, x);
                draw(screen, cycle, x);
                x += operations.get(i)[1];
            }
        }
        for (; cycle < screen.length * screen[0].length; cycle++)
            draw(screen, cycle, x);
        return screen;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<int[]> list = new ArrayList<>();
        String line;
        while ( (line = br.readLine()) != null ) {
            String[] part = line.split("\\s+");
            if ("noop".equals(part[0])) {
                list.add(new int[] {0});
            } else {
                list.add(new int[] {1, Integer.parseInt(part[1])});
            }
        }
        Solution s = new Solution();
        int ans1 = s.solvePart1(list);
        System.out.println("ans1: " + ans1 + " ans2:");
        char[][] screen = s.solvePart2(list);
        for (int i = 0; i < screen.length; i++) {
            for (int j = 0; j < screen[i].length; j++)
                System.out.print(screen[i][j] == '.' ? " " : screen[i][j]);
            System.out.println();
        }
        br.close();
    }
}
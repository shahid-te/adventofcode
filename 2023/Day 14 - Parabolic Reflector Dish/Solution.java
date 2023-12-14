import java.io.*;
import java.util.*;

class Grid {
    private char[][] G;
    Grid (List<String> list) {
        G = new char[list.size()][];
        for (int i = 0; i < G.length; i++)
            G[i] = list.get(i).toCharArray();
    }
    Grid(Grid g) {
        G = new char[g.G.length][g.G[0].length];
        for (int i = 0; i < G.length; i++)
            for (int j = 0; j < G[0].length; j++)
                G[i][j] = g.G[i][j];
    }
    public Grid shiftUp() {
        for (int j = 0; j < G[0].length; j++) {
            int pos = 0;
            for (int i = 0; i < G.length; i++) {
                if (G[i][j] == 'O')
                    G[pos++][j] = G[i][j];
                else if (G[i][j] == '#') {
                    while (pos < i) G[pos++][j] = '.';
                    pos = i + 1;
                }
            }
            while (pos < G.length) G[pos++][j] = '.';
        }
        return this;
    }
    public Grid shiftLeft() {
        for (int i = 0; i < G.length; i++) {
            int pos = 0;
            for (int j = 0; j < G[i].length; j++) {
                if (G[i][j] == 'O')
                    G[i][pos++] = G[i][j];
                else if (G[i][j] == '#') {
                    while (pos < j) G[i][pos++] = '.';
                    pos = j + 1;
                }
            }
            while (pos < G[i].length) G[i][pos++] = '.';
        }
        return this;
    }
    public Grid shiftRight() {
        for (int i = 0; i < G.length; i++) {
            int pos = G[i].length - 1;
            for (int j = G[i].length - 1; j >= 0; j--) {
                if (G[i][j] == 'O')
                    G[i][pos--] = G[i][j];
                else if (G[i][j] == '#') {
                    while (pos > j) G[i][pos--] = '.';
                    pos = j - 1;
                }
            }
            while (pos >= 0) G[i][pos--] = '.';
        }
        return this;
    }
    public Grid shiftDown() {
        for (int j = 0; j < G[0].length; j++) {
            int pos = G.length - 1;
            for (int i = G.length - 1; i >= 0; i--) {
                if (G[i][j] == 'O')
                    G[pos--][j] = G[i][j];
                else if (G[i][j] == '#') {
                    while (pos > i) G[pos--][j] = '.';
                    pos = i - 1;
                }
            }
            while (pos >= 0) G[pos--][j] = '.';
        }
        return this;
    }
    public Grid performCycle() {
        return new Grid(this).shiftUp().shiftLeft().shiftDown().shiftRight();
    }
    public int load() {
        int ans = 0;
        for (int i = 0; i < G.length; i++)
            for (int j = 0; j < G[i].length; j++)
                if (G[i][j] == 'O')
                    ans += G.length - i;
        return ans;
    }
    @Override
    public int hashCode() {
        int h = 0;
        for (int i = 0; i < G.length; i++)
            for (int j = 0; j < G[i].length; j++)
                h += i * G[i][j] + j;
        return h;
    }
    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Grid)) return false;
        Grid g = (Grid) o;
        if (G.length != g.G.length || G[0].length != g.G[0].length) return false;
        for (int i = 0; i < G.length; i++)
            for (int j = 0; j < G[0].length; j++)
                if (G[i][j] != g.G[i][j])
                    return false;
        return true;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < G.length; i++) {
            for (int j = 0; j < G[i].length; j++)
                sb.append(G[i][j]);
            sb.append("\n");
        }
        return sb.toString();
    }
}

class Solution {
    public int solvePart1(Grid g) {
        return g.shiftUp().load();
    }

    public int solvePart2(Grid g, int cycles) {
        HashMap<Grid, Integer> map = new HashMap<>();
        map.put(g, 0);
        for (int i = 1; i <= cycles; i++) {
            g = g.performCycle();
            Integer prev = map.put(g, i);
            if (prev != null) {
                int len = i - prev, rem = (cycles - prev) % len;
                while (rem-- > 0)
                    g = g.performCycle();
                break;
            }
        }
        return g.load();
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<String> list = new ArrayList<>();
        String line = null;
        while ((line = br.readLine()) != null) {
            list.add(line);
        }
        Grid g = new Grid(list);
        Solution s = new Solution();
        int ans1 = s.solvePart1(g), ans2 = s.solvePart2(g, 1000000000);
        System.out.println("ans1: " + ans1 + " ans2: " + ans2);
        br.close();
    }
}
import java.io.*;
import java.util.*;

class Pair {
    public int x, y;
    Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public int hashCode() {
        return 100001 * x + y;
    }
    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Pair)) return false;
        Pair p = (Pair) o;
        return x == p.x && y == p.y;
    }
    @Override
    public String toString() {
        return x + "," + y;
    }
}

class Solution {
    private List<List<Integer>> index = new ArrayList<>();
    private List<List<Integer>> numbers = new ArrayList<>();
    private List<List<Boolean>> isPart = new ArrayList<>();

    private boolean isValid(int i, int j, int R, int C) {
        return i >= 0 && i < R && j >= 0 && j < C;
    }

    public int solvePart1(List<String> list) {
        int ans = 0;
        for (int i = 0; i < list.size(); i++) {
            List<Integer> ind = new ArrayList<>();
            List<Integer> num = new ArrayList<>();
            List<Boolean> part = new ArrayList<>();
            String s = list.get(i);
            for (int j = 0; j < s.length(); j++) {
                if (s.charAt(j) >= '0' && s.charAt(j) <= '9') {
                    int cur = s.charAt(j) - '0';
                    int k = j + 1;
                    while (k < s.length() && s.charAt(k) >= '0' && s.charAt(k) <= '9') {
                        cur = cur * 10 + s.charAt(k) - '0';
                        k++;
                    }
                    // i-1: j-1 j...k-1 k
                    // i:   j-1 j...k-1 k    j...k-1 is cur (= a number)
                    // i+1: j-1 j...k-1 k
                    boolean ok = false;
                    for (int r = i - 1; r <= i + 1; r++) {
                        for (int c = j - 1; c <= k; c++) {
                            if (isValid(r, c, list.size(), s.length())) {
                                char ch = list.get(r).charAt(c);
                                if (!(ch >= '0' && ch <= '9') && ch != '.') {
                                    ok = true;
                                    break;
                                }
                            }
                        }
                        if (ok) break;
                    }
                    if (ok) ans += cur;
                    num.add(cur);
                    part.add(ok);
                    for (int z = j; z < k; z++)
                        ind.add(num.size() - 1);
                    ind.add(-1);
                    j = k;
                } else {
                    ind.add(-1);
                }
            }
            index.add(ind);
            numbers.add(num);
            isPart.add(part);
        }
        return ans;
    }

    public int solvePart2(List<String> list) {
        int ans = 0;
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            for (int j = 0; j < s.length(); j++) {
                if (s.charAt(j) == '*') {
                    // i-1: j-1 j j+1
                    // i:   j-1 j j+1    i,j is *
                    // i+1: j-1 j j+1
                    Set<Pair> adjacent = new HashSet<>();
                    for (int r = i - 1; r <= i + 1; r++) {
                        for (int c = j - 1; c <= j + 1; c++) {
                            if (isValid(r, c, list.size(), s.length())) {
                                int ind = index.get(r).get(c);
                                if (ind != -1 && isPart.get(r).get(ind))
                                    adjacent.add(new Pair(r, ind));
                            }
                        }
                    }
                    if (adjacent.size() == 2) {
                        int cur = 1;
                        for (Pair p: adjacent)
                            cur *= numbers.get(p.x).get(p.y);
                        ans += cur;
                    }
                }
            }
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
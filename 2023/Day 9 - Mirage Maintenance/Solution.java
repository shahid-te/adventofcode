import java.io.*;
import java.util.*;

class Solution {
    private int next(List<Integer> list) {
        List<Integer> A = new ArrayList<>(list);
        int start;
        for (start = 0; start < A.size() - 1; start++) {
            for (int i = A.size() - 1; i > start; i--)
                A.set(i, A.get(i) - A.get(i - 1));
            boolean done = true;
            for (int i = A.size() - 1; i > start; i--)
                if (A.get(i) != 0) {
                    done = false;
                    break;
                }
            if (done) break;
        }
        if (start == A.size() - 1) {
            System.out.println("Can not extrapolate. Not enough elements!");
            return -1;
        }
        A.add(0);
        for (; start >= 0; start--) {
            for (int i = start + 1; i < A.size(); i++)
                A.set(i, A.get(i) + A.get(i - 1));
        }
        return A.get(A.size() - 1);
    }

    public int solvePart1(List<List<Integer>> list) {
        int ans = 0;
        for (List<Integer> A: list)
            ans += next(A);
        return ans;
    }

    public int solvePart2(List<List<Integer>> list) {
        int ans = 0;
        for (List<Integer> A: list) {
            Collections.reverse(A);
            ans += next(A);
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<List<Integer>> list = new ArrayList<>();
        String line = null;
        while ((line = br.readLine()) != null) {
            String[] part = line.split("\\s+");
            List<Integer> A = new ArrayList<>();
            for (int i = 0; i < part.length; i++)
                A.add(Integer.valueOf(part[i]));
            list.add(A);
        }
        Solution s = new Solution();
        int ans1 = s.solvePart1(list), ans2 = s.solvePart2(list);
        System.out.println("ans1: " + ans1 + " ans2: " + ans2);
        br.close();
    }
}
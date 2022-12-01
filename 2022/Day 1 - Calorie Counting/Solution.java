import java.io.*;
import java.util.*;

class Solution {
    public int solvePart1(List<List<Integer>> lists) {
        int max = 0;
        for (List<Integer> list: lists) {
            int sum = 0;
            for (int a: list) {
                sum += a;
            }
            max = Math.max(max, sum);
        }
        return max;
    }

    public int solvePart2(List<List<Integer>> lists) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (List<Integer> list: lists) {
            int sum = 0;
            for (int a: list) {
                sum += a;
            }
            pq.add(sum);
            if (pq.size() > 3) {
                pq.poll();
            }
        }
        int max3 = 0;
        while (!pq.isEmpty()) {
            max3 += pq.poll();
        }
        return max3;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        List<List<Integer>> lists = new ArrayList<>();
        List<Integer> cur = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            if ("".equals(line)) {
                lists.add(cur);
                cur = new ArrayList();
            } else {
                cur.add(Integer.valueOf(line));
            }
        }
        if (!cur.isEmpty()) {
            lists.add(cur);
        }
        Solution s = new Solution();
        int ans1 = s.solvePart1(lists), ans2 = s.solvePart2(lists);
        System.out.println("ans1: " + ans1 + " ans2: " + ans2);
        br.close();
    }
}
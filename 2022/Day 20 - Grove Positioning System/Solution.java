import java.io.*;
import java.util.*;

class Solution {
    public long solve(List<Integer> list, long F, int R) {
        int n = list.size();
        int[] forward = new int[n], reverse = new int[n];
        // forward[i] = where list.get(i) is located now
        // reverse[i] = i'th item in current order corresponds to which index of original list. more formally, index such that forward[index] == i
        for (int i = 0; i < n; i++)
            forward[i] = reverse[i] = i;

        while (R-- > 0) {
            for (int i = 0; i < n; i++) {
                int shift = ((n - 1) + (int) (F * list.get(i) % (n - 1))) % (n - 1), p = forward[i];
                for (int j = 0; j < shift; j++) {
                    int p1 = p + 1 == n ? 0 : p + 1;
                    reverse[p] = reverse[p1];
                    forward[reverse[p1]] = p;
                    p = p1;
                }
                reverse[p] = i;
                forward[i] = p;
            }
        }
        for (int i = 0; i < n; i++)
            if (list.get(i) == 0) {
                int p = forward[i];
                return F * (list.get(reverse[(p + 1000) % n]) + list.get(reverse[(p + 2000) % n]) + list.get(reverse[(p + 3000) % n]));
            }
        return 0;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<Integer> list = new ArrayList<>();
        String line = null;
        while ((line = br.readLine()) != null)
            list.add(Integer.valueOf(line));
        br.close();
        Solution s = new Solution();
        long ans1 = s.solve(list, 1, 1), ans2 = s.solve(list, 811589153, 10);
        System.out.println("ans1: " + ans1 + " ans2: " + ans2);
    }
}
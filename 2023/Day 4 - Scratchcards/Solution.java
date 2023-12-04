import java.io.*;
import java.util.*;

class Solution {
    public int solvePart1(List<String> list) {
        int ans = 0;
        for (String s: list) {
            String[] part = s.split("\\s+\\|\\s+");
            String[] first = part[0].split("\\s+"), second = part[1].split("\\s+");
            Set<Integer> set = new HashSet<>();
            for (int i = 2; i < first.length; i++)
                set.add(Integer.parseInt(first[i]));
            int count = 0;
            for (int i = 0; i < second.length; i++)
                if (set.contains(Integer.parseInt(second[i])))
                    count++;
            ans += count > 0 ? 1 << (count - 1) : 0;
        }
        return ans;
    }

    public int solvePart2(List<String> list) {
        int ans = 0;
        int[] F = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ans += ++F[i];
            String[] part = list.get(i).split("\\s+\\|\\s+");
            String[] first = part[0].split("\\s+"), second = part[1].split("\\s+");
            Set<Integer> set = new HashSet<>();
            for (int j = 2; j < first.length; j++)
                set.add(Integer.parseInt(first[j]));
            int count = 0;
            for (int j = 0; j < second.length; j++)
                if (set.contains(Integer.parseInt(second[j])))
                    count++;
            for (int j = 0; j < count; j++)
                F[i + 1 + j] += F[i];
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
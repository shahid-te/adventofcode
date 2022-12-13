import java.io.*;
import java.util.*;

class NestedInteger implements Comparable<NestedInteger> {
    public boolean isNum;
    public int num;
    public List<NestedInteger> list = new ArrayList<>();

    NestedInteger() {}

    NestedInteger(int num) {
        this.num = num;
        isNum = true;
    }

    public void add(NestedInteger ni) {
        list.add(ni);
    }

    @Override
    public int compareTo(NestedInteger right) {
        if (isNum && right.isNum) {
            return Integer.compare(num, right.num);
        }
        if (isNum) {
            NestedInteger ni = new NestedInteger();
            ni.add(this);
            return ni.compareTo(right);
        }
        if (right.isNum) {
            NestedInteger ni = new NestedInteger();
            ni.add(right);
            return this.compareTo(ni);
        }
        int m = list.size(), n = right.list.size(), min = Math.min(m, n);
        for (int i = 0; i < min; i++) {
            int cmp = list.get(i).compareTo(right.list.get(i));
            if (cmp != 0) return cmp;
        }
        return Integer.compare(m, n);
    }

    public static NestedInteger of(String s) {
        Deque<NestedInteger> stack = new ArrayDeque<>();
        NestedInteger cur = new NestedInteger();
        boolean sign = false;
        char[] c = s.toCharArray();
        for(int i = 0; i < c.length; i++) {
            if(c[i] == '-') {
                sign = true;
            } else if(c[i] >= '0' && c[i] <= '9') {
                int val = c[i] - '0';
                while(i+1 < c.length && c[i+1] >= '0' && c[i+1] <= '9') val = val * 10 + c[++i] - '0';
                cur.add(new NestedInteger(sign ? -val : val));
                sign = false;
            } else if(c[i] == '[') {
                stack.push(cur);
                cur = new NestedInteger();
            } else if(c[i] == ']') {
                stack.peek().add(cur);
                cur = stack.pop();
            }
        }
        return cur.list.get(0);
    }
}

class Solution {
    public int solvePart1(List<NestedInteger> left, List<NestedInteger> right) {
        int ans = 0;
        for (int i = 0; i < left.size(); i++) {
            if (left.get(i).compareTo(right.get(i)) < 0)
                ans += i + 1;
        }
        return ans;
    }

    public int solvePart2(List<NestedInteger> left, List<NestedInteger> right) {
        NestedInteger key1 = NestedInteger.of("[[2]]"), key2 = NestedInteger.of("[[6]]");
        int loc1 = 0, loc2 = 1;
        for (int i = 0; i < left.size(); i++) {
            if (key1.compareTo(left.get(i)) > 0)
                loc1++;
            if (key1.compareTo(right.get(i)) > 0)
                loc1++;
            if (key2.compareTo(left.get(i)) > 0)
                loc2++;
            if (key2.compareTo(right.get(i)) > 0)
                loc2++;
        }
        return (loc1 + 1) * (loc2 + 1);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<NestedInteger> left = new ArrayList<>(), right = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            left.add(NestedInteger.of(line));
            right.add(NestedInteger.of(br.readLine()));
            br.readLine();
        }
        br.close();
        Solution s = new Solution();
        int ans1 = s.solvePart1(left, right), ans2 = s.solvePart2(left, right);
        System.out.println("ans1: " + ans1 + " ans2: " + ans2);
    }
}
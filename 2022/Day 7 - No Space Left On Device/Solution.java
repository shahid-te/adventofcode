import java.io.*;
import java.util.*;

class Dir {
    public boolean isFile;
    public int size;
    public String name;
    public Map<String, Dir> next = new HashMap<>();

    public Dir(String name) {
        this.name = name;
    }

    public Dir(int size, String name) {
        this.size = size;
        this.name = name;
        this.isFile = true;
    }
}

class Solution {
    private int calculateSize(Dir d) {
        for (Dir n: d.next.values())
            d.size += n.isFile ? n.size : calculateSize(n);
        return d.size;
    }

    public int solvePart1(Dir d) {
        int ans = d.size <= 100000 ? d.size : 0;
        for (Dir n: d.next.values())
            ans += !n.isFile ? solvePart1(n) : 0;
        return ans;
    }

    public int solvePart2(Dir d, int threshold) {
        int ans = d.size >= threshold ? d.size : Integer.MAX_VALUE;
        for (Dir n: d.next.values())
            if (!n.isFile)
                ans = Math.min(ans, solvePart2(n, threshold));
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        Dir root = new Dir("/");
        Deque<Dir> stack = new ArrayDeque<>();
        stack.push(root);
        String line;
        while ( (line = br.readLine()) != null ) {
            String[] part = line.split("\\s+");
            if ("$".equals(part[0])) {
                if ("cd".equals(part[1])) {
                    if ("..".equals(part[2])) {
                        stack.pop();
                    } else if("/".equals(part[2])) {
                        while(!stack.isEmpty()) stack.pop();
                        stack.push(root);
                    } else {
                        stack.push(stack.peek().next.get(part[2]));
                    }
                } /* else {    // "ls"
                }*/
            } else {
                if ("dir".equals(part[0])) {
                    stack.peek().next.putIfAbsent(part[1], new Dir(part[1]));
                } else {
                    stack.peek().next.putIfAbsent(part[1], new Dir(Integer.parseInt(part[0]), part[1]));
                }
            }
        }
        Solution s = new Solution();
        s.calculateSize(root);
        int ans1 = s.solvePart1(root), ans2 = s.solvePart2(root, root.size - 40000000);
        System.out.println("ans1: " + ans1 + " ans2: " + ans2);
        br.close();
    }
}
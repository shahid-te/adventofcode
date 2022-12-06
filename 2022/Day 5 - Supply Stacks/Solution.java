import java.io.*;
import java.util.*;
import java.util.regex.*;

class Solution {
    public String solvePart1(List<Deque<String>> stacks, List<int[]> moves) {
        int ans = 0;
        for (int[] move: moves) {
            Deque<String> from = stacks.get(move[1]), to = stacks.get(move[2]);
            for (int i = 0; i < move[0]; i++) to.push(from.pop());
        }
        StringBuilder sb = new StringBuilder();
        for (Deque<String> stack: stacks)
            sb.append(stack.peek());
        return sb.toString();
    }

    public String solvePart2(List<Deque<String>> stacks, List<int[]> moves) {
        int ans = 0;
        Deque<String> temp = new ArrayDeque<>();
        for (int[] move: moves) {
            Deque<String> from = stacks.get(move[1]), to = stacks.get(move[2]);
            for(int i = 0; i < move[0]; i++) temp.push(from.pop());
            while(!temp.isEmpty()) to.push(temp.pop());
        }
        StringBuilder sb = new StringBuilder();
        for (Deque<String> stack: stacks)
            sb.append(stack.peek());
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        List<Deque<String>> stacks1 = new ArrayList<>(), stacks2 = new ArrayList<>();
        String line = br.readLine();

        int len = line.length(), n = (1 + len) / 4;
        for (int i = 0; i < n; i++) {
            stacks1.add(new ArrayDeque<>());
            stacks2.add(new ArrayDeque<>());
        }

        do {
            boolean noMore = true;
            for (int i = 0; i < n; i++) {
                if (line.charAt(4 * i) == '[') {
                    stacks1.get(i).add(String.valueOf(line.charAt(4 * i + 1)));
                    stacks2.get(i).add(String.valueOf(line.charAt(4 * i + 1)));
                    noMore = false;
                }
            }
            if (noMore) break;
        } while ((line = br.readLine()) != null);

        Pattern pattern = Pattern.compile("\\d+");

        List<int[]> moves = new ArrayList<>();

        line = br.readLine();
        while ((line = br.readLine()) != null) {
            Matcher m = pattern.matcher(line);
            int[] move = new int[3];
            m.find();
            move[0] = Integer.parseInt(m.group());
            m.find();
            move[1] = Integer.parseInt(m.group()) - 1;
            m.find();
            move[2] = Integer.parseInt(m.group()) - 1;
            moves.add(move);
        }
        Solution s = new Solution();
        String ans1 = s.solvePart1(stacks1, moves), ans2 = s.solvePart2(stacks2, moves);
        System.out.println("ans1: " + ans1 + " ans2: " + ans2);
        br.close();
    }
}
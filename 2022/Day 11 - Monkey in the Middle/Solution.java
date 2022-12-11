import java.io.*;
import java.util.*;
import java.util.function.Function;

class Monkey {
    public Deque<Long> items;
    public Function<Long, Long> f;
    public int divisor;
    public int[] next = new int[2];
}

class Solution {

    public long solvePart1(List<Monkey> monkeys, int rounds) {
        int n = monkeys.size();
        int[] count = new int[n];
        while(rounds-- > 0) {
            for (int i = 0; i < n; i++) {
                Monkey m = monkeys.get(i);
                while (!m.items.isEmpty()) {
                    long v = m.f.apply(m.items.poll()) / 3;
                    monkeys.get(m.next[v % m.divisor == 0 ? 1 : 0]).items.add(v);
                    count[i]++;
                }
            }
        }
        Arrays.sort(count);
        return (long) count[n - 2] * count[n - 1];
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int c = a % b;
            a = b;
            b = c;
        }
        return a;
    }

    private int LCM(List<Monkey> monkeys) {
        int res = 1;
        for (Monkey m: monkeys)
            res = (res * m.divisor) / gcd(res, m.divisor);
        return res;
    }

    public long solvePart2(List<Monkey> monkeys, int rounds) {
        int n = monkeys.size(), lcm = LCM(monkeys);
        int[] count = new int[n];
        while(rounds-- > 0) {
            for (int i = 0; i < n; i++) {
                Monkey m = monkeys.get(i);
                while (!m.items.isEmpty()) {
                    long v = m.f.apply(m.items.poll()) % lcm;
                    monkeys.get(m.next[v % m.divisor == 0 ? 1 : 0]).items.add(v);
                    count[i]++;
                }
            }
        }
        Arrays.sort(count);
        return (long) count[n - 2] * count[n - 1];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<Monkey> monkeys1 = new ArrayList<>(), monkeys2 = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            Monkey m = new Monkey();
            line = br.readLine().substring(18);
            String[] part = line.split(",\\s+");
            m.items = new ArrayDeque<>();
            for (String s: part)
                m.items.add(Long.valueOf(s));
            line = br.readLine();
            if (line.charAt(23) == '+') {
                if ("old".equals(line.substring(25))) {
                    m.f = x -> x + x;
                } else {
                    int num = Integer.parseInt(line.substring(25));
                    m.f = x -> x + num;
                }
            } else {
                if ("old".equals(line.substring(25))) {
                    m.f = x -> x * x;
                } else {
                    int num = Integer.parseInt(line.substring(25));
                    m.f = x -> x * num;
                }
            }
            m.divisor = Integer.parseInt(br.readLine().substring(21));
            m.next[1] = Integer.parseInt(br.readLine().substring(29));
            m.next[0] = Integer.parseInt(br.readLine().substring(30));
            br.readLine();
            monkeys1.add(m);
            Monkey m2 = new Monkey();
            m2.items = new ArrayDeque<>(m.items);
            m2.f = m.f;
            m2.divisor = m.divisor;
            m2.next = m.next;
            monkeys2.add(m2);
        }
        br.close();
        Solution s = new Solution();
        long ans1 = s.solvePart1(monkeys1, 20), ans2 = s.solvePart2(monkeys2, 10000);
        System.out.println("ans1: " + ans1 + " ans2: " + ans2);
    }
}
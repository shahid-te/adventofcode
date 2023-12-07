import java.io.*;
import java.util.*;

class Hand {
    public String card;
    public int bid;
    Hand(String card, int bid) {
        this.card = card;
        this.bid = bid;
    }
}

class Solution {
    private int toInt(char c, boolean wild) {
        if (c == 'T') return 10;
        if (c == 'J') return wild ? 1 : 11;
        if (c == 'Q') return 12;
        if (c == 'K') return 13;
        if (c == 'A') return 14;
        return c - '2' + 2;
    }

    private int rank(String hand, boolean wild) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < hand.length(); i++)
            map.put(hand.charAt(i), map.getOrDefault(hand.charAt(i), 0) + 1);
        if (wild && map.containsKey('J')) {
            int count = map.remove('J'), maxValue = 0;
            char maxKey = 'J';
            for (Map.Entry<Character, Integer> entry: map.entrySet())
                if (entry.getValue() > maxValue) {
                    maxKey = entry.getKey();
                    maxValue = entry.getValue();
                }
            map.put(maxKey, maxValue + count);
        }
        if (map.size() == 1) return 7;
        List<Integer> f = new ArrayList<>(map.values());
        if (map.size() == 2) return (f.get(0) == 4 || f.get(1) == 4) ? 6 : 5;
        if (map.size() == 3) return (f.get(0) == 3 || f.get(1) == 3 || f.get(2) == 3) ? 4 : 3;
        return map.size() == 4 ? 2 : 1;
    }

    private int compare(String a, String b, boolean wild) {
        int r1 = rank(a, wild), r2 = rank(b, wild);
        if (r1 != r2) return r1 - r2;
        for (int i = 0; i < a.length(); i++) {
            int x = toInt(a.charAt(i), wild), y = toInt(b.charAt(i), wild);
            if (x != y) return x - y;
        }
        return 0;
    }

    public int solve(List<Hand> hands, boolean wild) {
        Collections.sort(hands, (a, b) -> compare(a.card, b.card, wild));
        int ans = 0;
        for (int i = 0; i < hands.size(); i++)
            ans += (i + 1) * hands.get(i).bid;
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<Hand> hands = new ArrayList<>();
        String line = null;
        while ((line = br.readLine()) != null) {
            String[] part = line.split("\\s+");
            hands.add(new Hand(part[0], Integer.parseInt(part[1])));
        }
        Solution s = new Solution();
        int ans1 = s.solve(hands, false), ans2 = s.solve(hands, true);
        System.out.println("ans1: " + ans1 + " ans2: " + ans2);
        br.close();
    }
}
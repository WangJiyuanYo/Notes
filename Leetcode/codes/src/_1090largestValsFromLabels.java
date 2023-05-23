import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class _1090largestValsFromLabels {
    static class Solution {
        public int largestValsFromLabels(int[] values, int[] labels, int numWanted, int useLimit) {
            int n = values.length;
            int[][] pairs = new int[n][2];
            for (int i = 0; i < n; ++i) {
                pairs[i] = new int[]{values[i], labels[i]};
            }
            Arrays.sort(pairs, (a, b) -> b[0] - a[0]);
            Map<Integer, Integer> cnt = new HashMap<>();
            int ans = 0, num = 0;
            for (int i = 0; i < n && num < numWanted; ++i) {
                int v = pairs[i][0], l = pairs[i][1];
                if (cnt.getOrDefault(l, 0) < useLimit) {
                    cnt.merge(l, 1, Integer::sum);
                    num += 1;
                    ans += v;
                }
            }
            return ans;

        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().largestValsFromLabels(new int[]{5, 4, 3, 2, 1}, new int[]{1, 3, 3, 3, 2}, 3, 2));
    }
}

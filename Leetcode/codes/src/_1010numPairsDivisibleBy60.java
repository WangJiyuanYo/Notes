public class _1010numPairsDivisibleBy60 {
    static class Solution {
        public int numPairsDivisibleBy60(int[] time) {
            int ans = 0;
            int[] cnt = new int[60];
            for (int t : time) {
                // 先查询 cnt，再更新 cnt，因为题目要求 i<j
                // 如果先更新，再查询，就把 i=j 的情况也考虑进去了
                ans += cnt[(60 - t % 60) % 60];
                cnt[t % 60]++;
            }
            return ans;
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().numPairsDivisibleBy60(new int[]{30, 20, 150, 100, 40}));
    }
}

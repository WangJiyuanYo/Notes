import java.util.Arrays;

public class _1335minDifficulty {
    static class Solution {
        private int[] a;
        private int[][] memo;

        public int minDifficulty(int[] a, int d) {
            int n = a.length;
            if (n < d) return -1;

            this.a = a;
            memo = new int[d][n];
            for (int i = 0; i < d; ++i)
                Arrays.fill(memo[i], -1); // -1 表示还没有计算过
            return dfs(d - 1, n - 1);
        }

        private int dfs(int i, int j) {
            if (memo[i][j] != -1) // 之前计算过了
                return memo[i][j];
            if (i == 0) { // 只有一天，必须完成所有工作
                int mx = 0;
                for (int k = 0; k <= j; k++)
                    mx = Math.max(mx, a[k]);
                return memo[i][j] = mx;
            }
            int res = Integer.MAX_VALUE;
            int mx = 0;
            for (int k = j; k >= i; k--) {
                mx = Math.max(mx, a[k]); // 从 a[k] 到 a[j] 的最大值
                res = Math.min(res, dfs(i - 1, k - 1) + mx);
            }
            return memo[i][j] = res;
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().minDifficulty(new int[]{6, 5, 4, 3, 2, 1}, 2));
    }
}

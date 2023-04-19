import java.util.Arrays;

public class _1043maxSumAfterPartitioning {

    class Solution {
        private int[] arr, memo;
        private int k;

        public int maxSumAfterPartitioning(int[] arr, int k) {
            this.arr = arr;
            this.k = k;
            int n = arr.length;
            memo = new int[n];
            Arrays.fill(memo, -1); // -1 表示还没有计算过
            return dfs(n - 1);
        }

        private int dfs(int i) {
            if (i < 0) return 0;
            if (memo[i] != -1) return memo[i]; // 之前计算过了
            int res = 0;
            for (int j = i, mx = 0; j > i - k && j >= 0; --j) {
                mx = Math.max(mx, arr[j]); // 一边枚举 j，一边计算子数组的最大值
                res = Math.max(res, dfs(j - 1) + (i - j + 1) * mx);
            }
            return memo[i] = res; // 记忆化
        }
    }

    public void main(String[] args) {
        System.out.println(new Solution().maxSumAfterPartitioning(new int[]{1, 15, 7, 9, 2, 5, 10}, 3));
    }

}

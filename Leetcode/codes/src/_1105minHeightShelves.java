import java.util.Arrays;

public class _1105minHeightShelves {
    class Solution {
        private int[][] books;
        private int shelfWidth;
        private int[] memo;

        public int minHeightShelves(int[][] books, int shelfWidth) {
            this.books = books;
            this.shelfWidth = shelfWidth;
            int n = books.length;
            memo = new int[n];
            Arrays.fill(memo, -1); // -1 表示还没有计算过
            return dfs(n - 1);
        }

        private int dfs(int i) {
            if (i < 0) return 0; // 没有书了，高度是 0
            if (memo[i] != -1) return memo[i]; // 之前计算过了
            int res = Integer.MAX_VALUE, maxH = 0, leftW = shelfWidth;
            for (int j = i; j >= 0; --j) {
                leftW -= books[j][0];
                if (leftW < 0) break; // 空间不足，无法放书
                maxH = Math.max(maxH, books[j][1]); // 从 j 到 i 的最大高度
                res = Math.min(res, dfs(j - 1) + maxH);
            }
            return memo[i] = res; // 记忆化
        }
    }

    public static void main(String[] args) {

    }
}

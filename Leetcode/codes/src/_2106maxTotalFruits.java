import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class _2106maxTotalFruits {
    static class Solution {
        public int maxTotalFruits(int[][] fruits, int startPos, int k) {
            int left = lowerBound(fruits, startPos - k); // 向左最远能到 fruits[left][0]
            int ans = 0, s = 0, n = fruits.length;
            for (int right = left; right < n && fruits[right][0] <= startPos + k; right++) {
                s += fruits[right][1]; // 枚举最右位置为 fruits[right][0]
                while (fruits[right][0] * 2 - fruits[left][0] - startPos > k &&
                        fruits[right][0] - fruits[left][0] * 2 + startPos > k)
                    s -= fruits[left++][1]; // fruits[left][0] 无法到达
                ans = Math.max(ans, s); // 更新答案最大值
            }
            return ans;
        }

        // 见 https://www.bilibili.com/video/BV1AP41137w7/
        private int lowerBound(int[][] fruits, int target) {
            int left = -1, right = fruits.length; // 开区间 (left, right)
            while (left + 1 < right) { // 开区间不为空
                // 循环不变量：
                // fruits[left][0] < target
                // fruits[right][0] >= target
                int mid = (left + right) >>> 1;
                if (fruits[mid][0] < target)
                    left = mid; // 范围缩小到 (mid, right)
                else
                    right = mid; // 范围缩小到 (left, mid)
            }
            return right;
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().maxTotalFruits(new int[][]{{2, 8}, {6, 3}, {8, 6}}, 5, 4));
    }
}

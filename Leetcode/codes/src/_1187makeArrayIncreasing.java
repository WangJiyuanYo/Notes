import java.util.Arrays;

public class _1187makeArrayIncreasing {
    class Solution {
        public int makeArrayIncreasing(int[] a, int[] b) {
            Arrays.sort(b);
            int n = a.length, m = 0;
            for (int i = 1; i < b.length; ++i)
                if (b[m] != b[i])
                    b[++m] = b[i]; // 原地去重
            ++m;
            int[] f = new int[n + 1];
            for (int i = 0; i <= n; i++) {
                int x = i < n ? a[i] : Integer.MAX_VALUE;
                int k = lowerBound(b, m, x);
                int res = k < i ? Integer.MIN_VALUE : 0; // 小于 a[i] 的数全部替换
                if (i > 0 && a[i - 1] < x) // 无替换
                    res = Math.max(res, f[i - 1]);
                for (int j = i - 2; j >= i - k - 1 && j >= 0; --j)
                    if (b[k - (i - j - 1)] > a[j])
                        // a[j+1] 到 a[i-1] 替换成 b[k-(i-j-1)] 到 b[k-1]
                        res = Math.max(res, f[j]);
                f[i] = res + 1; // 把 +1 移到这里，表示 a[i] 不替换
            }
            return f[n] < 0 ? -1 : n + 1 - f[n];
        }

        // 见 https://www.bilibili.com/video/BV1AP41137w7/
        private int lowerBound(int[] nums, int right, int target) {
            int left = -1; // 开区间 (left, right)
            while (left + 1 < right) { // 区间不为空
                // 循环不变量：
                // nums[left] < target
                // nums[right] >= target
                int mid = (left + right) >>> 1;
                if (nums[mid] < target)
                    left = mid; // 范围缩小到 (mid, right)
                else
                    right = mid; // 范围缩小到 (left, mid)
            }
            return right;
        }
    }
}

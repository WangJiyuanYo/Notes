import java.util.Arrays;

public class _lcp33storeWater {
    static class Solution {
        public int storeWater(int[] bucket, int[] vat) {
            int mx = Arrays.stream(vat).max().getAsInt();
            if (mx == 0) {
                return 0;
            }
            int n = vat.length;
            int ans = 1 << 30;
            for (int x = 1; x <= mx; ++x) {
                int y = 0;
                for (int i = 0; i < n; ++i) {
                    y += Math.max(0, (vat[i] + x - 1) / x - bucket[i]);
                }
                ans = Math.min(ans, x + y);
            }
            return ans;
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().storeWater(new int[]{1, 3}, new int[]{6, 8}));
    }
}

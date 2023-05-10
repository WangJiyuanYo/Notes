public class _1015smallestRepunitDivByK {
    static class Solution {
        public int smallestRepunitDivByK(int k) {
            if (k % 2 == 0 || k % 5 == 0)
                return -1;
            int x = 1 % k;
            for (int i = 1; ; i++) { // 一定有解
                if (x == 0)
                    return i;
                x = (x * 10 + 1) % k;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().smallestRepunitDivByK(3));
    }
}

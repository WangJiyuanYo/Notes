public class _2413smallestEvenMultiple {
    static class Solution {
        public int smallestEvenMultiple(int n) {
            if (n % 2 == 0) {
                return n;
            } else {
                return n * 2;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().smallestEvenMultiple(6));
    }
}

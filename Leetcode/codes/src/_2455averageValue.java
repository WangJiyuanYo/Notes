public class _2455averageValue {
    static class Solution {
        public int averageValue(int[] nums) {
            int count = 0;
            int sum = 0;
            for (int num : nums) {
                if (num % 3 == 0 && num % 2 == 0) {
                    sum += num;
                    count++;
                }
            }
            return count == 0 ? 0 : sum / count;
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().averageValue(new int[]{4, 4, 9, 10}));
    }
}

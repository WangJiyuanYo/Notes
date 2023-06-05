public class _2460applyOperations {
    static class Solution {
        public int[] applyOperations(int[] nums) {
            int length = nums.length;
            for (int i = 0; i < length - 1; i++) {
                if (nums[i] == nums[i + 1]) {
                    nums[i] <<= 1;
                    nums[i + 1] = 0;
                }
            }
            int left = 0;// 相当于 B
            int right = 0;// 相当于 A
            while (right < length) {
                if (nums[right] != 0) {
                    int tmp = nums[right];
                    nums[right] = nums[left];
                    nums[left] = tmp;
                    left++;
                }
                right++;
            }
            return nums;
        }
    }

    public static void main(String[] args) {
        new Solution().applyOperations(new int[]{1, 2, 2, 1, 1, 0});
    }
}

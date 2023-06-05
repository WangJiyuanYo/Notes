import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class _2465distinctAverages {
    static class Solution {
        public int distinctAverages(int[] nums) {
            Arrays.sort(nums);
            int min = 0, max = nums.length - 1;
            Set<Double> set = new HashSet<Double>();
            while (min < max) {
                set.add((nums[max] + nums[min]) / 2.0);
                min++;
                max--;
            }

            return set.size();

        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().distinctAverages(new int[]{1,100}));
    }
}

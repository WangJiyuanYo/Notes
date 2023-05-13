import java.util.HashSet;

public class _2441findMaxK {
    static class Solution {
        public int findMaxK(int[] nums) {
            int res = -1;
            HashSet<Integer> positive = new HashSet<>();
            HashSet<Integer> negative = new HashSet<>();
            for (int num : nums) {
                if (num <= 0) {
                    negative.add(num);
                    if (positive.contains(Math.abs(num))) {
                        res = Math.max(res, -num);
                    }
                } else {
                    positive.add(num);
                    if (negative.contains(-num)) {
                        res = Math.max(res, num);
                    }
                }
            }
            return res;
        }


        public int secondMethod(int[] nums) {
            int res = -1;
            HashSet<Integer> set = new HashSet<>();
            for (int num : nums) {
                if (set.contains(-num)) {
                    res = Math.max(res, Math.abs(num));
                }
                set.add(num);
            }
            return res;
        }
    }
    //Third:排序+双指针

    public static void main(String[] args) {
        System.out.println(new Solution().secondMethod(new int[]{-1,10,6,7,-7,1}));
    }
}

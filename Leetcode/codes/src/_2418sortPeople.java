import java.util.Arrays;

public class _2418sortPeople {
    static class Solution {
        public String[] sortPeople(String[] names, int[] heights) {
            int n = names.length;
            Integer[] id = new Integer[n];
            for (int i = 0; i < n; ++i)
                id[i] = i;
            Arrays.sort(id, (i, j) -> heights[j] - heights[i]);
            for (int integer : id) {
                System.out.println(integer);
            }
            String[] ans = new String[n];
            for (int i = 0; i < n; ++i)
                ans[i] = names[id[i]];
            return ans;
        }

    }

    public static void main(String[] args) {
        String[] names = new Solution().sortPeople(new String[]{"Alice", "Bob", "Bob"}, new int[]{155, 185, 150});
        for (String name : names) {
            System.out.println(name);
        }
    }
}
